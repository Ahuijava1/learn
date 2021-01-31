package com.learn.juc.base;

/**
 * PrintABC
 * 编写一个程序，开启三个线程，这三个线程的 ID 分别是 A、B 和 C，
 * 每个线程把自己的 ID 在屏幕上打印 10 遍，
 * 要求输出结果必须按 ABC 的顺序显示，如 ABCABCABC... 依次递推
 * @author ZhengChaoHui
 * @date 2021/2/1 0:25
 */
public class PrintABC {

    private static int start = 1;

    public static void main(String[] args) {
        final Object o = new Object();
        new Thread(() -> {
            // 需要原子性
            synchronized(o) {
                for (int i = 0; i < 10;) {
                    if (start == 1) {
                        System.out.print(Thread.currentThread().getName());
                        start = 2;
                        // 唤醒所有线程
                        o.notifyAll();
                        // i必须要在这里自增，否则跑到else也能自增了
                        i++;
                    } else {
                        try {
                            // 如果不是到该线程打印，主动让出执行权
                            o.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, "A").start();
        new Thread(() -> {
            synchronized(o) {
                for (int i = 0; i < 10;) {
                    if (start == 2) {
                        System.out.print(Thread.currentThread().getName());
                        start = 3;
                        o.notifyAll();
                        i++;
                    } else {
                        try {
                            o.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, "B").start();
        new Thread(() -> {
            synchronized(o) {
                for (int i = 0; i < 10;) {
                    if (start == 3) {
                        System.out.print(Thread.currentThread().getName());
                        start = 1;
                        o.notifyAll();
                        i++;
                    } else {
                        try {
                            o.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, "C").start();
    }

}
