package com.zh.ch.bigdata.flink.sql.primarykeychange;

import junit.framework.TestCase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class QueryTableTest extends TestCase {

    public void testGetDatabaseTables() throws SQLException {

        String connectionUrl = "jdbc:mysql://10.45.46.116:3306/zstream?useUnicode=true&characterEncoding=UTF-8&useSSL=false";
        String userName = "root";
        String password = "bdp123";

        Connection dbConn = DriverManager.getConnection(connectionUrl, userName, password);



        Map<String, List<String>> tableNameList = QueryTable.getDatabaseTableList(dbConn, "test");
        assert tableNameList != null;
//        tableNameList.forEach(System.out::println);

        Map<String, List<String>> tableColumnListMap = QueryTable.getTableColumnList(dbConn, "test");
        assert tableColumnListMap != null;
        for (Map.Entry<String, List<String>> entry : tableColumnListMap.entrySet()) {
            tableColumnListMap.get(entry.getKey()).forEach(System.out::println);
        }
    }
}