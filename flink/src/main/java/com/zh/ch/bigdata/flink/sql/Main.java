package com.zh.ch.bigdata.flink.sql;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.java.BatchTableEnvironment;

public class Main {

    public static void main(String[] args) throws Exception {
        ExecutionEnvironment executionEnvironment = ExecutionEnvironment.getExecutionEnvironment();
        BatchTableEnvironment batchTableEnvironment = BatchTableEnvironment.create(executionEnvironment);

        DataSet<String> input = executionEnvironment.readTextFile("D:\\bigdata\\bigdata\\flink\\src\\main\\resources\\score.csv");
        DataSet<PlayerData> topInput = input.map(new MapFunction<String, PlayerData>() {
            @Override
            public PlayerData map(String s) throws Exception {
                String[] split = s.split(",");
                return new PlayerData(String.valueOf(split[0]),
                        String.valueOf(split[1]),
                        String.valueOf(split[2]),
                        Integer.valueOf(split[3]),
                        Double.valueOf(split[4]),
                        Double.valueOf(split[5]),
                        Double.valueOf(split[6]),
                        Double.valueOf(split[7]),
                        Double.valueOf(split[8])
                );
            }
        });

        Table topScore = batchTableEnvironment.fromDataSet(topInput);
        batchTableEnvironment.registerTable("score", topScore);

        Table queryResult = batchTableEnvironment.sqlQuery(
                "select blocks, player from score"
        );

        DataSet<Result> result = batchTableEnvironment.toDataSet(queryResult, Result.class);

        result.print();

    }

    public static class Result {
        public String player;
        public Double blocks;

        public Result() {
            super();
        }

        public Result(String player, Double blocks) {
            this.player = player;
            this.blocks = blocks;
        }

        @Override
        public String toString() {
            return player + ":" + blocks;
        }
    }
}

