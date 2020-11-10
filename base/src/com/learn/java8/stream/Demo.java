package com.learn.java8.stream;

import java.util.Random;

/**
 * RateOfReturnDemo
 *
 * @author zhengchaohui
 * @date 2020/11/9 15:51
 */
public class Demo {
    public static void main(String[] args) {
        /**
         * 首先，我们给 Random 对象一个种子（以便程序再次运行时产生相同的输出）。
         * ints() 方法产生一个流并且 ints() 方法有多种方式的重载 — 两个参数限定了产生的数值的边界。
         * 这将生成一个随机整数流。
         * 我们用中间流操作（intermediate stream operation） distinct() 使流中的整数不重复，然后使用 limit() 方法获取前 7 个元素。
         * 接下来使用 sorted() 方法排序。最终使用 forEach() 方法遍历输出，它根据传递给它的函数对流中的每个对象执行操作。
         * 在这里，我们传递了一个可以在控制台显示每个元素的方法引用：System.out::println
         */
        new Random(47)
                .ints(5, 20)
                .distinct()
                .limit(7)
                .sorted()
                .forEach(System.out::println);
    }
}
