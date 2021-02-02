package com.learn.juc.thread;

/**
 * YieldTest
 * yield方法例子
 * @author ZhengChaoHui
 * @date 2021/2/2 22:47
 */
public class YieldTest implements Runnable{
    public static void main(String[] args) {
        new Thread(new YieldTest()).start();
        new Thread(new YieldTest()).start();
        new Thread(new YieldTest()).start();
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            if (i == 0) {
                System.out.println(Thread.currentThread() + " yield cpu...");
                // 让出执行权，这里进入到了就绪状态，也是有可能下一次就被cpu调度到的
                Thread.yield();
            }
        }
        System.out.println(Thread.currentThread() + " over.");
    }
}
