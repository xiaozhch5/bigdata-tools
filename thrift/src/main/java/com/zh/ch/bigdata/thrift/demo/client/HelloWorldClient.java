package com.zh.ch.bigdata.thrift.demo.client;

import com.zh.ch.bigdata.thrift.demo.common.HelloWorldService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 * @author xzc
 * @description HelloWorld客户端代码
 * @date 2020/11/15
 */
public class HelloWorldClient {

    public static final String SERVER_HOST = "localhost";
    public static final int SERVER_PORT = 8089;
    public static final int TIMEOUT = 30000;

    public void startClient(String userName) {

        try (TTransport transport = new TSocket(SERVER_HOST, SERVER_PORT, TIMEOUT)) {
            TProtocol protocol = new TBinaryProtocol(transport);
            HelloWorldService.Client client = new HelloWorldService.Client(protocol);
            transport.open();
            String result = client.sayHello(userName);

            System.out.println("Thrift Client Result is:" + result);

        } catch (TException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        HelloWorldClient client = new HelloWorldClient();
        client.startClient("tom");
    }

}
