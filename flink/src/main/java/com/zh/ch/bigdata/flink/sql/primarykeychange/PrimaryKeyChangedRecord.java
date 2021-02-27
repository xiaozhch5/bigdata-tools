package com.zh.ch.bigdata.flink.sql.primarykeychange;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zh.ch.bigdata.flink.sql.primarykeychange.bean.JsonRootBean;
import com.zh.ch.bigdata.flink.sql.primarykeychange.bean.PrimaryKeyChangeRecordInfo;
import com.zh.ch.bigdata.flink.sql.primarykeychange.bean.RelationalTableInfo;
import org.apache.flink.table.functions.FunctionContext;
import org.apache.flink.table.functions.ScalarFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 主键变更与复用
 * @author xzc
 * @description 主键变更与复用
 * @date 2021/01/31
 */
public class PrimaryKeyChangedRecord extends ScalarFunction {

    private static final Logger LOG = LoggerFactory.getLogger(PrimaryKeyChangedRecord.class);

    JsonRootBean primaryKeyChangedRecordJsonRootBean;

    String oggDataString;

    /**
     * 用于保存安徽还原数据库表名与字段之间的映射关系
     */
    private Map<String, List<String>> ahRestoreTableNamesColumnListMap;

    /**
     * 用于保存安徽目标数据库表名与字段之间的映射关系
     */
    private Map<String, List<String>> ahTargetTableNamesColumnListMap;

    /**
     * 用于保存浙江还原数据库表名与字段之间的映射关系
     */
    private Map<String, List<String>> zjRestoreTableNamesColumnListMap;

    /**
     * 用于保存浙江还原数据库表名与字段之间的映射关系
     */
    private Map<String, List<String>> zjTargetTableNamesColumnListMap;

    private Connection ahRestoreDbConn;

    private Connection ahTargetDbConn;

    private Connection zjRestoreDbConn;

    private Connection zjTargetDbConn;

    private Connection priKeyChangeRecordDbConn;

    private static final String[] PROVINCES = {"anhui", "zhejiang"};

    private static RelationalTableInfo ahRelationalTableInfo;

    private static RelationalTableInfo zjRelationalTableInfo;

    public String eval(String oggDataString) {
        this.oggDataString = oggDataString;
        oggPrimaryKeyChangedRecord();
        return oggDataString;
    }

    @Override
    public void open(FunctionContext context) throws Exception {
        super.open(context);
        String jsonConfig = context.getJobParameter(PriConfig.PRI_JSON_CONFIG, "");
        parseJsonConfig(jsonConfig);
    }

    /**
     * 初始化配置
     * @param jsonConfig json String数据
     */
    public void parseJsonConfig(String jsonConfig) {
        JSONObject jsonRootBean = JSONObject.parseObject(jsonConfig);
        this.primaryKeyChangedRecordJsonRootBean = JSON.toJavaObject(jsonRootBean, JsonRootBean.class);
    }

