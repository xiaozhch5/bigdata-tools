package com.zh.ch.bigdata.test.pkmanage;
/*
 * @author xzc
 * @description 用于解决主键变更与服用问题
 * @date 2021/01/05
 */

/*
 * 建表语句
 *
  create table data_primarykey_changed_record (
    change_id bigint(12) not null auto_increment,
    table_name varchar(128) not null,
    before_id varchar(128) not null,
    after_id varchar(128) not null,
    create_time datetime not null,
    primary key(change_id)
  );
 */