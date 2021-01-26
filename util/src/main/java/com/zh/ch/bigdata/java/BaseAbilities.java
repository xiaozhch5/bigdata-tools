package com.zh.ch.bigdata.java;

/**
 * @author xzc
 * @description
 * @date 2021/01/21
 */
public class BaseAbilities {

    public static void whileFunctionTest() {
        int a = 1;
        while (true) {
            if (a > 5) {
                break;
            }
            System.out.println(a);
            a++;
        }
    }

    public static void main(String[] args) {
        whileFunctionTest();
    }
}
