package com.zh.ch.bigdata.mysql.util;

import com.zh.ch.bigdata.exception.ProjectException;
import junit.framework.TestCase;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class MysqlClientTest extends TestCase {

    public void testBuild() throws ClassNotFoundException, SQLException, ProjectException, IOException {
        MysqlClient m = new MysqlClient();
        Connection connection = m.build();


    }
}