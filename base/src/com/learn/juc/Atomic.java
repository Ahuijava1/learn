package com.learn.juc;

import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * AtomicLong的简单使用
 * @author ZhengChaoHui
 * @Date 2020/7/8 19:03
 *
 */
public class Atomic {

    /**
     *     新建AtomicLong
     */
    private static AtomicLong atomicLong = new AtomicLong();
    private static Integer[] arrayOne = new Integer[]{23,22,0,7,82,21,0};
    private static Integer[] arrayTwo = new Integer[]{12,45,6,2,63,12,0};

    public static void main(String[] args) throws InterruptedException {
        //开启线程1
        Thread thread1 = new Thread(()-> {
            for (Integer integer : arrayOne) {
                if (integer == 0) {

                    atomicLong.incrementAndGet();
//                    atomicLong.getAndIncrement();

                }
            }
        });

        Thread thread2 = new Thread(()-> {
            for (Integer integer : arrayTwo) {
                if (integer == 0) {
                    atomicLong.incrementAndGet();

                }
            }
        });

        thread1.start();
        thread2.start();

        //等待完成
        thread1.join();
        thread2.join();

        //输出
        System.out.println("Count 0:" + atomicLong.get());
    }

}
