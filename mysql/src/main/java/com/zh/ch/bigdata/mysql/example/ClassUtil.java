package com.zh.ch.bigdata.mysql.example;

import com.zh.ch.bigdata.base.util.java.SubClassUtil;

import java.util.List;
import java.util.Set;

/**
 * @author xzc
 * @description
 * @date 2021/02/04
 */
public class ClassUtil {

    public static void main (String[] args) throws ClassNotFoundException {
        List classNames =  SubClassUtil.getFullImplClassNames("com.zh.ch.bigdata.mysql.util.IMysqlOperation");
        System.out.println(classNames.get(0));
    }

}
