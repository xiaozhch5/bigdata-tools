package com.zh.ch.bigdata.kudu.util;

import com.zh.ch.bigdata.exception.ProjectException;
import com.zh.ch.bigdata.java.PropertiesAnalyzeUtil;
import org.apache.kudu.client.KuduClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author xzc
 * @description kudu客户端建立连接
 * @date 2020/12/04
 */
public class MyKuduClient {

    private static final Logger LOG = LoggerFactory.getLogger(MyKuduClient.class);

    /**
     * 建立与Kudu的连接，返回KuduClient
     * @param kuduConfigPropertiesFilePath kudu配置文件路径
     * @return KuduClient
     * @throws IOException 异常
     * @throws ProjectException 异常
     */
    public static KuduClient build(String kuduConfigPropertiesFilePath) throws IOException, ProjectException {
        String kuduMaster = null;
        String kuduTableName = null;
        KuduClient kuduClient;
        try {
            LOG.info("开始与kudu建立连接......");
            kuduMaster = PropertiesAnalyzeUtil.getProperty(kuduConfigPropertiesFilePath, KuduEnv.KUDU_MASTER);
            LOG.info("当前连接的kuduMaster为 {}", kuduMaster);
            kuduTableName = PropertiesAnalyzeUtil.getProperty(kuduConfigPropertiesFilePath, KuduEnv.KUDU_TABLE_NAME);
            LOG.info("当前连接的kuduTableName为 {}", kuduTableName);
            KuduClient.KuduClientBuilder kuduClientBuilder = new KuduClient.KuduClientBuilder(kuduMaster);
            kuduClientBuilder.defaultAdminOperationTimeoutMs(Long.parseLong(PropertiesAnalyzeUtil.getProperty(kuduConfigPropertiesFilePath, KuduEnv.KUDU_DEFAULT_OPERATION_TIMEOUT_MS)));
            kuduClient = kuduClientBuilder.build();
            LOG.info("kudu连接成功");
            return kuduClient;
        } catch (ProjectException | IOException e) {
            LOG.error("kudu客户端建立连接错误，当前连接的kuduMaster为 {}", kuduMaster);
            LOG.error("kudu客户端建立连接错误，当前连接的kuduTableName为 {}", kuduTableName);
            throw e;
        }
    }
}
