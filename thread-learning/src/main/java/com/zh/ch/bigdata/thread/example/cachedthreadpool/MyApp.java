package com.zh.ch.bigdata.thread.example.cachedthreadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author xzc
 * @description cachedThreadPool实例
 * @date 2021/01/16
 */
public class MyApp {
    public static void main(String[] args) throws InterruptedException {
        // 不允许下面这样子做
        ExecutorService cachedThreadPool = Executors.newFixedThreadPool(20);
        for (int i=0;i<10;i++){
            final int finalI = i;
            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(10);
                        te(Thread.currentThread().getName()+"   正在处理："+ finalI);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        //提交任务，
        cachedThreadPool.shutdown();
        //等待所有子线程处理完成，超过15秒则不等待，让他异步处理。
        boolean b = cachedThreadPool.awaitTermination(15000, TimeUnit.MILLISECONDS);
    }

    public static void te(String s) throws InterruptedException {
        Thread.sleep(100);
        System.out.println(s);
    }
}
