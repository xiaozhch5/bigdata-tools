package com.zh.ch.bigdata.googleguice.example;

/**
 * @author xzc
 * @description
 * @date 2021/02/22
 */
public class CommunicatorImpl implements Communicator{

    /**
     * 消息发送
     *
     * @param message 消息数据
     * @return 是否发送成功
     */
    @Override
    public boolean sendMessage(String message) {
        System.out.println("send message:" + message);
        return true;
    }
}
