package com.zh.ch.bigdata.thread.example.userdefinethreadpool;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

/**
 * @author xzc
 * @description 用户手动创建线程池
 * @date 2021/01/16
 */
public class UserDefineThreadPool {

    public static void main(String[] args) {

        final ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(100);

        ThreadFactory namedThreadFactory =
                new ThreadFactoryBuilder().setNameFormat("thread-%d").build();
        ExecutorService taskExe =
                new ThreadPoolExecutor(
                        10,
                        20,
                        200L,
                        TimeUnit.MILLISECONDS,
                        new LinkedBlockingQueue<Runnable>(),
                        namedThreadFactory);

        taskExe.submit(
                new Runnable() {
                    @Override
                    public void run() {
                        int i = 0;
                        while (true) {
                            Random r = new Random();
                            i = r.nextInt(10);
                            try {
                                q.put(String.valueOf(i));
                                System.out.println(Thread.currentThread().getName() + "写入-->" + i);
                            } catch (InterruptedException e) {

                            }
                        }
                    }
                });

        taskExe.submit(
                new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            try {
                                if (!q.isEmpty()) {
                                    System.out.println(
                                            Thread.currentThread().getName() + "读取-->" + q.take());
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }
}
