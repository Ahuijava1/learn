package com.learn.juc;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * AtomicLong的简单使用
 * @author ZhengChaoHui
 * @date 2020/7/8 19:03
 *
 */
public class Atomic {

    /**
     * 新建AtomicLong
     */
    private static AtomicLong atomicLong = new AtomicLong();
    private static Integer[] arrayOne = new Integer[]{23,0,0,7,82,21,0};
    private static Integer[] arrayTwo = new Integer[]{12,45,6,2,63,12,0};

    private static final int CORE_POOL_SIZE = 5;
    private static final int MAX_POOL_SIZE = 10;
    private static final int QUEUE_CAPACITY = 100;
    private static final Long KEEP_ALIVE_TIME = 1L;

    public static void main(String[] args){

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(QUEUE_CAPACITY),
                new ThreadPoolExecutor.CallerRunsPolicy());

        executor.submit(getThread(arrayOne));
        executor.submit(getThread(arrayTwo));

        executor.shutdown();
        while (!executor.isTerminated()){}


        //输出
        System.out.println("The num of 0 is : " + atomicLong.get());
    }

    private static Runnable getThread(Integer[] arr) {
        return () -> {
            for (Integer integer : arr) {
                if (integer == 0) {
                    atomicLong.getAndIncrement();
                }
            }
        };
    }

}
