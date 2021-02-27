package com.zh.ch.bigdata.kafka.producer.example.ogg;

import com.zh.ch.bigdata.base.util.exception.ProjectException;
import com.zh.ch.bigdata.base.util.properties.PropertiesAnalyzeUtil;
import com.zh.ch.bigdata.kafka.util.AbstractKafkaProducer;
import com.zh.ch.bigdata.kafka.util.KafkaClient;
import com.zh.ch.bigdata.kafka.util.KafkaConfigEnv;
import lombok.Getter;
import lombok.Setter;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;


/**
 * @author xzc
 * @description 使用kafkaProducer发送消息到kafka中
 * @date 2020/11/10
 */
@Setter
@Getter
public class OggDataProducer extends AbstractKafkaProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(OggDataProducer.class);

    /**
     * length 发送的总条数
     */
    private Long length;

    /**
     * count length % count == 0 时，产生一条主键变更的数据
     */
    private Long count;

    public OggDataProducer(Long length, Long count) {
        this.length = length;
        this.count = count;
    }

    public OggDataProducer() {

    }

    public String getResource(String fileName) {
        return Objects.requireNonNull(this.getClass().getClassLoader().getResource(fileName)).toString().substring(6);
    }

    /**
     * 执行kafka数据发送
     * @throws InterruptedException 异常类型
     * @throws IOException 异常类型
     * @throws ProjectException 异常类型
     */
    @Override
    public void execute() throws Exception {
        try {
            Producer<String, String> producer = KafkaClient.buildProducer(kafkaProducerConfigFilePath);
            String topicName = PropertiesAnalyzeUtil.getProperty(kafkaProducerConfigFilePath, KafkaConfigEnv.TOPIC_NAME);
            for (Long i = 10L; i < getLength(); i++) {
                // 一秒发送一次数据
                Thread.sleep(1000);
                boolean a = producer.send(new ProducerRecord<String, String>(topicName, String.valueOf(i), GenOggData.generate(i, count))).isDone();
                System.out.println(GenOggData.generate(i, count));
                LOGGER.info("成功发送 {} 条数据到kafka", i);
            }
            producer.close();
        } catch (InterruptedException | IOException | ProjectException exception) {
            if (kafkaProducerConfigFilePath == null) {
                LOGGER.error("kafkaProducer配置文件路径未正确配置，请检查kafkaProducer配置文件路径");
            }
            throw exception;
        }
    }

    public static void main(String[] args) throws Exception {
        Long length = 20L;
        Long count = 2L;
        OggDataProducer oggDataProducer = new OggDataProducer();
        String kafkaProducerConfig = oggDataProducer.getResource("kafka-producer-config.properties");
        oggDataProducer.setKafkaProducerConfigFilePath(kafkaProducerConfig);
        oggDataProducer.setLength(length);
        oggDataProducer.setCount(count);
        oggDataProducer.execute();
    }
}
