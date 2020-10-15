package com.learn.juc.executor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * ThreadPoolDemo
 * 拒绝策略：当队列满并且线程个数达到maximumPoolSize后采取的策略，
 * 比如：
 *  1. AbortPolicy（抛出异常）
 *  2. CallerRunsPolicy（使用调用者所在线程来运行任务）
 *  3. DiscardOldestPolicy（调用poll丢弃一个任务，执行当前任务）
 *  4. DiscardPolicy（默认丢弃，不抛出异常）。
 * @author zhengchaohui
 * @date 2020/10/15 10:45
 */
public class ThreadPoolDemo {
    private static final int CORE_POOL_SIZE = 5;
    private static final int MAX_POOL_SIZE = 10;
    private static final int QUEUE_CAPACITY = 100;
    private static final Long KEEP_ALIVE_TIME = 1L;

    private static final int THREAD_NUM = 10;

    public static void main(String[] args) {
        ThreadPoolExecutor executor1 = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(QUEUE_CAPACITY),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );

        ThreadPoolExecutor executor2 = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(QUEUE_CAPACITY),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );

        List<Future<String>> futureList = new ArrayList<>();
        Callable<String> callable = new MyCallable();

        for (int i = 0; i < THREAD_NUM; i++) {
            Runnable worker = new MyRunnable(i + "");
            executor1.execute(worker);

            Future<String> future = executor2.submit(callable);
            futureList.add(future);
        }

        for (Future<String> future : futureList) {
            try {
                System.out.println(future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        executor1.shutdown();
        executor2.shutdown();
        while(!executor1.isTerminated() || !executor2.isTerminated()) {}
        System.out.println("executor1 : Finished all threads");
    }

}

/**
 * Runnable实现
 */
class MyRunnable implements Runnable{

    private String command;

    public MyRunnable(String s) {
        this.command = s;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " Start. Time = " + new Date());
        processCommand();
        System.out.println(Thread.currentThread().getName() + " End. Time = " + new Date());
    }

    private void processCommand() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return this.command;
    }

}

/**
 * Callable实现（有返回值）
 */
class MyCallable implements Callable<String> {

    @Override
    public String call() throws Exception {
        return "MyCallable:" + Thread.currentThread().getName();
    }
}
