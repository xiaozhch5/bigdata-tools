package com.zh.ch.bigdata.test.map;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xzc
 * @description
 * @date 2021/02/08
 */
public class Data<T> {

    List<T> valueList = new ArrayList<>();

    public Data() {}

    public Data(List<T> valueList) {
        this.valueList = valueList;
    }

    public void sink(SinkFunction<T>  sinkFunction) throws Exception {
        if (sinkFunction instanceof AbstractSinkFunction) {
            sinkFunction.open();
            for (T value : valueList) {
                sinkFunction.invoke(value);
            }
            sinkFunction.close();
        }
    }

    public Data<T> fromCollection(List<T> valueList) {
        return new Data<T>(valueList);
    }


}
