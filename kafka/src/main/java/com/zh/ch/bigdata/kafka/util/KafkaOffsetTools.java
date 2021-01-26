package com.zh.ch.bigdata.kafka.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import kafka.api.PartitionOffsetRequestInfo;
import kafka.common.ErrorMapping;
import kafka.common.OffsetMetadataAndError;
import kafka.common.TopicAndPartition;
import kafka.javaapi.OffsetFetchRequest;
import kafka.javaapi.OffsetFetchResponse;
import kafka.javaapi.OffsetResponse;
import kafka.javaapi.PartitionMetadata;
import kafka.javaapi.TopicMetadata;
import kafka.javaapi.TopicMetadataRequest;
import kafka.javaapi.TopicMetadataResponse;
import kafka.javaapi.consumer.SimpleConsumer;
import kafka.network.BlockingChannel;

/**
 * @author xzc
 * @description
 * @date 2021/01/18
 */
public class KafkaOffsetTools {
    public static Long getKafkaTopicLagWithConsumerGroup(String broker, int port,  String topic, String group) {
        int correlationId = 0;
        String clientId = "0";
        BlockingChannel channel = new BlockingChannel(broker, port,
                BlockingChannel.UseDefaultBufferSize(),
                BlockingChannel.UseDefaultBufferSize(),
                5000);
        channel.connect();

        List<String> seeds = new ArrayList<>();
        seeds.add(broker);

        TreeMap<Integer, PartitionMetadata> metaData = findLeader(seeds, port, topic);

        long sum = 0L;
        long sumOffset = 0L;
        List<TopicAndPartition> partitions = new ArrayList<>();
        for (Map.Entry<Integer,PartitionMetadata> entry : metaData.entrySet()) {
            int partition = entry.getKey();
            TopicAndPartition testPartition = new TopicAndPartition(topic, partition);
            partitions.add(testPartition);
        }
        OffsetFetchRequest fetchRequest = new OffsetFetchRequest(
                group,
                partitions,
                (short) 0,
                correlationId,
                clientId);
        for (Map.Entry<Integer,PartitionMetadata> entry : metaData.entrySet()) {
            int partition = entry.getKey();
            try {
                channel.send(fetchRequest.underlying());
                OffsetFetchResponse fetchResponse = OffsetFetchResponse.readFrom(channel.receive().payload());
                TopicAndPartition testPartition0 = new TopicAndPartition(topic, partition);
                OffsetMetadataAndError result = fetchResponse.offsets().get(testPartition0);
                short offsetFetchErrorCode = result.error();
                if (offsetFetchErrorCode != ErrorMapping.NotCoordinatorForConsumerCode()) {
                    long retrievedOffset = result.offset();
                    sumOffset += retrievedOffset;
                }
                String leadBroker = entry.getValue().leader().host();
                String clientName = "Client_" + topic + "_" + partition;
                SimpleConsumer consumer = new SimpleConsumer(leadBroker, port, 100000,
                        64 * 1024, clientName);
                long readOffset = getLastOffset(consumer, topic, partition,
                        kafka.api.OffsetRequest.LatestTime(), clientName);
                sum += readOffset;
                consumer.close();
            } catch (Exception e) {
                channel.disconnect();
            }
        }
        if (sumOffset < 0) {
            return sum;
        }
        else {
            return sum - sumOffset;
        }
    }

    public static long getLastOffset(SimpleConsumer consumer, String topic,
                                     int partition, long whichTime, String clientName) {
        TopicAndPartition topicAndPartition = new TopicAndPartition(topic,
                partition);
        Map<TopicAndPartition, PartitionOffsetRequestInfo> requestInfo = new HashMap<>();
        requestInfo.put(topicAndPartition, new PartitionOffsetRequestInfo(
                whichTime, 1));
        kafka.javaapi.OffsetRequest request = new kafka.javaapi.OffsetRequest(
                requestInfo, kafka.api.OffsetRequest.CurrentVersion(),
                clientName);
        OffsetResponse response = consumer.getOffsetsBefore(request);
        if (response.hasError()) {
            System.out.println("Error fetching data Offset Data the Broker. Reason: "
                            + response.errorCode(topic, partition));
            return 0;
        }
        long[] offsets = response.offsets(topic, partition);
        return offsets[0];
    }

    private static TreeMap<Integer,PartitionMetadata> findLeader(List<String> seedBrokers,
                                                          int port, String topic) {
        TreeMap<Integer, PartitionMetadata> map = new TreeMap<>();
        for (String seed : seedBrokers) {
            SimpleConsumer consumer = null;
            try {
                consumer = new SimpleConsumer(seed, port, 100000, 64 * 1024,
                        "leaderLookup" + System.currentTimeMillis());
                List<String> topics = Collections.singletonList(topic);
                TopicMetadataRequest req = new TopicMetadataRequest(topics);
                TopicMetadataResponse resp = consumer.send(req);

                List<TopicMetadata> metaData = resp.topicsMetadata();
                for (TopicMetadata item : metaData) {
                    for (PartitionMetadata part : item.partitionsMetadata()) {
                        map.put(part.partitionId(), part);
                    }
                }
            } catch (Exception e) {
                System.out.println("Error communicating with Broker [" + seed + "] to find Leader for [" + topic + ", ] Reason: " + e);
            } finally {
                if (consumer != null) {
                    consumer.close();
                }
            }
        }
        return map;
    }

    public static void main(String[] args) {
       Long lag = getKafkaTopicLagWithConsumerGroup("10.45.46.116", 9092, "ahkafkatopic71", "kafka_consumer_group_12");
       System.out.println(lag);
    }
}
