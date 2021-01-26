package com.zh.ch.bigdata.hadoop.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.security.UserGroupInformation;

import java.io.IOException;

/**
 * @author xzc
 * @description 认证kerberos读取hdfs目录
 * @date 2020/12/07
 */
public class GetHdfsData {


    public static void test1(String user, String keytab, String dir, String hdfsSite, String coreSite, String krb5Conf) throws Exception {
        Configuration conf = new Configuration();
        conf.addResource(new Path(hdfsSite));
        conf.addResource(new Path(coreSite));
        conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");
        System.setProperty("java.security.krb5.conf", krb5Conf);
        UserGroupInformation.setConfiguration(conf);
        UserGroupInformation.loginUserFromKeytab(user, keytab);
        listDir(conf, dir);
    }

    public static void listDir(Configuration conf, String dir) throws IOException {
        FileSystem fs = FileSystem.get(conf);
        FileStatus[] files = fs.listStatus(new Path(dir));
        for (FileStatus file : files) {
            System.out.println(file.getPath());
        }
    }

    public static void main(String[] args) {
        // kerberos用户
        String user = args[0];
        // kerberos用户对应的keytab
        String keytab = args[1];
        // 待读取目录
        String dir = args[2];
        // hdfs-site.xml文件路径
        String hdfsSite = args[3];
        // core-site.xml文件路径
        String coreSite = args[4];
        // krb5.conf文件路径
        String krb5Conf = args[5];
        try {
            test1(user, keytab, dir, hdfsSite, coreSite, krb5Conf);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
