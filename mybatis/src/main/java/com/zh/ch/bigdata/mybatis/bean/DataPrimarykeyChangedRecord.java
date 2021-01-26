package com.zh.ch.bigdata.mybatis.bean;

import java.util.Date;

public class DataPrimarykeyChangedRecord {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column data_primarykey_changed_record.change_id
     *
     * @mbggenerated
     */
    private Long changeId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column data_primarykey_changed_record.table_name
     *
     * @mbggenerated
     */
    private String tableName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column data_primarykey_changed_record.before_id
     *
     * @mbggenerated
     */
    private String beforeId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column data_primarykey_changed_record.after_id
     *
     * @mbggenerated
     */
    private String afterId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column data_primarykey_changed_record.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_primarykey_changed_record
     *
     * @mbggenerated
     */
    public DataPrimarykeyChangedRecord(Long changeId, String tableName, String beforeId, String afterId, Date createTime) {
        this.changeId = changeId;
        this.tableName = tableName;
        this.beforeId = beforeId;
        this.afterId = afterId;
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_primarykey_changed_record
     *
     * @mbggenerated
     */
    public DataPrimarykeyChangedRecord() {
        super();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column data_primarykey_changed_record.change_id
     *
     * @return the value of data_primarykey_changed_record.change_id
     *
     * @mbggenerated
     */
    public Long getChangeId() {
        return changeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column data_primarykey_changed_record.change_id
     *
     * @param changeId the value for data_primarykey_changed_record.change_id
     *
     * @mbggenerated
     */
    public void setChangeId(Long changeId) {
        this.changeId = changeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column data_primarykey_changed_record.table_name
     *
     * @return the value of data_primarykey_changed_record.table_name
     *
     * @mbggenerated
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column data_primarykey_changed_record.table_name
     *
     * @param tableName the value for data_primarykey_changed_record.table_name
     *
     * @mbggenerated
     */
    public void setTableName(String tableName) {
        this.tableName = tableName == null ? null : tableName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column data_primarykey_changed_record.before_id
     *
     * @return the value of data_primarykey_changed_record.before_id
     *
     * @mbggenerated
     */
    public String getBeforeId() {
        return beforeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column data_primarykey_changed_record.before_id
     *
     * @param beforeId the value for data_primarykey_changed_record.before_id
     *
     * @mbggenerated
     */
    public void setBeforeId(String beforeId) {
        this.beforeId = beforeId == null ? null : beforeId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column data_primarykey_changed_record.after_id
     *
     * @return the value of data_primarykey_changed_record.after_id
     *
     * @mbggenerated
     */
    public String getAfterId() {
        return afterId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column data_primarykey_changed_record.after_id
     *
     * @param afterId the value for data_primarykey_changed_record.after_id
     *
     * @mbggenerated
     */
    public void setAfterId(String afterId) {
        this.afterId = afterId == null ? null : afterId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column data_primarykey_changed_record.create_time
     *
     * @return the value of data_primarykey_changed_record.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column data_primarykey_changed_record.create_time
     *
     * @param createTime the value for data_primarykey_changed_record.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}