package com.learn.algoritem;

import com.learn.java8.stream.DrawDemo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * RateOfReturnDemo
 * 最大回撤率
 * @author zhengchaohui
 * @date 2020/11/10 16:09
 */
public class RateOfReturnDemo {

    private static List<BigDecimal> rateOfReturn = new ArrayList<>();
    private static Random rand = new Random(new Random().nextLong());
    private static int o = 0, d = 0;
    private static void init() {
        System.out.println("init......");
        rand.doubles(150).limit(150).forEach(value ->
                {
                    BigDecimal rate = new BigDecimal(value).setScale(6, 1);
                    rateOfReturn.add(rate);
                });

//        rateOfReturn.add(new BigDecimal("0.52"));
//        rateOfReturn.add(new BigDecimal("0.73"));
//        rateOfReturn.add(new BigDecimal("0.74"));
//        rateOfReturn.add(new BigDecimal("0.55"));
//        rateOfReturn.add(new BigDecimal("0.51"));
//        rateOfReturn.add(new BigDecimal("0.58"));
//        rateOfReturn.add(new BigDecimal("0.57"));
//        rateOfReturn.add(new BigDecimal("0.51"));
//        rateOfReturn.add(new BigDecimal("0.52"));
//        rateOfReturn.add(new BigDecimal("0.58"));
//        rateOfReturn.add(new BigDecimal("0.54"));
//        rateOfReturn.add(new BigDecimal("0.51"));
//        rateOfReturn.add(new BigDecimal("0.52"));
    }

    /**
     * 暴力
     * 时间复杂度：O(n^2)
     * 空间复杂度：O(1)
     */
    private static void violence() {
        int origin = 0, dest = 0;
        BigDecimal maxRateOfReturn = new BigDecimal("0.0");

        // 问题1：如果没有回撤，那么最大回撤率返回什么？0.00%吗？

        for (int i = 0; i < rateOfReturn.size(); i++) {
            for (int j = i; j < rateOfReturn.size(); j++) {
                // 保存最小值
                BigDecimal temp = rateOfReturn.get(j).subtract(rateOfReturn.get(i));
                if (temp.compareTo(maxRateOfReturn) <= 0) {
                    maxRateOfReturn = temp;
                    origin = i;
                    dest = j;
                }
            }
        }
        o = origin;
        d = dest;
        System.out.println("origin, pos: " + origin + ", value: " + rateOfReturn.get(origin)
                            + ", dest, pos: " + dest + ", value: " + rateOfReturn.get(dest) + ", maxRateOfReturn:" + maxRateOfReturn);
    }

    /**
     * 优化1
     */
    private static void help() {
        int origin = 0, dest = 0;
        BigDecimal maxRateOfReturn = new BigDecimal("0.0");

        // 问题1：如果没有回撤，那么最大回撤率返回什么？0.00%吗？

        for (int i = 0; i < rateOfReturn.size(); i++) {
            if (i < rateOfReturn.size() - 1 && rateOfReturn.get(i + 1).compareTo(rateOfReturn.get(i)) > 0) {
                continue;
            }
            for (int j = i; j < rateOfReturn.size(); j++) {

                // 如果右指针比左指针的值大，continue
                if (rateOfReturn.get(j).compareTo(rateOfReturn.get(i)) > 0) {
                    continue;
                }

                // 保存最小值
                BigDecimal temp = rateOfReturn.get(j).subtract(rateOfReturn.get(i));
                if (temp.compareTo(maxRateOfReturn) <= 0) {
                    maxRateOfReturn = temp;
                    origin = i;
                    dest = j;
                }
            }
        }
        System.out.println("origin, pos: " + origin + ", value: " + rateOfReturn.get(origin)
                + ", dest, pos: " + dest + ", value: " + rateOfReturn.get(dest) + ", maxRateOfReturn:" + maxRateOfReturn);
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("数据初始化....");
        init();
        System.out.println("数据初始化完成....");
        // 暴力算法
        long start = System.currentTimeMillis();
        violence();
        long end = System.currentTimeMillis();
        System.out.println((end - start) + "ms");
        // 优化
        help();
        System.out.println((System.currentTimeMillis() - end) + "ms");
        new DrawDemo().go(rateOfReturn, o, d);
    }
}
