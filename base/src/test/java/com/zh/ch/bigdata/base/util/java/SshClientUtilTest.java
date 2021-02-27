package com.zh.ch.bigdata.base.util.java;

import com.zh.ch.bigdata.base.util.shell.SshClientUtil;
import junit.framework.TestCase;

import java.io.InputStream;

public class SshClientUtilTest extends TestCase {

    public void testInitConnection() {
    }

    public void testExec() {
        try {
            SshClientUtil sshClientUtil = new SshClientUtil();
            boolean isConnected = sshClientUtil.initConnection("hadoop1", "root", "m98Edicines");
            System.out.println(isConnected);
            InputStream inputStream = sshClientUtil.exec("ls");
            int temp = 0;
            int foot = 0;
            byte[] data = new byte[1024];
            while((temp = inputStream.read()) != -1){
                data[foot ++] = (byte) temp;
            }

            System.out.println(data);
        } catch (Exception e) {
        }


    }
}