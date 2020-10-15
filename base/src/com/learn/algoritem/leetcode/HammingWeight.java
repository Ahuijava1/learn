package com.learn.algoritem.leetcode;

/**
 * HammingWeight
 * 请实现一个函数，输入一个整数，输出该数二进制表示中 1 的个数。
 *      例如，把 9 表示成二进制是 1001，有 2 位是 1。因此，如果输入 9，则该函数输出 2。
 * @author zhengchaohui
 * @date 2020/9/11 13:57
 */
public class HammingWeight {

    public static void main(String[] args) {
        System.out.println(new HammingWeight().hammingWeight(9));
    }

    /**
     *     you need to treat n as an unsigned value
      */
    private int hammingWeight(int n) {
        int result = 0;
        do {
            if ((n & 1) == 1) {
                result++;
            }
        } while ((n >>>= 1) != 0);
        return result;
    }
}
