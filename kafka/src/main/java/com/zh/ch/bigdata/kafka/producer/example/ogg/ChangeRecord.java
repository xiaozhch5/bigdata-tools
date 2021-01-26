package com.zh.ch.bigdata.kafka.producer.example.ogg;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author xzc
 * @description Ogg数据中的before和after的数据实体类
 * @date 2021/01/09
 */
public class ChangeRecord {

    @JSONField(name = "ACCT_ID")
    private String acctId;

    @JSONField(name = "ACCT_NAME")
    private String acctName;

    @JSONField(name = "CUST_ID")
    private String custId;

    @JSONField(name = "STATUS_CD")
    private String statusCd;

    @JSONField(name = "STATUS_DATE")
    private String statusDate;

    public void setAcctId(String acctId) {
        this.acctId = acctId;
    }

    public void setAcctName(String acctName) {
        this.acctName = acctName;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public void setStatusCd(String statusCd) {
        this.statusCd = statusCd;
    }

    public void setStatusDate(String statusDate) {
        this.statusDate = statusDate;
    }

    public String getAcctId() {
        return acctId;
    }

    public String getAcctName() {
        return acctName;
    }

    public String getCustId() {
        return custId;
    }

    public String getStatusCd() {
        return statusCd;
    }

    public String getStatusDate() {
        return statusDate;
    }
}
