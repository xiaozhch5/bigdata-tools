package com.zh.ch.bigdata.java;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author xzc
 * @description
 * @date 2021/01/19
 */
public class ArrayListTest {

    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        list.add("1");
        list.add("2");

        list.removeIf("1"::equals);
        System.out.println(list);
    }

}
