package com.zh.ch.bigdata.springboot.manager.mysql.controller;

import com.zh.ch.bigdata.springboot.manager.mysql.bean.ListParams;
import com.zh.ch.bigdata.springboot.manager.mysql.service.DataPrimaryKeyChangedRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
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

    @RequestMapping(value = "/TableNameAndPrimaryKey", method = RequestMethod.POST)
    @ResponseBody
    public void deleteByPrimaryKey(@RequestBody ListParams param){
    dataPrimaryKeyChangedRecordService.deleteByPrimaryKey(param.getPrimaryKey());
    }


}
