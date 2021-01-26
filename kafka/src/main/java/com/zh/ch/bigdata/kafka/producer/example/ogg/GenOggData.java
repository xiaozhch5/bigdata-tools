package com.zh.ch.bigdata.kafka.producer.example.ogg;

import com.alibaba.fastjson.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author xzc
 * @description 生成Ogg数据
 * @date 2021/01/08
 */
public class GenOggData {

    /**
     * 生成Ogg数据
     *
     * @param index 数据序号
     * @param count 当 index % count == 0 时，产生一条主键变更数据
     */
    public static String generate(Long index, Long count) {
        OggData oggData = new OggData();
        ChangeRecord before = new ChangeRecord();
        ChangeRecord after = new ChangeRecord();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSS");
        String[] primaryKeys = {"ACCT_ID"};

        if (index % count == 0) {
            after.setAcctId(String.valueOf(index + 100));
        } else {
            after.setAcctId(String.valueOf(index));
        }
        before.setAcctId(String.valueOf(index));
        before.setAcctName(String.valueOf(index + 1231));
        before.setCustId(String.valueOf(index));
        before.setStatusCd("I");
        before.setStatusDate(simpleDateFormat.format(new Date()));
        after.setAcctName(String.valueOf(index + 1231));
        after.setCustId(String.valueOf(index));
        after.setStatusCd("I");
        after.setStatusDate(simpleDateFormat.format(new Date()));
        oggData.setBefore(before);
        oggData.setAfter(after);
        oggData.setTable("ACCOUNT");
        oggData.setPrimaryKeys(primaryKeys);
        oggData.setOpType("I");
        oggData.setOpTs(simpleDateFormat.format(new Date()));
        oggData.setCurrentTs(simpleDateFormat.format(new Date()));
        return JSONObject.toJSONString(oggData);
    }
}

