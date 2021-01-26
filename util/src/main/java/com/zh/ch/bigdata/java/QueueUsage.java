package com.zh.ch.bigdata.java;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * 写个代码。
 * A线程一直（不间断）往队列（queue）中写数据。
 * B-Bn（多个）线程（不间断）从队列中读取数据。
 * 说一下思路，怎么保证线程安全？如果多个线程读重了怎么办？
 * @author xzc
 * @description
 * @date 2021/01/20
 */
public class QueueUsage {

    public static void main(String[] args) {

        Queue<String> q = new ArrayBlockingQueue<>(10);

        new Thread(() -> {
            int i = 0;
            while (true) {
                Random r = new Random();
                i = r.nextInt(10);
                q.offer(String.valueOf(i));
                System.out.println(Thread.currentThread().getName()+"写入-->" + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "写入线程1").start();

        new Thread(() -> {
            while (true) {
                try {
                    System.out.println(Thread.currentThread().getName()+"读取-->" + q.poll());
                    Thread.sleep(100);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "读取线程1").start();

        new Thread(() -> {
            while (true) {
                try {
                    System.out.println(Thread.currentThread().getName()+"读取-->" + q.poll());
                    Thread.sleep(100);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "读取线程2").start();

    }

}
