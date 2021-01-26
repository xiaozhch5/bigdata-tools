package com.zh.ch.bigdata.flink.connectors.kafka;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.apache.flink.types.Row;
import org.apache.flink.util.Collector;

import java.util.Properties;
import java.util.stream.Stream;

public class TableDemo {

    /**
     * 官网API网址：https://ci.apache.org/projects/flink/flink-docs-release-1.11/zh/dev/table/connectors/kafka.html#how-to-create-a-kafka-table
     *
     *所有参数：
     * connector
     * topic
     * properties.bootstrap.servers
     * properties.group.id
     * format
     *  scan.startup.mode
     *  scan.startup.specific-offsets
     *  scan.startup.timestamp-millis
     *  sink.partitioner
     *
     */
    private static final String KAFKA_SQL = "CREATE TABLE kafkaTable (\n" +
            " code STRING," +
            " total_emp INT" +
            ") WITH (" +
            " 'connector' = 'kafka'," +
            " 'topic' = 'flink_dwd_test1'," +
            " 'properties.bootstrap.servers' = 'hadoop1.lrting.top:9092'," +
            " 'properties.group.id' = 'test1'," +
            " 'format' = 'json'," +
            " 'scan.startup.mode' = 'earliest-offset'" +
            ")";



    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment Env = StreamExecutionEnvironment.getExecutionEnvironment();

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "hadoop1:9092,hadoop2:9092,hadoop3:9092");
        properties.setProperty("group.id", "flink-consumer-3");

        FlinkKafkaConsumer<String> myConsumer = new FlinkKafkaConsumer<String>("topic_3",new SimpleStringSchema(),properties);

        myConsumer.setStartFromEarliest();
        myConsumer.setStartFromGroupOffsets();


        Env.setParallelism(2).setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        DataStream<Tuple2<String,Integer>> stream = Env.addSource(myConsumer)
                .flatMap((String lines, Collector<Tuple2<String,Integer>> out) ->
                        Stream.of(lines.split(","))
                                .forEach(a -> out.collect(Tuple2.of(a,1))))
                .returns(Types.TUPLE(Types.STRING,Types.INT))
                .keyBy(0)
                //.window(TumblingEventTimeWindows.of(Time.seconds(5)))
                .sum(1)
                ;

        stream.print();
        Env.execute("my first stream flink");


    }
}
