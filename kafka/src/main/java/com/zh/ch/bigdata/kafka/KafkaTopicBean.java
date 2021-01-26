package com.zh.ch.bigdata.kafka;

import lombok.Getter;
import lombok.Setter;

/**
 * @author xzc
 * @description Kafka连接信息
 * @date 2020/11/10
 */
@Setter
@Getter
public class KafkaTopicBean {

    private String topicName;

    private Integer partition;

    private Integer replication;

    private String describe;

}
