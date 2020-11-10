package com.learn.java8.stream;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

/**
 * StreamOf
 * 将一组元素转化成为流
 * @author zhengchaohui
 * @date 2020/11/9 15:59
 */
public class StreamOf {

    public static void main(String[] args) {
        // 输出：a pie! It's for wonderful day （非有序，但好像每次都是这个顺序）
        Set<String> w = new HashSet<>(Arrays.asList("It's a wonderful day for pie!".split(" ")));
        w.stream().map(a -> a + " ").forEach(System.out::print);

        System.out.println();
        System.out.println("-----------------------------------");

        // 输出：It's a wonderful day for pie! （有序）
        Stream.of("It's ", "a ", "wonderful ", "day ", "for ", "pie!")
                .forEach(System.out::print);
    }
}
