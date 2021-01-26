package com.zh.ch.bigdata.impala;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zh.ch.bigdata.exception.ProjectException;
import com.zh.ch.bigdata.impala.util.ImpalaClient;
import com.zh.ch.bigdata.java.FileReaderUtil;
import com.zh.ch.bigdata.json.JsonAnalysisUtil;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

/**
 * @author xzc
 * @description impala客户端
 * @date 2020/12/02
 */
public class ImpalaInsert {

    private static final Logger LOG = LoggerFactory.getLogger(ImpalaInsert.class);

    /**
     * impala配置文件路径
     */
    @Getter
    @Setter
    private String impalaConfigPropertiesFilePath = null;

    /**
     * 待插入impala的数据
     */
    @Getter
    @Setter
    private String jsonFilePath = null;

    /**
     * 无参构造函数
     */
    public ImpalaInsert() { }

    /**
     * 配置Impala连接信息
     * @param impalaConfigPropertiesFilePath 配置文件路径
     * @param jsonFilePath Json文件路径
     */
    public void config(String impalaConfigPropertiesFilePath, String jsonFilePath) {
        this.impalaConfigPropertiesFilePath = impalaConfigPropertiesFilePath;
        this.jsonFilePath = jsonFilePath;
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
     *
     * @throws IOException IOException异常
     *
     */

    /**
     * 将数据使用impala插入数据库
     * @throws IOException IOException异常
     * @throws ProjectException ProjectException异常
     * @throws SQLException SQLException异常
     * @throws ClassNotFoundException ClassNotFoundException异常
     */
    public void doInsert() throws IOException, ProjectException, SQLException, ClassNotFoundException {
        String sql = null;
        String sql1 = "insert into test_2.weather values (%d, \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\")";
        try {
            Connection connection = ImpalaClient.build(getImpalaConfigPropertiesFilePath());
            String jsonStr = FileReaderUtil.getString(getJsonFilePath());
            JSONArray jsonArray = JsonAnalysisUtil.parseJsonObject(jsonStr).getJSONArray("data");
            Statement statement = connection.createStatement();
            long id = 10002L;
            for (Object obj : jsonArray) {
                JSONObject jsonObject = (JSONObject) obj;
                sql = String.format(sql1, id, jsonObject.getString("P"), jsonObject.getString("DDATETIME"), jsonObject.getString("OBTID"), jsonObject.getString("T"),
                        jsonObject.getString("WD10DF"), jsonObject.get("HOURR"), jsonObject.getString("WD10DD"), jsonObject.getString("V"), jsonObject.getString("CRTTIME"));
                statement.execute(sql);
                id = id + 1;
                if (id % 500 == 0) {
                    LOG.info("已插入数据 {} 条", id - 10002L);
                }
            }
            statement.close();
            connection.close();
            LOG.info("数据插入完成！");
        } catch (SQLException | ProjectException | ClassNotFoundException e) {
            LOG.error("无法插入数据，当前SQL语句为 {}", sql);
            throw e;
        }
    }

    public static void main(String[] args) throws Exception {
        ImpalaInsert impalaInsert = new ImpalaInsert();
        impalaInsert.config(impalaInsert.getResource("impala-config.properties"), impalaInsert.getResource("weather.json"));
        impalaInsert.doInsert();
    }
}
