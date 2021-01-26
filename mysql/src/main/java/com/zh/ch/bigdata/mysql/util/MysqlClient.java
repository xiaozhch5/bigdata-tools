package com.zh.ch.bigdata.mysql.util;

import com.zh.ch.bigdata.exception.ProjectException;
import com.zh.ch.bigdata.java.PropertiesAnalyzeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

/**
 * @author xzc
 * @description 读取mysql-config.properties配置文件，与mysql建立连接
 * @date 2020/12/08
 */
public class MysqlClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(MysqlClient.class);

    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

    private String mysqlConfigPropertiesFilePath = null;

    /**
     * 无参构造方法，默认使用resources目录下的配置文件
     */
    public MysqlClient() { }

    /**
     * 有参构造方法
     * @param mysqlConfigPropertiesFilePath mysql-config.properties配置文件路径
     */
    public MysqlClient(String mysqlConfigPropertiesFilePath) {
        this.mysqlConfigPropertiesFilePath = mysqlConfigPropertiesFilePath;
    }

    /**
     * 获取resources文件路径
     * @param fileName 文件名
     * @return 文件路径
     */
    public String getResource(String fileName) {
        return Objects.requireNonNull(this.getClass().getClassLoader().getResource(fileName)).toString().substring(6);
    }

    /**
     * 建立与mysql之间的连接
     * @return Mysql Connection
     * @throws ClassNotFoundException 异常
     * @throws IOException 异常
     * @throws ProjectException 异常
     * @throws SQLException 异常
     */
    public Connection build() throws ClassNotFoundException, IOException, ProjectException, SQLException {
        if (this.mysqlConfigPropertiesFilePath == null) {
            this.mysqlConfigPropertiesFilePath = new MysqlClient().getResource(MysqlConfigEnv.MYSQL_CONFIG_FILENAME);
        }
        String mysqlConnectionUrl = PropertiesAnalyzeUtil.getProperty(mysqlConfigPropertiesFilePath, MysqlConfigEnv.MYSQL_CONNECTION_URL);
        String mysqlUserName = PropertiesAnalyzeUtil.getProperty(mysqlConfigPropertiesFilePath, MysqlConfigEnv.MYSQL_USERNAME);
        String mysqlPassword = PropertiesAnalyzeUtil.getProperty(mysqlConfigPropertiesFilePath, MysqlConfigEnv.MYSQL_PASSWORD);

        try {
            LOGGER.info("初始化mysql数据库连接");
            Class.forName(JDBC_DRIVER);
            Connection connection = DriverManager.getConnection(mysqlConnectionUrl, mysqlUserName, mysqlPassword);
            LOGGER.info("mysql数据库连接成功");
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.error("mysql建立连接失败");
            throw e;
        }
    }
}
