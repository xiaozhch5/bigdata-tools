package com.zh.ch.bigdata.flink.sql.primarykeychange;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xzc
 * @description 查询目标表中的所有columns
 * @date 2021/02/02
 */
public class QueryTable {

    private static final Logger LOG = LoggerFactory.getLogger(QueryTable.class);

    /**
     * 获取数据库中所有表的名称
     * @param dbName 数据库名称
     * @return List<String>
     */
    public static Map<String, List<String>> getDatabaseTableList(Connection dbConn, String dbName) {
        try {
            List<String> tableList = new ArrayList<>();
            Map<String, List<String>> databaseTableMap = new HashMap<>();
            Statement statement = dbConn.createStatement();
            // select column_key from information_schema.columns where table_schema='iahdb' and table_name='ct_cust_info' and column_key='PRI';
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("select table_name from information_schema.tables where table_schema=");
            stringBuilder.append("'");
            stringBuilder.append(dbName);
            stringBuilder.append("'");
            ResultSet resultSet = statement.executeQuery(stringBuilder.toString());
            while (resultSet.next()) {
                String tableName = resultSet.getString("table_name");
                tableList.add(tableName);
            }
            databaseTableMap.put(dbName, tableList);

            return databaseTableMap;
        }
        catch (SQLException e) {
            LOG.error("数据库 {} 表名查询失败", dbName, e);
        }
        // 返回null则表示该表没有主键
        return null;
    }

    /**
     * 获取数据库中所有表的字段，以map形式返回
     * @param dbConn dbConn
     * @param dbName dbName
     * @return Map<String, List<String>>
     */
    public static Map<String, List<String>> getTableColumnList(Connection dbConn, String dbName) {
        String tableName = null;
        String sql = null;
        try {
            Statement statement = dbConn.createStatement();
            Map<String, List<String>> tableNameList = getDatabaseTableList(dbConn, dbName);

            Map<String, List<String>> dbNameColumnListMap = new HashMap<>();
            if (tableNameList != null) {
                for (String table : tableNameList.get(dbName)) {
                    List<String> columnList = new ArrayList<>();
                    tableName = table;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("select column_name from information_schema.columns where table_schema=");
                    stringBuilder.append("'");
                    stringBuilder.append(dbName);
                    stringBuilder.append("'");
                    stringBuilder.append(" and table_name= ");
                    stringBuilder.append("'");
                    stringBuilder.append(tableName);
                    stringBuilder.append("'");
                    sql = stringBuilder.toString();
                    ResultSet resultSet = statement.executeQuery(stringBuilder.toString());
                    while (resultSet.next()) {
                        String column = resultSet.getString("column_name");
                        columnList.add(column);
                    }
                    dbNameColumnListMap.put(tableName, columnList);
                }
            }
            return dbNameColumnListMap;
        } catch (SQLException e) {
            LOG.error("数据库{}中的{}表字段查询失败, sql为{}", dbName, tableName, sql, e);
        }
        return null;
    }

    /**
     * 获取目标库表的主键
     * @param dbName 数据库名
     * @param tableName 表名称
     * @return 主键
     */
    public static List<String> queryTablePrimaryKeyList(Connection dbConn, String dbName, String tableName) {
        try {
            List<String> primaryKeys = new ArrayList<>();
            Statement statement = dbConn.createStatement();
            // select column_key from information_schema.columns where table_schema='iahdb' and table_name='ct_cust_info' and column_key='PRI';
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("select column_name from information_schema.columns where table_schema=");
            stringBuilder.append("'");
            stringBuilder.append(dbName);
            stringBuilder.append("'");
            stringBuilder.append(" and table_name=");
            stringBuilder.append("'");
            stringBuilder.append(tableName);
            stringBuilder.append("'");
            stringBuilder.append(" and column_key='PRI'");
            ResultSet resultSet = statement.executeQuery(stringBuilder.toString());
            while (resultSet.next()) {
                String primaryKey = resultSet.getString("column_name");
                primaryKeys.add(primaryKey);
            }
            return primaryKeys;
        }
        catch (SQLException e) {
            LOG.error("数据库 {} 表 {} 主键失败", dbName, tableName);
        }
        // 返回null则表示该表没有主键
        return null;
    }
}
