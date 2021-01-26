package com.zh.ch.bigdata.kafka.producer.example.ogg;

import junit.framework.TestCase;

public class GenOggDataTest extends TestCase {

    public void testGenerate() {

        System.out.println(GenOggData.generate(1L, 100L));

    }
}