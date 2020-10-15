package com.learn.algoritem.leetcode;

import java.util.Arrays;

/**
 * MyPow 数值的整数次方
 *  实现函数double Power(double base, int exponent)，求base的exponent次方。不得使用库函数，同时不需要考虑大数问题。
 *
 * @author zhengchaohui
 * @date 2020/9/11 14:09
 */
public class MyPow {

    public static void main(String[] args) {
//        System.out.println(new MyPow().myPow(
//                2.00000
//                        ,-2147483647));
        new MyPow().test1();
    }

    private void test1(){
        Demo demo = new Demo();
        demo.change(demo.str, demo.ch);
        System.out.println(demo.str);
        System.out.println(Arrays.toString(demo.ch));

    }

    class Demo {
        String str = new String("hello");
        char[] ch = {'a', 'b'};
        public void change(String str, char[] ch) {
            str = "ok";
            ch[0] = 'c';
        }
    }

    public double myPow(double x, int n) {
        double result = 0L;
        if (n == 0) {
            return x;
        }
        while(n != 0){
            int currBit;
            if ((currBit = n & 1)  == 1) {

            }

        }
        return result;
    }
}
