package com.learn.juc.thread;

/**
 * WaitNotifyInterrupt
 * 线程等待-打断例子
 * @author ZhengChaoHui
 * @date 2021/2/2 22:28
 */
public class WaitNotifyInterrupt {
    static final Object object = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread( () -> {
            synchronized(object) {
                System.out.println("获取锁");
                try {
                    // wait后，别的线程调用本线程的interrupt会进入到异常处理，并返回
                    object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("离开临界区");
            }
        });

        thread.start();
        Thread.sleep(1000);
        // 中断线程thread
        thread.interrupt();
    }


}
