package com.zh.ch.bigdata.kafka.consumer.example;

import org.apache.kafka.clients.consumer.CommitFailedException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class CommonConsumer {
    private static KafkaConsumer<String, String> consumer;
    private static Properties kfkProperties;

    static {
        kfkProperties = new Properties();
        kfkProperties.put("bootstrap.servers", "hadoop1:9092,hadoop2:9092,hadoop3:9092");
        kfkProperties.put("group.id", "kafkatest1");
        kfkProperties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        kfkProperties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    }

    /**
     * 　　* consumer 1 : 自动提交位移
     *
     */
    private static void generalConsumerMessageAutoCommit() {
        kfkProperties.put("enable.auto.commit", true);
        consumer = new KafkaConsumer<>(kfkProperties);
// consumer.subscribe(Collections.singletonList("kafkatest"));
        String topic = "gdkafkatopic1101";
        TopicPartition partition = new TopicPartition(topic, 1);
        List<TopicPartition> lists = new ArrayList<TopicPartition>();
        lists.add(partition);
        consumer.assign(lists);
        consumer.seekToBeginning(lists);

// consumer.seek(partition, 0);
        try {
            while (true) {

                ConsumerRecords<String, String> records = consumer.poll(8000);
                for (ConsumerRecord<String, String> record : records) {
                    System.out.println(record.timestamp() + "," + record.topic() + "," + record.partition() + "," + record.offset() + " " + record.key() + "," + record.value());
                }
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        } finally {
            consumer.close();
        }
    }

    /**
     * 　　* consumer 2 : 手动提交位移
     *
     */
    private static void generalConsumerMessageManualCommitSync() {
        kfkProperties.put("enable.auto.commit", false);
        consumer = new KafkaConsumer<>(kfkProperties);
        consumer.subscribe(Collections.singletonList("oggsrc-test-6"));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(80);
            for (ConsumerRecord<String, String> record : records) {
                System.out.println(record.timestamp() + "," + record.topic() + "," + record.partition() + "," + record.offset() + " " + record.key() + "," + record.value());
            }
            try {
                consumer.commitSync();
            } catch (CommitFailedException e) {
                System.out.println("commit failed msg" + e.getMessage());
            }

        }

    }

    /**
     * 　　* consumer 3 异步提交位移
     *
     */
    private static void generalConsumerMessageManualCommitAsync() {
        kfkProperties.put("enable.auto.commit", false);
        consumer = new KafkaConsumer<>(kfkProperties);
        consumer.subscribe(Collections.singletonList("kafkatest"));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(80);
            for (ConsumerRecord<String, String> record : records) {
                System.out.println(record.timestamp() + "," + record.topic() + "," + record.partition() + "," + record.offset() + " " + record.key() + "," + record.value());

            }

            consumer.commitAsync();
        }
    }

    /**
     * 　　* consumer 4 异步提交位移带回调
     *
     */
    private static void generalConsumerMessageManualCommitAsyncWithCallBack() {
        kfkProperties.put("enable.auto.commit", false);
        consumer = new KafkaConsumer<>(kfkProperties);
        consumer.subscribe(Collections.singletonList("kafkatest"));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(80);
            for (ConsumerRecord<String, String> record : records) {
                System.out.println(record.timestamp() + "," + record.topic() + "," + record.partition() + "," + record.offset() + " " + record.key() + "," + record.value());
            }

            consumer.commitAsync((offsets, e) -> {
                if (null != e) {
                    System.out.println("commit async callback error" + e.getMessage());
                    System.out.println(offsets);
                }
            });
        }
    }


    /**
     * 　　　　* consumer 5 混合提交方式
     *
     */
    private static void generalMixSyncAndAsyncCommit() {
        kfkProperties.put("enable.auto.commit", false);
        consumer = new KafkaConsumer<>(kfkProperties);
        consumer.subscribe(Collections.singletonList("kafkatest"));
        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(80);
                for (ConsumerRecord<String, String> record : records) {
                    System.out.println(record.timestamp() + "," + record.topic() + "," + record.partition() + "," + record.offset() + " " + record.key() + "," + record.value());
                }

                consumer.commitAsync();
            }

        } catch (Exception e) {
            System.out.println("commit async error: " + e.getMessage());
        } finally {
            try {
                consumer.commitSync();
            } finally {
                consumer.close();
            }
        }
    }

    /**
     * 　　*
     * 　　* @param args
     * 　　* 主函数测试
     *
     */
    public static void main(String[] args) {
        CommonConsumer.generalConsumerMessageAutoCommit();
    }

}