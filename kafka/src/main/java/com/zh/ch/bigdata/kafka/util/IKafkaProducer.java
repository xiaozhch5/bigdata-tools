package com.zh.ch.bigdata.kafka.util;

/**
 * @author xzc
 * @description kafka发送数据接口
 * @date 2021/01/09
 */
public interface IKafkaProducer {

    /**
     * 执行kafka消息发送
     * @throws Exception 异常
     */
    void execute() throws Exception;
}
