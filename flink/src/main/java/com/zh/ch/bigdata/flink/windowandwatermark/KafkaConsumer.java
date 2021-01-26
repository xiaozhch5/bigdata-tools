package com.zh.ch.bigdata.flink.windowandwatermark;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.util.Collector;

import java.util.Properties;
import java.util.stream.Stream;

public class KafkaConsumer {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment streamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment();

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "hadoop1:9092,hadoop2:9092,hadoop3:9092");
        properties.setProperty("group.id", "flink-consumer-3");

        FlinkKafkaConsumer<String> myConsumer = new FlinkKafkaConsumer<String>("topic_3",new SimpleStringSchema(),properties);

        myConsumer.setStartFromEarliest();
        myConsumer.setStartFromGroupOffsets();

        streamExecutionEnvironment
                .setParallelism(2)
                .setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        DataStream<Tuple2<String,Integer>> stream = streamExecutionEnvironment.addSource(myConsumer)
                .flatMap((String lines, Collector<Tuple2<String,Integer>> out) ->
                        Stream.of(lines.split(","))
                                .forEach(a -> out.collect(Tuple2.of(a,1))))
                .returns(Types.TUPLE(Types.STRING,Types.INT))
                .keyBy(0)
                //.window(TumblingEventTimeWindows.of(Time.seconds(5)))
                .sum(1)
                ;

        //stream.writeAsText("C:\\Users\\yaowentao\\Desktop\\a");
        stream.print();
        streamExecutionEnvironment.execute("my first stream flink");
    }
}
