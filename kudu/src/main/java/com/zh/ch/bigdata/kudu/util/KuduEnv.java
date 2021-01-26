package com.zh.ch.bigdata.kudu.util;

/**
 * @author xzc
 * @description Kudu用到的配置变量
 * @date 2020/12/04
 */
public class KuduEnv {

    /**
     * kuduMaster的地址
     */
    public static final String KUDU_MASTER = "kudu.master";

    /**
     * kudu中的表名
     */
    public static final String KUDU_TABLE_NAME = "kudu.tableName";

    /**
     * kudu默认的TimeOut时间，单位为ms
     */
    public static final String KUDU_DEFAULT_OPERATION_TIMEOUT_MS = "kudu.defaultOperationTimeoutMs";

    /**
     * kudu中表的列名的分隔符
     */
    public static final String KUDU_TABLE_COLUMN_NAME_SEPARATOR = "kudu.table.columnName.separator";

    /**
     * kudu中表的列名
     */
    public static final String KUDU_TABLE_COLUMN_NAME = "kudu.table.columnName";
}