    /**
     * 与province对应的还原库建立连接
     * @param province province
     * @throws SQLException {@link SQLException}
     */
    private Connection establishDbConn(String province) {
        Connection dbConn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            List<RelationalTableInfo> relationalTableInfoList = primaryKeyChangedRecordJsonRootBean.getRelationalTableInfo();
            for (RelationalTableInfo relationalTableInfo : relationalTableInfoList) {
                if (province.equals(relationalTableInfo.getPrivoice())) {
                    String targetDbConnectionUrl = relationalTableInfo.getRestoreDbConnectionUrl();
                    String targetDbConnectionUserName = relationalTableInfo.getRestoreDbConnectionUserName();
                    String targetDbConnectionPassword = relationalTableInfo.getRestoreDbConnectionPassword();
                    dbConn = DriverManager.getConnection(targetDbConnectionUrl, targetDbConnectionUserName, targetDbConnectionPassword);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            LOG.error("数据库建立连接失败", e);
        }
        return dbConn;
    }

    /**
     * 与province对应的统一库建立连接
     * @param province province
     * @throws SQLException {@link SQLException}
     */
    private Connection establishDbConn2(String province) {
        Connection dbConn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            List<RelationalTableInfo> relationTableDataList = primaryKeyChangedRecordJsonRootBean.getRelationalTableInfo();
            for (RelationalTableInfo relationalTableInfo : relationTableDataList) {
                if (province.equals(relationalTableInfo.getPrivoice())) {
                    String relationTableConnectionUrl = relationalTableInfo.getTargetDbConnectionUrl();
                    String relationTableConnectionUserName = relationalTableInfo.getTargeteDbConnectionUserName();
                    String relationTableConnectionPassword = relationalTableInfo.getTargetDbConnectionPassword();
                    dbConn = DriverManager.getConnection(relationTableConnectionUrl, relationTableConnectionUserName, relationTableConnectionPassword);
                    break;
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            LOG.error("数据库建立连接失败", e);
        }
        return dbConn;
    }

    private Connection establishDbConn3() {
        String primaryKeysChangeRecordMysqlConnectionUrl = null;
        String primaryKeysChangeRecordMysqlUserName = null;
        String primaryKeysChangeRecordMysqlPassword = null;
        Connection dbConn3 = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            PrimaryKeyChangeRecordInfo changeRecordTableData = primaryKeyChangedRecordJsonRootBean.getPrimaryKeyChangeRecordInfo();
             primaryKeysChangeRecordMysqlConnectionUrl = changeRecordTableData.getPrimaryKeysChangeRecordMysqlConnectionUrl();
             primaryKeysChangeRecordMysqlUserName = changeRecordTableData.getPrimaryKeysChangeRecordMysqlUserName();
             primaryKeysChangeRecordMysqlPassword = changeRecordTableData.getPrimaryKeysChangeRecordMysqlPassword();
            dbConn3 = DriverManager.getConnection(primaryKeysChangeRecordMysqlConnectionUrl, primaryKeysChangeRecordMysqlUserName, primaryKeysChangeRecordMysqlPassword);
        } catch (SQLException | ClassNotFoundException e) {
            LOG.error("数据库{}建立连接失败", primaryKeysChangeRecordMysqlConnectionUrl, e);
        }
        return dbConn3;
    }


    /**
     * 判断ogg数据中主键是否修改，关联表配置格式为：
     */
    public void oggPrimaryKeyChangedRecord() {
        int mysqlReturnCode = -1;
        int relationTableReturnCode = -1;
        int targetTableReturnCode = -1;
        List<Integer> returnCodes = new ArrayList<>();
        try {
            JSONObject oggData = JSONObject.parseObject(oggDataString);
            String provinceName = oggData.getString("province");
            String tableName = oggData.getString("table");

            List<String> primaryKeyList = null;

            Connection restoreDbConn = null;
            Connection targetDbConn = null;

            Map<String, List<String>> restoreTableNamesColumnListMap = null;
            Map<String, List<String>> targetTableNamesColumnListMap = null;

            synchronized (this) {
                List<RelationalTableInfo> relationalTableInfoList = primaryKeyChangedRecordJsonRootBean.getRelationalTableInfo();
                for (RelationalTableInfo relationalTableInfo1 : relationalTableInfoList) {
                    if (relationalTableInfo1.getPrivoice().equals(PROVINCES[0])) {
                        ahRelationalTableInfo = relationalTableInfo1;
                    } else if (relationalTableInfo1.getPrivoice().equals(PROVINCES[1])) {
                        zjRelationalTableInfo = relationalTableInfo1;
                    }
                }
                if ((ahRestoreDbConn == null) || (ahTargetDbConn == null)) {
                    ahRestoreDbConn = establishDbConn(PROVINCES[0]);
                    ahTargetDbConn = establishDbConn2(PROVINCES[0]);
                    ahRestoreTableNamesColumnListMap = QueryTable.getTableColumnList(ahRestoreDbConn, ahRelationalTableInfo.getRestoreDbName());
                    ahTargetTableNamesColumnListMap = QueryTable.getTableColumnList(ahTargetDbConn, ahRelationalTableInfo.getTargetDbName());
                }
                if ((zjRestoreDbConn == null) || (zjTargetDbConn == null)) {
                    zjRestoreDbConn = establishDbConn(PROVINCES[1]);
                    zjTargetDbConn = establishDbConn2(PROVINCES[1]);
                    zjRestoreTableNamesColumnListMap = QueryTable.getTableColumnList(zjRestoreDbConn, zjRelationalTableInfo.getRestoreDbName());
                    zjTargetTableNamesColumnListMap = QueryTable.getTableColumnList(zjTargetDbConn, zjRelationalTableInfo.getTargetDbName());
                }
                if (priKeyChangeRecordDbConn == null) {
                    priKeyChangeRecordDbConn = establishDbConn3();
                }
            }

            if (provinceName.equals(PROVINCES[0])) {
                primaryKeyList = QueryTable.queryTablePrimaryKeyList(ahRestoreDbConn, ahRelationalTableInfo.getRestoreDbName(), tableName);
                restoreDbConn = this.ahRestoreDbConn;
                targetDbConn = this.ahTargetDbConn;
                restoreTableNamesColumnListMap = this.ahRestoreTableNamesColumnListMap;
                targetTableNamesColumnListMap = this.ahTargetTableNamesColumnListMap;
            } else if (provinceName.equals(PROVINCES[1])) {
                primaryKeyList = QueryTable.queryTablePrimaryKeyList(zjRestoreDbConn, zjRelationalTableInfo.getRestoreDbName(), tableName);
                restoreDbConn =this.zjRestoreDbConn;
                targetDbConn = this.zjTargetDbConn;
                restoreTableNamesColumnListMap = this.zjRestoreTableNamesColumnListMap;
                targetTableNamesColumnListMap = this.zjTargetTableNamesColumnListMap;
            }

            if (primaryKeyList == null) {
                throw new SQLException(ErrorCode.PRIMARY_KEY_NOT_FOUND);
            }

            for (String key : primaryKeyList) {
                if (oggData.getJSONObject(OggCons.BEFORE) != null) {
                    String primaryKeyBefore = oggData.getJSONObject(OggCons.BEFORE).getString(key);
                    String primaryKeyAfter = oggData.getJSONObject(OggCons.AFTER).getString(key);

                    if (!primaryKeyBefore.equals(primaryKeyAfter)) {
                        String oggTableName = oggData.getString(OggCons.TABLE);
                        String opTs = oggData.getString(OggCons.OP_TS);
                        String mysqlTableName = primaryKeyChangedRecordJsonRootBean.getPrimaryKeyChangeRecordInfo().getPrimaryKeysChangeRecordTableName();
                        mysqlReturnCode = writeOggPrimaryKeyChangedRecordToDb(priKeyChangeRecordDbConn, mysqlTableName, oggTableName, key, primaryKeyBefore, primaryKeyAfter, opTs, provinceName);
                        relationTableReturnCode = changeRestoreTable(restoreDbConn, restoreTableNamesColumnListMap, oggTableName, key, primaryKeyAfter, primaryKeyBefore);
                        targetTableReturnCode = changeTargetTable(targetDbConn, targetTableNamesColumnListMap, oggTableName, key, primaryKeyAfter, primaryKeyBefore);
                        returnCodes.add(mysqlReturnCode);
                        returnCodes.add(relationTableReturnCode);
                        returnCodes.add(targetTableReturnCode);
                        LOG.info("主键变更修改记录与相关表成功");
                    }
                }
            }
        }
        catch(SQLException e) {
            ahRestoreDbConn = null;
            ahTargetDbConn = null;
            zjRestoreDbConn = null;
            zjTargetDbConn = null;
            if (ErrorCode.PRIMARY_KEY_NOT_FOUND.equals(e.getMessage())) {
                LOG.error("无法获取目标表的主键");
            } else {
                LOG.error("主键变更与复用记录失败，主键变更与复用的返回值为 {}", returnCodes, e);
            }
        }
    }

    /**
     * 修改还原库关联数据
     * @param key                key
     * @param primaryKeyAfter    primaryKeyAfter
     * @param primaryKeyBefore   primaryKeyBefore
     * @return int
     */
    private int changeRestoreTable(
            Connection dbConn,
            Map<String, List<String>> restoreTableNamesColumnListMap,
            String oggTableName,
            String key,
            String primaryKeyAfter,
            String primaryKeyBefore) {

        int relationTableReturnCode = -1;
        try {
            // 更新关联的数据记录中的主键数据
            // 需要更新的关联表
            // 如果没有指定则不需要更新
            Statement statement = dbConn.createStatement();
            // 根据还原库中所有表的字段进行修改，如果该表含有该字段，那么就修改这个关联表的关联字段
            for (Map.Entry<String, List<String>> entry : restoreTableNamesColumnListMap.entrySet()) {
                String tableName = entry.getKey();
                if (oggTableName.equals(tableName)) {
                    continue;
                }
                List<String> columnList = entry.getValue();
                if (columnList.contains(key)) {
                    StringBuilder updateSQL = new StringBuilder();
                    updateSQL.append("update ");
                    updateSQL.append(tableName);
                    updateSQL.append(" set ");
                    updateSQL.append(key);
                    updateSQL.append(" = ");
                    updateSQL.append(primaryKeyAfter);
                    updateSQL.append(" where ");
                    updateSQL.append(key);
                    updateSQL.append(" = ");
                    updateSQL.append(primaryKeyBefore);
                    relationTableReturnCode = statement.executeUpdate(updateSQL.toString());
                }
            }
        } catch (SQLException e) {
            LOG.error("ogg由于主键变更之后的关联表修改失败，关联表以及字段为", e);
        }
        return relationTableReturnCode;
    }


    /**
     * 修改目标表关联数据
     * @param key                key
     * @param primaryKeyAfter    primaryKeyAfter
     * @param primaryKeyBefore   primaryKeyBefore
     * @return
     */
    private int changeTargetTable(
            Connection dbConn,
            Map<String, List<String>> targetTableNamesColumnListMap,
            String oggTableName,
            String key,
            String primaryKeyAfter,
            String primaryKeyBefore) {
        int targetTableReturnCode = -1;
        try {
            // 更新关联的数据记录中的主键数据
            // 需要更新的关联表
            // 如果没有指定则不需要更新
            Statement statement = dbConn.createStatement();

            // 根据还原库中所有表的字段进行修改，如果该表含有该字段，那么就修改这个关联表的关联字段
            if (targetTableNamesColumnListMap == null) {
                throw new SQLException(ErrorCode.NO_TABLES_IN_DB);
            }
            for (Map.Entry<String, List<String>> entry : targetTableNamesColumnListMap.entrySet()) {
                String tableName = entry.getKey();
                if (oggTableName.equals(tableName)) {
                    continue;
                }
                List<String> columnList = entry.getValue();
                if (columnList.contains(key)) {
                    StringBuilder updateSQL = new StringBuilder();
                    updateSQL.append("update ");
                    updateSQL.append(tableName);
                    updateSQL.append(" set ");
                    updateSQL.append(key);
                    updateSQL.append(" = ");
                    updateSQL.append(primaryKeyAfter);
                    updateSQL.append(" where ");
                    updateSQL.append(key);
                    updateSQL.append(" = ");
                    updateSQL.append(primaryKeyBefore);
                    targetTableReturnCode = statement.executeUpdate(updateSQL.toString());
                }
            }
        } catch (SQLException e) {
            LOG.error("ogg由于主键变更之后的关联表修改失败", e);
        }
        return targetTableReturnCode;
    }

    private int writeOggPrimaryKeyChangedRecordToDb(Connection dbConn, String mysqlTableName, String oggTableName, String columnName, String primaryKeyBefore, String primaryKeyAfter, String opTs, String province) throws SQLException {
        // 插入主键变更记录数据到记录数据库中
        int mysqlReturnCode = -1;
        PreparedStatement preparedStatement;
        StringBuilder insertSQL = new StringBuilder();
        insertSQL.append("insert into ");
        insertSQL.append(mysqlTableName);
        insertSQL.append("(table_name, before_id, after_id, create_time, province, column_name)");
        insertSQL.append(" values(?, ? ,?, ?, ?, ?)");
        preparedStatement = dbConn.prepareStatement(insertSQL.toString());
        preparedStatement.setString(1, oggTableName);
        preparedStatement.setString(2, primaryKeyBefore);
        preparedStatement.setString(3, primaryKeyAfter);
        preparedStatement.setString(4, opTs);
        preparedStatement.setString(5, province);
        preparedStatement.setString(6, columnName);
        mysqlReturnCode = preparedStatement.executeUpdate();
        return mysqlReturnCode;
    }

    public void setOggDataString(String oggDataString) {
        this.oggDataString = oggDataString;
    }

    public String getOggDataString() {
        return oggDataString;
    }
}
