package com.zh.ch.bigdata.kudu.operation;

import com.zh.ch.bigdata.exception.ProjectException;
import com.zh.ch.bigdata.java.FileReaderUtil;
import com.zh.ch.bigdata.java.PropertiesAnalyzeUtil;
import com.zh.ch.bigdata.kudu.util.KuduEnv;
import com.zh.ch.bigdata.kudu.util.MyKuduClient;
import org.apache.kudu.client.KuduClient;
import org.apache.kudu.client.KuduScanner;
import org.apache.kudu.client.RowResult;
import org.apache.kudu.client.RowResultIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.PropertyPermission;

/**
 * @author xzc
 * @description 查询表数据
 * @date 2020/12/04
 */
public class QueryTable {

    private static final Logger LOG = LoggerFactory.getLogger(QueryTable.class);

    public static void doQuery(String kuduConfigPropertiesFilePath) throws IOException, ProjectException {

        try {
            KuduClient kuduClient = MyKuduClient.build(kuduConfigPropertiesFilePath);
            String kuduTableName = PropertiesAnalyzeUtil.getProperty(kuduConfigPropertiesFilePath, KuduEnv.KUDU_TABLE_NAME);
            KuduScanner.KuduScannerBuilder kuduScannerBuilder = kuduClient.newScannerBuilder(kuduClient.openTable(kuduTableName));

            ArrayList<String> columnsList = new ArrayList<>();
            String[] columns = PropertiesAnalyzeUtil.getProperty(kuduConfigPropertiesFilePath, KuduEnv.KUDU_TABLE_COLUMN_NAME)
                    .split(PropertiesAnalyzeUtil.getProperty(kuduConfigPropertiesFilePath, KuduEnv.KUDU_TABLE_COLUMN_NAME_SEPARATOR));
            Collections.addAll(columnsList, columns);
            kuduScannerBuilder.setProjectedColumnNames(columnsList);
            KuduScanner kuduScanner = kuduScannerBuilder.build();

            while (kuduScanner.hasMoreRows()) {
                RowResultIterator rowIterator = kuduScanner.nextRows();
                while (rowIterator.hasNext()) {
                    RowResult row = rowIterator.next();
                    for (String column : columns) {
                        System.out.print(row.getString(column));
                    }
                    System.out.println();
                }
            }
        } catch (IOException | ProjectException e) {
            LOG.error("kudu查询错误");
            throw e;
        }
    }

    /**
     * 获取resources文件路径
     * @param fileName 文件名
     * @return 文件路径
     */
    public String getResource(String fileName) {
        return Objects.requireNonNull(this.getClass().getClassLoader().getResource(fileName)).toString().substring(6);
    }


    public static void main(String[] args) throws IOException, ProjectException {
        QueryTable queryTable = new QueryTable();
        doQuery(queryTable.getResource("kudu-config.properties"));
    }

}
