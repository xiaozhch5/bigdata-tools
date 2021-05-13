package com.zh.ch.bigdata.flinklearning.sink;

import org.apache.derby.jdbc.EmbeddedXADataSource;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.connector.jdbc.JdbcExactlyOnceOptions;
import org.apache.flink.connector.jdbc.JdbcExecutionOptions;
import org.apache.flink.connector.jdbc.JdbcSink;
import org.apache.flink.connector.jdbc.JdbcStatementBuilder;
import org.apache.flink.connector.jdbc.xa.JdbcXaSinkFunction;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author xzc
 * @description
 * @date 2021/05/13
 */
public class MysqlSinkFunction {

    private final String sqlStatement = "";

    public static SinkFunction mysqlJdbcStatementBuilder = JdbcSink.exactlyOnceSink("insert into books (id, title, author, price, qty) values (?,?,?,?,?)",
                new MysqlJdbcStatementBuilder(),
                JdbcExecutionOptions.builder().build(),
                JdbcExactlyOnceOptions.defaults(),
                () -> {
                    // create a driver-specific XA DataSource
                    EmbeddedXADataSource ds = new EmbeddedXADataSource();
                    ds.setDatabaseName("my_db");
                    return ds;
                });


    static class MysqlJdbcStatementBuilder implements JdbcStatementBuilder<Tuple2<String, Integer>> {


        @Override
        public void accept(PreparedStatement preparedStatement, Tuple2<String, Integer> stringIntegerTuple2) throws SQLException {
            preparedStatement.setString(1, stringIntegerTuple2.f0);
            preparedStatement.setInt(2, stringIntegerTuple2.f1);
        }
    }

}
