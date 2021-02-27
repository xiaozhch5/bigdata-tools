package com.zh.ch.bigdata.base.util.java;

import com.zh.ch.bigdata.base.util.shell.ClientHost;
import com.zh.ch.bigdata.base.util.shell.ShellResult;
import com.zh.ch.bigdata.base.util.shell.ShellUtil;
import junit.framework.TestCase;

public class ShellUtilTest extends TestCase {

    public void testExecuteCmd() {
        ClientHost clientHost = new ClientHost();
        clientHost.setHost("host165");
        clientHost.setPort(22);
        clientHost.setUsername("bdp");
        clientHost.setPassword("Pass.166");
        String[] args = {"yarn  application -list -appStates RUNNING"};
        ShellResult shellResult = ShellUtil.sshExecuteCmd(args, clientHost);
        String[] results = shellResult.getStdout().split("\n");
        for (String result : results) {
            if (result.contains("restoreTest")) {
                System.out.println(result.split(" ")[0]);
            }
        }
    }

    public void testExecuteShell() {
    }

    public void testSshUploadFile() {
    }

    public void testSshExecuteCmd() {
    }
}