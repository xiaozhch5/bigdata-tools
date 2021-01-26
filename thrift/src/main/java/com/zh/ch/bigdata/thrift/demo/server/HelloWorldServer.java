package com.zh.ch.bigdata.thrift.demo.server;

import com.zh.ch.bigdata.thrift.demo.common.HelloWorldService;
import com.zh.ch.bigdata.thrift.demo.common.HelloWorldImpl;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

/**
 * @author xzc
 * @description HelloWorld服务端代码
 * @date 2020/11/15
 */
public class HelloWorldServer {

    public static final int SERVER_PORT = 8089;

    public  void startServer() {
        try {
            System.out.print("HelloWorld thrift Server start ...");
            TProcessor tProcessor = new HelloWorldService.Processor<HelloWorldService.Iface>(new HelloWorldImpl());

            TServerSocket tServerSocket = new TServerSocket(SERVER_PORT);
            TServer.Args args = new TServer.Args(tServerSocket);
            args.processor(tProcessor);
            args.protocolFactory(new TBinaryProtocol.Factory());
            TServer server = new TSimpleServer(args);
            server.serve();
        }
        catch (TTransportException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        HelloWorldServer helloWorldServer = new HelloWorldServer();
        helloWorldServer.startServer();
    }


}
