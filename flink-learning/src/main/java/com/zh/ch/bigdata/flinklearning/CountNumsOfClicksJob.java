package com.zh.ch.bigdata.flinklearning;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class CountNumsOfClicksJob {

    private static final Logger log = LoggerFactory.getLogger(CountNumsOfClicksJob.class);


    public static void main(String[] args) throws Exception {
        try {
            StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
            env.setParallelism(1);
            DataStream<String> dataStream = env.addSource(getFlinkKafkaConsumer());
            dataStream.print();
            env.execute();
        } catch (Exception e) {
            log.error("flink数据消费失败", e);
        }
    }


    public static FlinkKafkaConsumer<String> getFlinkKafkaConsumer() {
        FlinkKafkaConsumer<String> flinkKafkaConsumer;
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "hadoop1.lrting.top:6667,hadoop2.lrting.top:6667,hadoop3.lrting.top:6667");
        properties.setProperty("group.id", "test");
        flinkKafkaConsumer = new FlinkKafkaConsumer<>("sample1", new SimpleStringSchema(), properties);
        flinkKafkaConsumer.setStartFromEarliest();
        return flinkKafkaConsumer;
    }

}
