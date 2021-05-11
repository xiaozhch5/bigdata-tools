package com.zh.ch.bigdata.flinklearning;

import com.alibaba.fastjson.JSONObject;
import org.apache.flink.api.common.eventtime.*;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.tuple.Tuple4;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.runtime.operators.util.AssignerWithPeriodicWatermarksAdapter;
import org.apache.flink.util.Collector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Properties;

public class CountNumsOfClicksJob {

    private static final Logger log = LoggerFactory.getLogger(CountNumsOfClicksJob.class);


    public static void main(String[] args) throws Exception {
        try {
            StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
            env.setParallelism(1);
            DataStreamSource<String> dataStream = env.addSource(getFlinkKafkaConsumer());

            DataStream<Tuple4<String, String, String, String>> points = dataStream.map(new RichMapFunction<String, Tuple4<String, String, String, String>>() {
                @Override
                public Tuple4<String, String, String, String> map(String input) {
                    Tuple4<String, String ,String, String> result = new Tuple4<>();
                    JSONObject jsonObject = JSONObject.parseObject(input);
                    String x = jsonObject.getJSONArray("points").getJSONObject(0).getString("x");
                    String y = jsonObject.getJSONArray("points").getJSONObject(0).getString("y");
                    String key = jsonObject.getJSONArray("points").getJSONObject(0).getJSONArray("customdata").get(0).toString();
                    String timestamp = jsonObject.getString("timestamp");
                    result.setFields(x, y, timestamp, key);
                    return result;
                }
            });

            SerializableTimestampAssigner<Tuple4<String, String, String, String>> timestampAssigner = new SerializableTimestampAssigner<Tuple4<String, String, String, String>>() {
                @Override
                public long extractTimestamp(Tuple4<String, String, String, String> element, long recordTimestamp) {
                    return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(element.f2, new ParsePosition(0)).getTime();
                }
            };

            SingleOutputStreamOperator<Tuple4<String, String, String, String>> singleOutputStreamOperator = points.assignTimestampsAndWatermarks(
                    WatermarkStrategy
                            .<Tuple4<String, String, String, String>>forBoundedOutOfOrderness(Duration.ofSeconds(5))
                            .withTimestampAssigner(timestampAssigner)
            );

            singleOutputStreamOperator.flatMap(new FlatMapFunction<Tuple4<String, String, String, String>, Tuple2<Tuple3<String, String, String>, Integer>>() {
                @Override
                public void flatMap(Tuple4<String, String, String, String> value, Collector<Tuple2<Tuple3<String, String, String>, Integer>> out) throws Exception {

                    out.collect(new Tuple2<>(new Tuple3<>(value.f0, value.f1, value.f3), 1));
                }
            }).keyBy(event -> event.f0.f2).sum(1).print();

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
        flinkKafkaConsumer.setStartFromLatest();
        return flinkKafkaConsumer;
    }

}
