package com.zh.ch.bigdata.c3p0;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author xzc
 * @description
 * @date 2021/04/10
 */
public class TestCase {

    public static void main(String[] args) throws Exception{
        Connection conn=null;

        conn=DBConnectServer.getInstance().getConnection();
        String sql="select * from request";
        Statement stmt=conn.createStatement();
//        ResultSet res=stmt.executeQuery();
//        while (res.next()){
//            System.out.println(res.getString("request_context"));
//        }

        String sql1 = "update request set request_context='安装服务' where request_id=409";
        stmt.execute(sql1);
        // 连续数据库

    }
}
