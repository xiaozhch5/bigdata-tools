package com.zh.ch.bigdata.kafka.producer.example.ogg;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author xzc
 * @description ogg数据实体类
 * @date 2021/01/08
 */
public class OggData {

    @JSONField(name ="table")
    private String table;

    @JSONField(name = "op_type")
    private String opType;

    @JSONField(name = "op_ts")
    private String opTs;

    @JSONField(name = "current_ts")
    private String currentTs;

    @JSONField(name = "primary_keys")
    private String[] primaryKeys;

    @JSONField(name = "before")
    private ChangeRecord before;

    @JSONField(name = "after")
    private ChangeRecord after;

    public void setTable(String table) {
        this.table = table;
    }

    public void setOpType(String opType) {
        this.opType = opType;
    }

    public void setOpTs(String opTs) {
        this.opTs = opTs;
    }

    public void setCurrentTs(String currentTs) {
        this.currentTs = currentTs;
    }

    public void setPrimaryKeys(String[] primaryKeys) {
        this.primaryKeys = primaryKeys;
    }

    public void setBefore(ChangeRecord before) {
        this.before = before;
    }

    public void setAfter(ChangeRecord after) {
        this.after = after;
    }

    public String getTable() {
        return table;
    }

    public String getOpType() {
        return opType;
    }

    public String getOpTs() {
        return opTs;
    }

    public String getCurrentTs() {
        return currentTs;
    }

    public String[] getPrimaryKeys() {
        return primaryKeys;
    }

    public ChangeRecord getBefore() {
        return before;
    }

    public ChangeRecord getAfter() {
        return after;
    }
}