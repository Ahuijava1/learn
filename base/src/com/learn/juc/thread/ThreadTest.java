package com.learn.juc.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * ThreadTest
 * 线程开启的三种方式
 * @author ZhengChaoHui
 * @date 2021/2/2 22:07
 */
public class ThreadTest {
    public static void main(String[] args) {
        // 通过继承开启线程
        new MyThread().start();

        // 通过实现Runnable开启线程
        new Thread(new MyRunnable()).start();

        // 有返回值的线程
        FutureTask<String> futureTask = new FutureTask<>(new MyFutureTask());
        new Thread(futureTask).start();
        try {
            String s = futureTask.get();
            System.out.println(s);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}

/**
 * 通过继承开启线程
 */
class MyThread extends Thread{

    @Override
    public void run() {
        System.out.println("通过继承Thread开启线程");
    }
}

/**
 * 通过实现Runnable开启线程
 */
class MyRunnable implements Runnable{

    @Override
    public void run() {
        System.out.println("通过实现Runnable开启线程");
    }
}

/**
 * 有返回值
 */
class MyFutureTask implements Callable<String> {

    @Override
    public String call() throws Exception {
        return "hello";
    }
}

