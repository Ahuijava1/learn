## ForkJoinPool

参考文章：[[怀旧并发10]分析jdk-1.8-ForkJoinPool实现原理(上)](https://www.jianshu.com/p/de025df55363)

> 顾名思义，ForkJoinPool运用了Fork/Join原理，使用“分而治之”的思想，将大任务分拆成小任务分配给多个线程执行，最后合并得到最终结果，加快运算。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201021162502254.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MTc5NjI1Nw==,size_16,color_FFFFFF,t_70#pic_center)

### 核心思想

```java
if(任务很小）{
    直接计算得到结果
}else{
    分拆成N个子任务
    调用子任务的fork()进行计算
    调用子任务的join()合并计算结果
}
```

### 代码

计算数组和

```java
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
```

### 执行原理（基于1.8）