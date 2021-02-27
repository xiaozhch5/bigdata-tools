package com.zh.ch.bigdata.flink.sql.primarykeychange.bean;

/**
 * @author xzc
 * @description
 * @date 2021/02/01
 */
public class Message {

    public Message(String data) {
        this.data = data;
    }

    public Message(){

    }

    public String data;

    public void setData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Message{" +
                "data='" + data + '\'' +
                '}';
    }
}
