package com.zh.ch.bigdata.mysql.example;
import com.zh.ch.bigdata.base.util.exception.ProjectException;
import com.zh.ch.bigdata.mysql.util.AbstractMysqlOperation;
import com.zh.ch.bigdata.mysql.util.IMysqlOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author xzc
 * @description 操作mysql数据库
 * @date 2020/12/09
 */
public class MyMysqlOperationImpl extends AbstractMysqlOperation {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyMysqlOperationImpl.class);

    /**
     * 表查询
     * 代码模板
     * try {
     * Statement stmt;
     * Connection conn = build();
     * stmt = conn.createStatement();
     * ResultSet rs = stmt.executeQuery(sql);
     * LOGGER.info("sql语句执行成功");
     * while (rs.next()) {
     * // 通过字段检索并打印数据
     * int id = rs.getInt("a");
     * System.out.println(id);
     * }
     * rs.close();
     * stmt.close();
     * conn.close();
     * } catch (ClassNotFoundException | SQLException | ProjectException | IOException e) {
     * LOGGER.error("表查询异常，sql语句为 {}", sql);
     * throw e;
     * }
     *
     * @param sql sql语句
     * @throws Exception 异常
     */
    @Override
    public void queryTable(String sql) throws Exception {
        try {
            Statement stmt;
            Connection conn = build();
            stmt = conn.createStatement();
            stmt.execute(sql);
//            ResultSet rs = stmt.executeQuery(sql);
//            LOGGER.info("sql语句执行成功");
//            while (rs.next()) {
//                // 通过字段检索并打印数据
//                String primaryKey = rs.getString("request_context");
//                System.out.println(primaryKey);
//            }
//            rs.close();
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException | IOException e) {
            LOGGER.error("表查询异常，sql语句为 {}", sql);
            throw e;
        }
    }

    /**
     * 创建表
     *
     * @param tableName 表明
     * @throws Exception 异常
     */
    @Override
    public void createTable(String tableName) throws Exception {

    }

    /**
     * 删除表
     *
     * @param tableName 表明
     * @throws Exception 异常
     */
    @Override
    public void deleteTable(String tableName) throws Exception {

    }

    /**
     * 插入数据到表中
     *
     * @param sql sql语句
     * @throws Exception 异常
     */
    @Override
    public void insertTable(String sql) throws Exception {

    }

    public static void main(String[] args) throws Exception {
        IMysqlOperation myOperationImpl = new MyMysqlOperationImpl();
        myOperationImpl.queryTable("update wdp_2.request set request_context='安装服务' where request_id=477");
    }
}
