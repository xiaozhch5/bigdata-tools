package com.zh.ch.bigdata.flink.examples.windowuserpv;

import com.zh.ch.bigdata.java.PropertiesAnalyzeUtil;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.tuple.Tuple5;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableConfig;
import org.apache.flink.table.api.java.StreamTableEnvironment;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.Properties;

/**
 * @author xzc
 * @description 窗口统计
 * @date 2021/01/04
 */
public class FlinkSqlWindowUserPv {


    public String getResource(String fileName) {
        return Objects.requireNonNull(this.getClass().getClassLoader().getResource(fileName)).toString().substring(6);
    }

    public static void main(String[] args) throws Exception {

        FlinkSqlWindowUserPv flinkSqlWindowUserPv = new FlinkSqlWindowUserPv();

        TableConfig tc = new TableConfig();

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        EnvironmentSettings bsSettings = EnvironmentSettings.newInstance().useBlinkPlanner().inStreamingMode().build();
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env, bsSettings);

        env.setParallelism(2);

        Properties props = new Properties();
        props.setProperty(WindowUserPvConfig.BOOTSTRAP_SERVERS,
                PropertiesAnalyzeUtil.getProperty(flinkSqlWindowUserPv.getResource("example-window-user-pv-config.properties"),
                        WindowUserPvConfig.BOOTSTRAP_SERVERS));
        props.setProperty(WindowUserPvConfig.ZOOKEEPER_CONNECT,
                PropertiesAnalyzeUtil.getProperty(flinkSqlWindowUserPv.getResource("example-window-user-pv-config.properties"),
                        WindowUserPvConfig.ZOOKEEPER_CONNECT));
        props.setProperty(WindowUserPvConfig.KEY_DESERIALIZER,
                PropertiesAnalyzeUtil.getProperty(flinkSqlWindowUserPv.getResource("example-window-user-pv-config.properties"),
                        WindowUserPvConfig.KEY_DESERIALIZER));
        props.setProperty(WindowUserPvConfig.VALUE_DESERIALIZER,
                PropertiesAnalyzeUtil.getProperty(flinkSqlWindowUserPv.getResource("example-window-user-pv-config.properties"),
                        WindowUserPvConfig.VALUE_DESERIALIZER));
        props.setProperty(WindowUserPvConfig.GROUP_ID,
                PropertiesAnalyzeUtil.getProperty(flinkSqlWindowUserPv.getResource("example-window-user-pv-config.properties"),
                        WindowUserPvConfig.GROUP_ID));


        FlinkKafkaConsumer<String> myConsumer = new FlinkKafkaConsumer<String>("productInfo-test-2", new SimpleStringSchema(), props);
        DataStream<String> stream = env.addSource(myConsumer);
        DataStream<Tuple3<Long, Long, Long>> map = stream.map(new MapFunction<String, Tuple3<Long, Long, Long>>() {
            private static final long serialVersionUID = 1471936326697828381L;
            @Override
            public Tuple3<Long, Long, Long> map(String value) throws Exception {
                String[] split = value.split(",");
                return new Tuple3<Long, Long, Long>(Long.valueOf(split[0]), Long.valueOf(split[1]), Long.valueOf(split[2]));
            }
        });
//        map.print(); //打印流数据
        //注册为user表
        tableEnv.registerDataStream("ProductInfo", map, "productId, productPrice, productSalesVolume, proctime.proctime");

        //执行sql查询滚动窗口 统计五秒内商品的总额
        Table sqlQuery = tableEnv.sqlQuery("SELECT TUMBLE_END(proctime, INTERVAL '5' SECOND) as processTime,"
                + "productId,sum(productSalesVolume) as productTotalSales "
                + "FROM ProductInfo "
                + "GROUP BY TUMBLE(proctime, INTERVAL '5' SECOND), productId");
        //Table 转化为 DataStream
        DataStream<Tuple3<Timestamp, Long, Long>> appendStream = tableEnv.toAppendStream(sqlQuery, Types.TUPLE(Types.SQL_TIMESTAMP, Types.LONG, Types.LONG));
        appendStream.print();
        //sink to mysql
        appendStream.map(new MapFunction<Tuple3<Timestamp, Long, Long>, ProductInfo>() {
            private static final long serialVersionUID = -4770965496944515917L;
            @Override
            public ProductInfo map(Tuple3<Timestamp, Long, Long> value) throws Exception {
                return new ProductInfo(value.f0.toString(), value.f1, value.f2);
            }
        }).addSink(new SinkProductSalesToMysql());
        env.execute("userPv from Kafka");
    }
}
