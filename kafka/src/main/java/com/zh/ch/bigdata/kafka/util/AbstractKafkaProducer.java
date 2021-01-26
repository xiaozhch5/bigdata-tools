package com.zh.ch.bigdata.kafka.util;

import lombok.Getter;
import lombok.Setter;

/**
 * @author xzc
 * @description kafkaProducer抽象类
 * @date 2021/01/09
 */
@Setter
@Getter
public class AbstractKafkaProducer implements IKafkaProducer {

    /**
     * kafkaProducer配置文件路径
     */
    protected String kafkaProducerConfigFilePath;

    /**
     * 消息发送的时间间隔
     */
    protected Long sleepTime;

    /**
     * 执行kafka消息发送
     *
     * @throws Exception 异常
     */
    @Override
    public void execute() throws Exception {

    }
}
