package com.zh.ch.bigdata.test.map;

/**
 * @author xzc
 * @description
 * @date 2021/02/08
 */
public abstract class AbstractSinkFunction<T> implements SinkFunction<T>{
    @Override
    public void open() throws Exception {

    }

    @Override
    public void invoke(T value) throws Exception {

    }

    @Override
    public void close() throws Exception {

    }
}
