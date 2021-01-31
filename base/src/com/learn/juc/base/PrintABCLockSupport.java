package com.learn.juc.base;

import java.util.concurrent.locks.LockSupport;

/**
 * PrintABCLockSupport
 * 编写一个程序，开启三个线程，这三个线程的 ID 分别是 A、B 和 C，
 * 每个线程把自己的 ID 在屏幕上打印 10 遍，
 * 要求输出结果必须按 ABC 的顺序显示，如 ABCABCABC... 依次递推
 * @author ZhengChaoHui
 * @date 2021/2/1 0:48
 */
public class PrintABCLockSupport {
    static Thread threadA, threadB, threadC;

    public static void main(String[] args) {
        threadA = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                // 先阻塞，等待其它线程唤醒
                LockSupport.park();
                System.out.print(Thread.currentThread().getName());
                LockSupport.unpark(threadB);
            }
        }, "A");
        threadB = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                // 先阻塞，等待其它线程唤醒
                LockSupport.park();
                System.out.print(Thread.currentThread().getName());
                LockSupport.unpark(threadC);
            }
        }, "B");
        threadC = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                // 先阻塞，等待其它线程唤醒
                LockSupport.park();
                System.out.print(Thread.currentThread().getName());
                LockSupport.unpark(threadA);
            }
        }, "C");

        threadA.start();
        threadB.start();
        threadC.start();
        LockSupport.unpark(threadA);
    }
}
