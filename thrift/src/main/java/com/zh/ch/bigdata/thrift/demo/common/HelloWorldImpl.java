package com.zh.ch.bigdata.thrift.demo.common;

import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;

/**
 * @author xzc
 * @description 实现HelloWorld接口
 * @date 2020/11/15
 */
public class HelloWorldImpl implements HelloWorldService.Iface {

    @Override
    public String sayHello(String userName) throws TException {
        return "hi" + userName + "World to thrift World";
    }
}
