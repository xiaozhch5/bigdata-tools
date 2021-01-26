package com.zh.ch.bigdata.mybatis.service;

import com.zh.ch.bigdata.mybatis.dao.DataPrimarykeyChangedRecordMapper;

/**
 * @author xzc
 * @description
 * @date 2021/01/11
 */
public class DataPrimarykeyChangedRecordService {

    DataPrimarykeyChangedRecordMapper dataPrimarykeyChangedRecordMapper;


    public int deleteByPrimaryKey(Long changeId) {
        return dataPrimarykeyChangedRecordMapper.deleteByPrimaryKey(changeId);
    }


}
