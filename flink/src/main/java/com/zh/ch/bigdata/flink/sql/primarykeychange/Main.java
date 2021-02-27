package com.zh.ch.bigdata.flink.sql.primarykeychange;

import com.zh.ch.bigdata.base.util.file.FileReaderUtil;
import com.zh.ch.bigdata.flink.sql.primarykeychange.bean.Message;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.java.BatchTableEnvironment;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xzc
 * @description
 * @date 2021/02/01
 */
public class Main {

    public static void main(String[] args) throws Exception {

        String jsonConfig = FileReaderUtil.getString("C:\\Users\\hadoop\\company\\project\\zstream-poc-20201207\\json\\testdata.json");

        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        BatchTableEnvironment tableEnv = BatchTableEnvironment.create(env);
        env.setParallelism(4);

        Map<String, String> map = new HashMap<>();
        map.put(PriConfig.PRI_JSON_CONFIG, jsonConfig);
        ParameterTool paramTool = ParameterTool.fromMap(map);
        env.getConfig().setGlobalJobParameters(paramTool);

        tableEnv.registerFunction("primaryKeyChangeRecord", new PrimaryKeyChangedRecord());

        DataSet<String> input = env.readTextFile("C:\\Users\\hadoop\\company\\bigdata\\bigdata-2\\flink\\src\\main\\resources\\oggdata.txt");
        DataSet<Message> jsonRootBeanDataSet
                = input.map(new MapFunction<String, Message>() {
            @Override
            public Message map(String s) throws Exception {
                return new Message(s);
            }
        });

        Table table = tableEnv.fromDataSet(jsonRootBeanDataSet);
        tableEnv.registerTable("view", table);


        // use the function in SQL
        Table queryResult = tableEnv.sqlQuery("SELECT primaryKeyChangeRecord(data) as data FROM view");

        DataSet<Message> result = tableEnv.toDataSet(queryResult, Message.class);

        result.print();

    }

}
