package com.zh.ch.bigdata.thread.example.futuretest;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author xzc
 * @description
 * @date 2021/02/03
 */
public class FutureTest {

  public static void main(String[] args) {

    ThreadFactory threadFactory =
        new ThreadFactoryBuilder().setNameFormat("futureTask-thread-").build();

    ExecutorService executorService =
        new ThreadPoolExecutor(
            10,
            20,
            200L,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(),
            threadFactory);

    // 第一种方式
    Task task = new Task();
    FutureTask<Integer> futureTask = new FutureTask<Integer>(task);
    executorService.submit(futureTask);
    executorService.shutdown();

    // 第二种方式，注意这种方式和第一种方式效果是类似的，只不过一个使用的是ExecutorService，一个使用的是Thread
    /*Task task = new Task();
    FutureTask<Integer> futureTask = new FutureTask<Integer>(task);
    Thread thread = new Thread(futureTask);
    thread.start();*/

    try {
      Thread.sleep(1000);
    } catch (InterruptedException e1) {
      e1.printStackTrace();
    }

    try {
      // futureTask其实是Runnable+Future的综合体，因此可以通过futureTask.get()获取执行结果
      System.out.println("task运行结果" + futureTask.get());
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }

    System.out.println("所有任务执行完毕");
  }
}

class Task implements Callable<Integer> {
  @Override
  public Integer call() throws Exception {
    System.out.println("子线程在进行计算");
    Thread.sleep(3000);
    int sum = 0;
    for (int i = 0; i < 100; i++) {
      sum += i;
    }
    return sum;
  }
}
