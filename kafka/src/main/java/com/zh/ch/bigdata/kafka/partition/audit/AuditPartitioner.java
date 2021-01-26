package com.zh.ch.bigdata.kafka.partition.audit;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author xzc
 * @description 实现审计功能的kafka分区策略，将审计数据发送到最后一个分区
 * @date 2021/01/09
 */
public class AuditPartitioner implements Partitioner {

    private Random random;

    @Override
    public int partition(String topic, Object key, byte[] bytes, Object o1, byte[] bytes1, Cluster cluster) {
        String keyObj = (String) key;
        List<PartitionInfo> partitionInfoList = cluster.availablePartitionsForTopic(topic);
        int partitionCount = partitionInfoList.size();
        int auditPartition = partitionCount - 1;
        return keyObj == null || "".equals(keyObj) || !keyObj.contains("audit") ? random.nextInt(partitionCount - 1) : auditPartition;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {
        random = new Random();
    }
}
