package com.zh.ch.bigdata.springboot.manager.mysql.controller;

import com.zh.ch.bigdata.springboot.manager.mysql.bean.ListParams;
import com.zh.ch.bigdata.springboot.manager.mysql.service.DataPrimaryKeyChangedRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xzc
 * @description
 * @date 2021/01/11
 */
@RestController
@RequestMapping("deleteData")
public class DataPrimarykeyChangedRecordController {

    @Autowired
    DataPrimaryKeyChangedRecordService dataPrimaryKeyChangedRecordService;

    @RequestMapping(value = "/tableNameAndPrimaryKey", method = RequestMethod.POST)
    @ResponseBody
    public int deleteByPrimaryKey(@RequestBody ListParams param){
    return dataPrimaryKeyChangedRecordService.deleteByPrimaryKey(param.getPrimaryKey());
    }
}
