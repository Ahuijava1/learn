package com.learn.juc.forkjoinpool;

import java.util.Random;
import java.util.concurrent.*;

/**
 * ForkJoinAction
 *
 * @author zhengchaohui
 * @date 2020/10/21 16:01
 */
public class ForkJoinAction {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int[] arr = new int[100];
        Random random = new Random();
        int total =0;
        //初始化100个数组元素
        for(int i=0,len = arr.length;i<len;i++){
            int temp = random.nextInt(20);
            //对数组元素赋值，并将数组元素的值添加到sum总和中
            total += (arr[i]=temp);
        }
        System.out.println("初始化数组总和："+total);

        // 1.8新增的创建ForkJoinPool方法
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        // 创建task
        ForkJoinTask<Integer> task = new SumTask(arr, 0, arr.length - 1);
        // 提交
        forkJoinPool.submit(task);
        System.out.println("多线程执行结果："+task.get());
        // 关闭
        forkJoinPool.shutdown();
    }
}


class SumTask extends RecursiveTask<Integer> {

    private static final int THRESHOLD = 20;
    private int[] array;
    private int start;
    private int end;

    public SumTask(int[] array, int start, int end) {
        super();
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        if (end - start < THRESHOLD) {
            for(int i = start; i <= end; i++) {
                sum+=array[i];
            }
            return sum;
        } else {
            int mid = start + (end - start) / 2;
            SumTask left = new SumTask(array, start, mid);
            SumTask right = new SumTask(array, mid + 1, end);
            // fork分任务
            left.fork();
            right.fork();
            // join合并任务
            return left.join() + right.join();
        }
    }
}