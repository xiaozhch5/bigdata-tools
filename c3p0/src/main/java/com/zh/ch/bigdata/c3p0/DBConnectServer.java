package com.zh.ch.bigdata.c3p0;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author xzc
 * @description
 * @date 2021/04/10
 */

public final class DBConnectServer {
    /**
     * 使用单例模式创建数据库连接池
     */
    private static DBConnectServer instance;
    private static ComboPooledDataSource dataSource;

    private DBConnectServer() throws SQLException, PropertyVetoException {
        dataSource = new ComboPooledDataSource();
        // 用户名
        dataSource.setUser("root");
        //密码
        dataSource.setPassword("Pass-23-wdp");
        //数据库地址
        dataSource.setJdbcUrl("jdbc:mysql://vhost-118-23:3306/wdp_2?useUnicode=true&characterEncoding=UTF-8");
        dataSource.setDriverClass("com.mysql.jdbc.Driver");
        // 初始化时获取连接数，取值应在minPoolSize与maxPoolSize之间。Default: 3
        dataSource.setInitialPoolSize(5);
        //  连接池中保留的最小连接数
        dataSource.setMinPoolSize(1);
        // 连接池中保留的最大连接数。Default: 15
        dataSource.setMaxPoolSize(10);
        // 最长等待时间
        dataSource.setMaxStatements(50);
        // 最大空闲时间,60秒内未使用则连接被丢弃。若为0则永不丢弃。Default: 0
        dataSource.setMaxIdleTime(60);
        // 当连接池中的连接耗尽的时候c3p0一次同时获取的连接数。Default: 3
        dataSource.setAcquireIncrement(3);
        // 定义在从数据库获取新连接失败后重复尝试的次数。Default: 30
        dataSource.setAcquireRetryAttempts(30);


    }
    public static final DBConnectServer getInstance() {
        if (instance == null) {
            try {
                instance = new DBConnectServer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    public synchronized final Connection getConnection() {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }


}