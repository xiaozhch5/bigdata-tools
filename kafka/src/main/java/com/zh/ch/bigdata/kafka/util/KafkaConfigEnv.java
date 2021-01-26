package com.zh.ch.bigdata.kafka.util;

/**
 * @author xzc
 * @description kafka使用过程中需要设置的变量
 * @date 2020/11/20
 */
public class KafkaConfigEnv {

    /**
     * kafka集群的地址
     * producer和consumer共用的参数
     */
    public static String BOOTSTRAP_SERVERS = "bootstrap.servers";

    /**
     * producer和consumer共用的参数
     */
    public static String REQUEST_REQUIRED_ACKS = "request.required.acks";

    /**
     * 重试次数
     * producer和consumer共用的参数
     */
    public static String RETRIES = "retries";

    /**
     * 数据大小
     * producer和consumer共用的参数
     */
    public static String BATCH_SIZE = "batch.size";

    /**
     * producer和consumer共用的参数
     */
    public static String LINGER_MS = "linger.ms";

    /**
     * producer和consumer共用的参数
     */
    public static String BUFFER_MEMORY = "buffer.memory";

    /**
     * producer和consumer共用的参数
     */
    public static String TOPIC_NAME = "topicName";

    /**
     * producer参数
     * key的序列化类
     */
    public static String KEY_SERIALIZER = "key.serializer";

    /**
     * producer参数
     * value的序列化参数
     */
    public static String VALUE_SERIALIZER = "value.serializer";

    /**
     * consumer参数
     * key的反序列化参数
     */
    public static String KEY_DESERIALIZER = "key.deserializer";

    /**
     * consumer参数
     * value的反序列参数
     */
    public static String VALUE_DESERIALIZER = "value.deserializer";

    /**
     * consumer参数
     * 消费组名称
     */
    public static String GROUP_ID = "group.id";

    /**
     * consumer参数
     * 消费的offset
     */
    public static String AUTO_OFFSET_RESET = "auto.offset.reset";

    /**
     * consumer参数
     * 是否自动提交offset
     */
    public static String AUTO_COMMIT_ENABLE = "auto.commit.enable";
}
