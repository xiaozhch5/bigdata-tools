package com.zh.ch.bigdata.springboot.manager.mysql.service;

import com.zh.ch.bigdata.springboot.manager.mysql.dao.DataPrimarykeyChangedRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xzc
 * @description
 * @date 2021/01/11
 */
@Service
public class DataPrimaryKeyChangedRecordService {

    @Autowired
    DataPrimarykeyChangedRecordMapper dataPrimarykeyChangedRecordMapper;


    public int deleteByPrimaryKey(Long changeId) {
        return dataPrimarykeyChangedRecordMapper.deleteByPrimaryKey(changeId);
    }
}
