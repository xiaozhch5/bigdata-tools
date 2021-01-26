package com.zh.ch.bigdata.kafka.util;

import com.zh.ch.bigdata.base.util.exception.ProjectException;
import com.zh.ch.bigdata.base.util.java.PropertiesAnalyzeUtil;
import com.zh.ch.bigdata.base.util.java.StringJointUtil;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * @author xzc
 * @description kafka客户端工具，读取配置文件建立与kafka server的连接
 * @date 2020/11/20
 */
public class KafkaClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaClient.class);

    /**
     * 设置kafka配置项
     * @param kafkaConfigProperties kafka-config.properties配置文件路径
     * @return Properties
     * @throws ProjectException 全局异常
     * @throws IOException IOException
     */
    public static Properties setKafkaConfigProperties(String kafkaConfigProperties) throws ProjectException, IOException {
        Properties props = new Properties();
        props.put(KafkaConfigEnv.BOOTSTRAP_SERVERS, PropertiesAnalyzeUtil.getProperty(kafkaConfigProperties, KafkaConfigEnv.BOOTSTRAP_SERVERS));
        props.put(KafkaConfigEnv.REQUEST_REQUIRED_ACKS, PropertiesAnalyzeUtil.getProperty(kafkaConfigProperties, KafkaConfigEnv.REQUEST_REQUIRED_ACKS));
        props.put(KafkaConfigEnv.RETRIES, PropertiesAnalyzeUtil.getProperty(kafkaConfigProperties, KafkaConfigEnv.RETRIES));
        props.put(KafkaConfigEnv.BATCH_SIZE, PropertiesAnalyzeUtil.getProperty(kafkaConfigProperties, KafkaConfigEnv.BATCH_SIZE));
        props.put(KafkaConfigEnv.LINGER_MS, PropertiesAnalyzeUtil.getProperty(kafkaConfigProperties, KafkaConfigEnv.LINGER_MS));
        props.put(KafkaConfigEnv.BUFFER_MEMORY, PropertiesAnalyzeUtil.getProperty(kafkaConfigProperties, KafkaConfigEnv.BUFFER_MEMORY));
        return props;
    }

    /**
     * 设置kafka配置项
     * @param kafkaConfigProperties kafka-config.properties配置文件路径
     * @return Properties
     * @throws ProjectException 全局异常
     * @throws IOException IOException
     */
    public static Properties setKafkaProducerConfigProperties(String kafkaConfigProperties) throws ProjectException, IOException {
        Properties props = setKafkaConfigProperties(kafkaConfigProperties);
        props.put(KafkaConfigEnv.KEY_SERIALIZER, PropertiesAnalyzeUtil.getProperty(kafkaConfigProperties, KafkaConfigEnv.KEY_SERIALIZER));
        props.put(KafkaConfigEnv.VALUE_SERIALIZER, PropertiesAnalyzeUtil.getProperty(kafkaConfigProperties, KafkaConfigEnv.VALUE_SERIALIZER));
        LOGGER.info("kafka-properties配置文件加载完成");
        traverseProperties(props);
        return props;
    }

    /**
     * 设置kafka配置项
     * @param kafkaConfigProperties kafka-config.properties配置文件路径
     * @return Properties
     * @throws ProjectException 全局异常
     * @throws IOException IOException
     */
    public static Properties setKafkaConsumerConfigProperties(String kafkaConfigProperties) throws ProjectException, IOException {
        Properties props = setKafkaConfigProperties(kafkaConfigProperties);
        props.put(KafkaConfigEnv.KEY_DESERIALIZER, PropertiesAnalyzeUtil.getProperty(kafkaConfigProperties, KafkaConfigEnv.KEY_DESERIALIZER));
        props.put(KafkaConfigEnv.VALUE_DESERIALIZER, PropertiesAnalyzeUtil.getProperty(kafkaConfigProperties, KafkaConfigEnv.VALUE_DESERIALIZER));
        props.put(KafkaConfigEnv.GROUP_ID, PropertiesAnalyzeUtil.getProperty(kafkaConfigProperties, KafkaConfigEnv.GROUP_ID));
        props.put(KafkaConfigEnv.AUTO_OFFSET_RESET, PropertiesAnalyzeUtil.getProperty(kafkaConfigProperties, KafkaConfigEnv.AUTO_OFFSET_RESET));
        props.put(KafkaConfigEnv.AUTO_COMMIT_ENABLE, PropertiesAnalyzeUtil.getProperty(kafkaConfigProperties, KafkaConfigEnv.AUTO_COMMIT_ENABLE));
        LOGGER.info("kafka-properties配置文件加载完成");
        traverseProperties(props);
        return props;
    }

    /**
     * build kafkaProducer
     * @param kafkaConfigFilePath kafka-config.properties配置文件路径
     * @return KafkaProducer
     * @throws ProjectException 全局异常
     * @throws IOException IOException
     */
    public static KafkaProducer<String, String> buildProducer(String kafkaConfigFilePath) throws ProjectException, IOException {
        Properties props = setKafkaProducerConfigProperties(kafkaConfigFilePath);
        return new KafkaProducer<>(props);
    }

    /**
     * build KafkaConsumer
     * @param kafkaConfigFilePath kafka-config.properties配置文件路径
     * @return KafkaConsumer
     * @throws ProjectException 全局异常
     * @throws IOException IOException
     */
    public static KafkaConsumer<String, String> buildConsumer(String kafkaConfigFilePath) throws ProjectException, IOException {
        Properties props = setKafkaConsumerConfigProperties(kafkaConfigFilePath);
        return new KafkaConsumer<>(props);
    }

    public static void traverseProperties(Properties props) {
        LOGGER.info("==============配置起始项================");
        for (Map.Entry<Object, Object> entry : props.entrySet()) {
            Object key = entry.getKey();
            Object value = entry.getValue();
            LOGGER.info(StringJointUtil.getString(key.toString(), "=", value.toString()));
        }
        LOGGER.info("==============配置结束项================");
    }
}
