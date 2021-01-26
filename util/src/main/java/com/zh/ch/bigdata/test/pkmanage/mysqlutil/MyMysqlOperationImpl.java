//package com.zh.ch.bigdata.test.pkmanage.mysqlutil;
//
//import com.ztesoft.zstream.flink.pkmanage.exception.ProjectException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.HashMap;
//
///**
// * @author xzc
// * @description mysql实现类
// * @date 2021/01/05
// */
//public class MyMysqlOperationImpl extends AbstractMysqlOperation{
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(MyMysqlOperationImpl.class);
//
//    /**
//     * 表查询
//     * 代码模板
//     * try {
//     * Statement stmt;
//     * Connection conn = build();
//     * stmt = conn.createStatement();
//     * ResultSet rs = stmt.executeQuery(sql);
//     * LOGGER.info("sql语句执行成功");
//     * while (rs.next()) {
//     * // 通过字段检索并打印数据
//     * int id = rs.getInt("a");
//     * System.out.println(id);
//     * }
//     * rs.close();
//     * stmt.close();
//     * conn.close();
//     * } catch (ClassNotFoundException | SQLException | ProjectException | IOException e) {
//     * LOGGER.error("表查询异常，sql语句为 {}", sql);
//     * throw e;
//     * }
//     *
//     * @param sql sql语句
//     * @throws Exception 异常
//     */
//    @Override
//    public void queryTable(String sql) throws Exception {
//        try {
//            Statement stmt;
//            Connection conn = build();
//            stmt = conn.createStatement();
//            ResultSet rs = stmt.executeQuery(sql);
//            LOGGER.info("sql语句执行成功");
//            while (rs.next()) {
//                // 通过字段检索并打印数据
//                int id = rs.getInt("a");
//                System.out.println(id);
//            }
//            rs.close();
//            stmt.close();
//            conn.close();
//        }
//        catch (ClassNotFoundException | SQLException | ProjectException | IOException e) {
//            LOGGER.error("表查询异常，sql语句为 {}", sql);
//            throw e;
//        }
//    }
//
//    /**
//     * 创建表
//     *
//     * @param tableName 表明
//     * @throws Exception 异常
//     */
//    @Override
//    public void createTable(String tableName) throws Exception {
//
//    }
//
//    /**
//     * 删除表
//     *
//     * @param tableName 表明
//     * @throws Exception 异常
//     */
//    @Override
//    public void deleteTable(String tableName) throws Exception {
//
//    }
//
//    /**
//     * 插入数据到"data_primarykey_changed_record"表中
//     *
//     * @param tableName 表名称
//     * @param values 待插入的值
//     * @throws Exception 异常
//     */
//    @Override
//    public void insertTable(String tableName, String[] values) throws Exception {
//        PreparedStatement preparedStatement;
//        ResultSet rs;
//        Connection conn = build();
//        preparedStatement = conn.prepareStatement(
//                "insert into " + tableName + "(table_name, before_id, after_id, create_time) values(?, ?, ?, ?)");
//        preparedStatement.setString(1, values[0]);
//        preparedStatement.setString(2, values[1]);
//        preparedStatement.setString(3, values[2]);
//        preparedStatement.setString(4, values[3]);
//        preparedStatement.executeUpdate();
//    }
//}
