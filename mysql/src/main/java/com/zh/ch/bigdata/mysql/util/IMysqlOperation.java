package com.zh.ch.bigdata.mysql.util;

/**
 * @author xzc
 * @description mysql操作接口
 * @date 2020/12/08
 */
public interface IMysqlOperation {

    /**
     * 表查询
     * @param sql sql语句
     * @throws Exception 异常
     */
    void queryTable(String sql) throws Exception;

    /**
     * 建mysql表
     * @param tableName 表明
     * @throws Exception 异常
     */
    void createTable(String tableName) throws Exception;

    /**
     * 删除表
     * @param tableName 表名
     * @throws Exception 异常
     */
    void deleteTable(String tableName) throws Exception;

    /**
     * 往表中插入数据
     * @param sql sql语句
     * @throws Exception 异常
     */
    void insertTable(String sql) throws Exception;


}
