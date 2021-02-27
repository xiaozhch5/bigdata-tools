package com.zh.ch.bigdata.googleguice.example;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * @author xzc
 * @description
 * @date 2021/02/22
 */
public class Communication {


    @Inject
    private Communicator communicator;

    public Communication(Boolean keepRecords) {
        if (keepRecords) {
            System.out.println("Message logging enabled");
        }
    }

    public boolean sendMessage(String message) {
        return communicator.sendMessage(message);
    }

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new BasicModule());
        Communication communication = injector.getInstance(Communication.class);
        communication.sendMessage("hello world");
    }

}
