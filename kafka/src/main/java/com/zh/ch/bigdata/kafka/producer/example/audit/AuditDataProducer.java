package com.zh.ch.bigdata.kafka.producer.example.audit;

import com.zh.ch.bigdata.base.util.properties.PropertiesAnalyzeUtil;
import com.zh.ch.bigdata.base.util.strings.RandomStringUtil;
import com.zh.ch.bigdata.kafka.util.AbstractKafkaProducer;
import com.zh.ch.bigdata.kafka.util.KafkaClient;
import com.zh.ch.bigdata.kafka.util.KafkaConfigEnv;
import lombok.Getter;
import lombok.Setter;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Objects;

/**
 * @author xzc
 * @description 生成audit数据并发送到kafka指定分区上
 * @date 2021/01/09
 */
@Setter
@Getter
public class AuditDataProducer extends AbstractKafkaProducer {

    /**
     * length 发送的总条数
     */
    private Long length;

    /**
     * count length % count == 0 时，产生一条key为audit的数据
     */
    private Long count;

    public String getResource(String fileName) {
        return Objects.requireNonNull(this.getClass().getClassLoader().getResource(fileName)).toString().substring(6);
    }

    public AuditDataProducer(Long length, Long count) {
        this.length = length;
        this.count = count;
    }

    public AuditDataProducer(){

    }

    /**
     * 执行kafka消息发送
     *
     * @throws Exception 异常
     */
    @Override
    public void execute() throws Exception {
        Producer<String, String> producer = KafkaClient.buildProducer(kafkaProducerConfigFilePath);
        String topicName = PropertiesAnalyzeUtil.getProperty(kafkaProducerConfigFilePath, KafkaConfigEnv.TOPIC_NAME);
        String auditString = RandomStringUtil.randomAlphanumeric(5);
        String anotherString = RandomStringUtil.randomAlphanumeric(5);
        ProducerRecord<String, String> producerRecord;
        for (int i = 0; i < length; i++) {
            if (i % count == 0) {
                producerRecord = new ProducerRecord<>(topicName, "audit", auditString);
            }
            else {
                producerRecord = new ProducerRecord<>(topicName, "another", anotherString);
            }
            Thread.sleep(sleepTime);
            producer.send(producerRecord);
        }
    }

    public static void main(String[] args) throws Exception {
        AuditDataProducer auditDataProducer = new AuditDataProducer();
        auditDataProducer.setKafkaProducerConfigFilePath(auditDataProducer.getResource("kafka-producer-config.properties"));
        auditDataProducer.setLength(100L);
        auditDataProducer.setCount(1L);
        auditDataProducer.setSleepTime(100L);
        auditDataProducer.execute();
    }
}
