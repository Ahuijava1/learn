package com.learn.juc.thread;

/**
 * InterruptedTest
 * interrupted()方法例子
 * @author ZhengChaoHui
 * @date 2021/2/2 22:59
 */
public class InterruptedTest {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            while(!Thread.interrupted()) {

            }
            // 输出false，因为interrupted()方法清空中断标志了
            System.out.println("thread isInterrupted: " + Thread.interrupted());
        });

        thread.start();
        Thread.sleep(500);
        thread.interrupt();
        thread.join();
        System.out.println("main thread is over!");

    }
}
