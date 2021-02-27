/**
  * Copyright 2021 bejson.com 
  */
package com.zh.ch.bigdata.flink.sql.primarykeychange.bean;
import java.util.List;

/**
 * Auto-generated: 2021-02-02 13:41:57
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class JsonRootBean {

    private PrimaryKeyChangeRecordInfo primaryKeyChangeRecordInfo;
    private List<RelationalTableInfo> relationalTableInfo;
    public void setPrimaryKeyChangeRecordInfo(PrimaryKeyChangeRecordInfo primaryKeyChangeRecordInfo) {
         this.primaryKeyChangeRecordInfo = primaryKeyChangeRecordInfo;
     }
     public PrimaryKeyChangeRecordInfo getPrimaryKeyChangeRecordInfo() {
         return primaryKeyChangeRecordInfo;
     }

    public void setRelationalTableInfo(List<RelationalTableInfo> relationalTableInfo) {
         this.relationalTableInfo = relationalTableInfo;
     }
     public List<RelationalTableInfo> getRelationalTableInfo() {
         return relationalTableInfo;
     }

}