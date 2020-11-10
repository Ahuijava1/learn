package com.learn.java8.stream.pojo;

/**
 * Bubble
 *
 * @author zhengchaohui
 * @date 2020/11/9 16:21
 */
public class Bubble {
    public final int i;

    public Bubble(int n) {
        i = n;
    }

    @Override
    public String toString() {
        return "Bubble(" + i + ")";
    }

    private static int count = 0;
    public static Bubble bubbler() {
        return new Bubble(count++);
    }
}
