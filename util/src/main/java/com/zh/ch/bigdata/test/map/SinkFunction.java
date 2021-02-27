package com.zh.ch.bigdata.test.map;

/**
 * @author xzc
 * @description
 * @date 2021/02/08
 */
public interface SinkFunction<T> {

    void open() throws Exception;

    void invoke(T value) throws Exception;

    void close() throws Exception;
}
