package com.zh.ch.bigdata.impala.util;

import com.zh.ch.bigdata.exception.ProjectException;
import com.zh.ch.bigdata.java.PropertiesAnalyzeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author xzc
 * @description 创建Impala客户端，建立与impala的连接
 * @date 2020/12/03
 */
public class ImpalaClient {

    private static final Logger logger = LoggerFactory.getLogger(ImpalaClient.class);

    /**
     * Impala驱动名称
     */
    private static final String IMPALA_DRIVER_CLASS = "com.cloudera.impala.jdbc41.Driver";

    /**r
     * 建立impala连接
     * @param impalaConfigFilePath impala配置文件路径
     * @return Connection
     */
    public static Connection build(String impalaConfigFilePath) throws IOException, ProjectException, SQLException, ClassNotFoundException {
        try {
            Connection conn = null;
            logger.info("建立Impala连接...");
            Class.forName(IMPALA_DRIVER_CLASS);
            conn = DriverManager.getConnection(PropertiesAnalyzeUtil.getProperty(impalaConfigFilePath, ImpalaEnv.IMPALA_CONNECTION_URL));
            logger.info("Impala连接建立陈功！");
            return conn;
        }
        catch (ClassNotFoundException | SQLException | ProjectException | IOException e) {
            logger.error("Impala 建立连接失败，当前impala_connection_url为 {}", PropertiesAnalyzeUtil.getProperty(impalaConfigFilePath, ImpalaEnv.IMPALA_CONNECTION_URL));
            logger.error("Impala 建立连接失败，驱动名称为 {}", IMPALA_DRIVER_CLASS);
            throw e;
        }
    }
}
