package com.zh.ch.bigdata.flinklearning;

import com.alibaba.fastjson.JSONObject;
import com.zh.ch.bigdata.flinklearning.sink.MysqlSinkFunction;
import org.apache.derby.jdbc.EmbeddedXADataSource;
import org.apache.flink.api.common.eventtime.*;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.tuple.Tuple4;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.connector.jdbc.JdbcConnectionOptions;
import org.apache.flink.connector.jdbc.JdbcExactlyOnceOptions;
import org.apache.flink.connector.jdbc.JdbcExecutionOptions;
import org.apache.flink.connector.jdbc.JdbcSink;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.assigners.WindowAssigner;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.runtime.operators.util.AssignerWithPeriodicWatermarksAdapter;
import org.apache.flink.util.Collector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Properties;

public class CountNumsOfClicksJob {

    private static final Logger log = LoggerFactory.getLogger(CountNumsOfClicksJob.class);
    private static int i = 0;
    private static String timestamp = "";


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

            singleOutputStreamOperator
                    .flatMap(new FlatMapFunction<Tuple4<String, String, String, String>, Tuple2<Tuple4<String, String, String, String>, Integer>>() {
                        @Override
                        public void flatMap(Tuple4<String, String, String, String> stringStringStringStringTuple4, Collector<Tuple2<Tuple4<String, String, String, String>, Integer>> collector) throws Exception {
                            i = i + 1;
                            if (i == 1) {
                                timestamp = stringStringStringStringTuple4.f2;
                            }
                            collector.collect(new Tuple2<>(stringStringStringStringTuple4, 1));
                        }
                    })
                    .keyBy(event -> event.f0.f3)
                    .window(TumblingEventTimeWindows.of(Time.seconds(5), Time.seconds(Integer.parseInt(timestamp.split(":")[2])/10)))
                    .sum(1)
                    .addSink(JdbcSink.sink(
                            "insert into count_value (t, category, num) values (?, ?, ?)",
                            (statement, value) -> {
                                statement.setTimestamp(1, new Timestamp((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(value.f0.f2, new ParsePosition(0)).getTime()));
                                statement.setInt(2, Integer.parseInt(value.f0.f3));
                                statement.setInt(3, value.f1);
                            },
                            JdbcExecutionOptions.builder()
                                    .withBatchSize(1)
                                    .withBatchIntervalMs(200)
                                    .withMaxRetries(5)
                                    .build(),
                            new JdbcConnectionOptions.JdbcConnectionOptionsBuilder()
                                    .withUrl("jdbc:mysql://hadoop3.lrting.top:3306/flinktest")
                                    .withDriverName("com.mysql.jdbc.Driver")
                                    .withUsername("root")
                                    .withPassword("m98Edicines-")
                                    .build()
                    ));

            env.execute();
        } catch (Exception e) {
            log.error("flink数据消费失败", e);
        }
    }


    public static FlinkKafkaConsumer<String> getFlinkKafkaConsumer() {
        FlinkKafkaConsumer<String> flinkKafkaConsumer;
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "hadoop1.lrting.top:6667,hadoop2.lrting.top:6667,hadoop3.lrting.top:6667");
        properties.setProperty("group.id", "test1");
        flinkKafkaConsumer = new FlinkKafkaConsumer<>("sample1", new SimpleStringSchema(), properties);
        flinkKafkaConsumer.setStartFromLatest();
        return flinkKafkaConsumer;
    }

    static class CountWindow extends RichFlatMapFunction<Tuple2<Tuple3<String, String, String>, Integer>, Tuple2<String, Long>> {

        private transient ValueState<Tuple2<String, Long>> sum;

        @Override
        public void flatMap(Tuple2<Tuple3<String, String, String>, Integer> tuple3StringTuple2, Collector<Tuple2<String, Long>> collector) throws Exception {
            Tuple2<String, Long> currentSum = sum.value();
            currentSum.f0 = tuple3StringTuple2.f0.f2;
            currentSum.f1 += 1;
            sum.update(currentSum);
            if (currentSum.f1 >= 5) {
                collector.collect(new Tuple2<String, Long>(currentSum.f0, currentSum.f1));
                sum.clear();
            }
        }

        @Override
        public void open(Configuration config) {
            ValueStateDescriptor<Tuple2<String, Long>> descriptor =
                    new ValueStateDescriptor<>(
                            "average", // the state name
                            TypeInformation.of(new TypeHint<Tuple2<String, Long>>() {}), // type information
                            Tuple2.of("", 0L)); // default value of the state, if nothing was set
            sum = getRuntimeContext().getState(descriptor);
        }
    }

}
