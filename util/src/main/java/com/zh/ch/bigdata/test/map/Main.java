package com.zh.ch.bigdata.test.map;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xzc
 * @description
 * @date 2021/02/08
 */
public class Main {

    public static void main(String[] args) throws Exception {

        List<String> valueList = new ArrayList<>();
        valueList.add("tom");
        valueList.add("tony");


        Data<String> data = new Data<String>().fromCollection(valueList);

        data.sink(new AbstractSinkFunction<String>() {
            @Override
            public void open() throws Exception {
                super.open();
            }

            @Override
            public void invoke(String value) throws Exception {
                System.out.println(value);
            }

            @Override
            public void close() throws Exception {
                super.close();
            }
        });

    }

}
