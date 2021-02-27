package com.zh.ch.bigdata.googleguice.example;

import com.google.inject.AbstractModule;

/**
 * @author xzc
 * @description
 * @date 2021/02/22
 */
public class BasicModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Communicator.class).to(CommunicatorImpl.class);
        bind(Communication.class).toInstance(new Communication(true));
    }
}
