//package com.zh.ch.bigdata.test.pkmanage.mysqlutil;
//
//import java.util.HashMap;
//
///**
// * 1. 为了使用本Operation类操作数据库，需要继承Operation类，并重写相关方法，
// * Operation类中的方法提供了相关模板。
// * 2. 继承Operation类之后，可对mysqlConfigPropertiesFilePath参数进行赋值，
// * 如果不赋值，则默认使用resource目录下的配置文件
// *
// * @author xzc
// * @description mysql数据库操作
// * @date 2020/12/08
// */
//public abstract class AbstractMysqlOperation extends MysqlClient implements IMysqlOperation {
//
//    /**
//     * 表查询
//     * 代码模板
//     * try {
//     *     Statement stmt;
//     *     Connection conn = build();
//     *     stmt = conn.createStatement();
//     *     ResultSet rs = stmt.executeQuery(sql);
//     *     LOGGER.info("sql语句执行成功");
//     *     while (rs.next()) {
//     *         // 通过字段检索并打印数据
//     *         int id = rs.getInt("a");
//     *         System.out.println(id);
//     *     }
//     *     rs.close();
//     *     stmt.close();
//     *     conn.close();
//     * } catch (ClassNotFoundException | SQLException | ProjectException | IOException e) {
//     *     LOGGER.error("表查询异常，sql语句为 {}", sql);
//     *     throw e;
//     * }
//     * @param sql sql语句
//     * @throws Exception 异常
//     */
//    @Override
//    public abstract void queryTable(String sql) throws Exception;
//
//    /**
//     * 创建表
//     * @param tableName 表明
//     * @throws Exception 异常
//     */
//    @Override
//    public abstract void createTable(String tableName) throws Exception;
//
//    /**
//     * 删除表
//     * @param tableName 表明
//     * @throws Exception 异常
//     */
//    @Override
//    public abstract void deleteTable(String tableName) throws Exception;
//
//    /**
//     * 往表中插入数据
//     * @param tableName 需要插入的表名称
//     * @param values 需要插入的数据
//     * @throws Exception 异常
//     */
//    @Override
//    public abstract void insertTable(String tableName, String[] values) throws Exception;
//
//}
