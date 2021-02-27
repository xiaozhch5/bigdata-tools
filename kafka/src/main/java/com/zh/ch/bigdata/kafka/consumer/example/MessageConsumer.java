package com.zh.ch.bigdata.kafka.consumer.example;

import com.zh.ch.bigdata.base.util.exception.ProjectException;
import com.zh.ch.bigdata.kafka.util.KafkaClient;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.Objects;

/**
 * @author xzc
 * @description 使用KafkaConsumer接受从kafka发送过来的消息
 * @date 2020/11/10
 */
public class MessageConsumer {

    public String getResource(String fileName) {
        return Objects.requireNonNull(this.getClass().getClassLoader().getResource(fileName))
                .toString()
                .substring(6);
    }

    public static void main(String[] args) throws IOException, ProjectException {

        MessageConsumer receiver = new MessageConsumer();
        String kafkaConfigFilePath = receiver.getResource("kafka-config.properties");
        Consumer<String, String> consumer = KafkaClient.buildConsumer(kafkaConfigFilePath);
        consumer.subscribe(Collections.singletonList("un_account_topic"));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(1L);
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf(
                        "offset = %d, key = %s, value = %s%n",
                        record.offset(), record.key(), record.value());
            }
        }
    }
}
