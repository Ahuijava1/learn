package com.learn.java8.stream;

import java.util.Random;
import java.util.stream.Stream;

/**
 * RandomGenerators
 * 随机数流
 * @author zhengchaohui
 * @date 2020/11/9 16:23
 */
public class RandomGenerators {
    public static <T> void show(Stream<T> stream) {
        stream.limit(4).forEach(System.out::println);
        System.out.println("----------------------------");
    }
    public static void main(String[] args) {
        Random rand = new Random(10086);
        show(rand.ints().boxed());
        show(rand.doubles().boxed());
        show(rand.longs().boxed());

        // 上下限
        System.out.println("上下限");
        show(rand.ints(5, 20).boxed());
        show(rand.longs(5, 20).boxed());
        show(rand.doubles(5, 20).boxed());

        // 控制流大小
        System.out.println("控制流大小");
        show(rand.ints(2).boxed());
        show(rand.longs(2).boxed());
        show(rand.doubles(2).boxed());

        // 控制流的大小和上下限
        System.out.println("控制流的大小和上下限");
        show(rand.ints(2, 5, 20).boxed());
        show(rand.longs(2, 5, 20).boxed());
        show(rand.doubles(2, 5, 20).boxed());
    }
}
