package com.zh.ch.bigdata.googleguice.example;

/**
 * @author xzc
 * @description
 * @date 2021/02/22
 */
public interface Communicator {
    /**
     * 消息发送
     * @param message 消息数据
     * @return 是否发送成功
     */
    boolean sendMessage(String message);
}
