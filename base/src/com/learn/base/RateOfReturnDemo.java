package com.learn.base;

import com.learn.base.DrawDemo;

import java.math.BigDecimal;
import java.util.*;

/**
 * RateOfReturnDemo
 * 最大回撤率
 * @author zhengchaohui
 * @date 2020/11/10 16:09
 */
public class RateOfReturnDemo {

    private static List<BigDecimal> rateOfReturn = new ArrayList<>();
    private static Random rand = new Random(new Random().nextLong());
    private static Integer DATA_SIZE = 250;
    private static int o = 0, d = 0;

    /**
     * 初始化（随机生成数据）
     */
    private static void init() {
        System.out.println("init......");
        rand.doubles(DATA_SIZE).limit(DATA_SIZE).forEach(value ->
                {
                    // 6位小数，向下取整
                    BigDecimal rate = new BigDecimal(value).setScale(12, 1);
                    rateOfReturn.add(rate);
                });

//        rateOfReturn.add(new BigDecimal("0.50"));
//        rateOfReturn.add(new BigDecimal("0.52"));
//        rateOfReturn.add(new BigDecimal("0.50"));
//        rateOfReturn.add(new BigDecimal("0.52"));
//        rateOfReturn.add(new BigDecimal("0.50"));
//        rateOfReturn.add(new BigDecimal("0.46"));
//        rateOfReturn.add(new BigDecimal("0.44"));
//        rateOfReturn.add(new BigDecimal("-1"));
//        rateOfReturn.add(new BigDecimal("0.44"));
//        rateOfReturn.add(new BigDecimal("-1"));
//        rateOfReturn.add(new BigDecimal("0.54"));
//        rateOfReturn.add(new BigDecimal("0.51"));
//        rateOfReturn.add(new BigDecimal("0.44"));
    }

    /**
     * 暴力
     * 时间复杂度：O(n^2)
     * 空间复杂度：O(1)
     */
    private static void violence() {
        long start = System.currentTimeMillis();

        int origin = 0, dest = 0;
        BigDecimal maxRateOfReturn = new BigDecimal("0.00");

        // 问题1：如果没有回撤，那么最大回撤率返回什么？0.00%吗？

        for (int i = 0; i < rateOfReturn.size(); i++) {
            for (int j = i; j < rateOfReturn.size(); j++) {
                // 保存最小值
                BigDecimal temp = rateOfReturn.get(i).subtract(rateOfReturn.get(j));
                if (temp.compareTo(maxRateOfReturn) >= 0) {
                    maxRateOfReturn = temp;
                    origin = i;
                    dest = j;
                }
            }
        }
        o = origin;
        d = dest;
        System.out.println("origin, pos: " + origin + ", value: " + rateOfReturn.get(origin)
                            + ", dest, pos: " + dest + ", value: " + rateOfReturn.get(dest)
                + ", 最大回撤率:" + (1.0 - (rateOfReturn.get(dest).doubleValue() / (rateOfReturn.get(origin)).doubleValue())) + "%");
        long end = System.currentTimeMillis();
        System.out.println((end - start) + "ms");
    }

    /**
     * 优化1
     * 减枝
     * 时间复杂度：O(n^2)
     * 空间复杂度：O(1)
     */
    private static void help() {
        long start = System.currentTimeMillis();
        int origin = 0, dest = 0;
        BigDecimal maxRateOfReturn = new BigDecimal("0.00");

        for (int i = 0; i < rateOfReturn.size(); i++) {
            if (i < rateOfReturn.size() - 1 && rateOfReturn.get(i + 1).compareTo(rateOfReturn.get(i)) > 0) {
                continue;
            }
            for (int j = rateOfReturn.size() - 1; j >= i ; j--) {
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
        o = origin;
        d = dest;
        System.out.println("origin, pos: " + origin + ", value: " + rateOfReturn.get(origin)
                + ", dest, pos: " + dest + ", value: " + rateOfReturn.get(dest)
                + ", 最大回撤率:" + ((rateOfReturn.get(dest).subtract(rateOfReturn.get(origin))) + "%"));
        long end = System.currentTimeMillis();
        System.out.println((end - start) + "ms");
    }

    /**
     * 优化2
     * 只用一个指针，遍历一次。
     * 最大回撤的问题，其实就是寻找该指针左边最大值和右边最小值的问题。
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     */
    private static void help2() {
        long start = System.currentTimeMillis();

        int origin = 0, dest = 0;
        BigDecimal maxRateOfReturn = new BigDecimal("0.00");
        LinkedList<BigDecimal> rightMins = new LinkedList<>();
        int[] rightIndexs = new int[rateOfReturn.size()];
        BigDecimal leftMax = rateOfReturn.get(0);
        BigDecimal rightMin = rateOfReturn.get(rateOfReturn.size() - 1);
        // 计算保存右边的最小值
        for (int i = rateOfReturn.size() - 1; i >= 0 ; i--) {
            if (rateOfReturn.get(i).compareTo(rightMin) <= 0) {
                rightMins.addFirst(rateOfReturn.get(i));
                rightIndexs[i] = i;
            } else {
                rightMins.addFirst(rightMin);
                rightIndexs[i] = rightIndexs[i + 1];

            }
        }
        System.out.println("rightMins");
        for (int i = 0; i < rightMins.size(); i++) {
            System.out.print(rightMins.get(i) + ", ");
        }
        System.out.println();
        System.out.println("rateOfReturn");
        for (int i = 0; i < rightIndexs.length; i++) {
            System.out.print(rateOfReturn.get(i) + ", ");
        }
        System.out.println();
        System.out.println("rightIndexs");
        for (int rightIndex : rightIndexs) {
            System.out.print(rightIndex + ", ");
        }
        System.out.println();
        for (int i = 0; i < rateOfReturn.size(); i++) {
            // 计算左边最大值
            if (leftMax.compareTo(rateOfReturn.get(i)) <= 0) {
                leftMax = rateOfReturn.get(i);

            }
            // rightMins.get(i)为i(包括)往右的最小值
            BigDecimal temp = rightMins.get(i).subtract(leftMax);
            if (temp.compareTo(maxRateOfReturn) <= 0) {
                maxRateOfReturn = temp;
                dest = i;

            }
        }

        o = origin;
        d = dest;
        System.out.println("origin, pos: " + origin + ", value: " + rateOfReturn.get(origin)
                + ", dest, pos: " + dest + ", value: " + rateOfReturn.get(dest) + ", maxRateOfReturn:" + maxRateOfReturn);
        long end = System.currentTimeMillis();
        System.out.println((end - start) + "ms");

    }

    /**
     * O(n)
     */
    private static void help3() {
        long start = System.currentTimeMillis();
        BigDecimal max = new BigDecimal(Integer.MIN_VALUE);
        BigDecimal min = new BigDecimal(Integer.MAX_VALUE);
        BigDecimal payback = new BigDecimal(Integer.MIN_VALUE);
        BigDecimal maxReal = BigDecimal.ZERO;
        BigDecimal minReal = BigDecimal.ZERO;
        int maxIndex = 0, minIndex = 0;
        int maxRealIndex = 0, minRealIndex = 0;
        int index = 0;
        for (BigDecimal rate : rateOfReturn) {
            if (max.compareTo(rate) <= 0)
            {
                max = rate;
                maxIndex = index;
                min = rate;
                minIndex = index;
            }
            if (min.compareTo(rate) > 0)
            {
                min = rate;
                minIndex = index;
            }

            if ((max.subtract(min)).compareTo(payback) > 0
                    && max.compareTo(min) != 0)
            {
                maxReal = max;
                maxRealIndex = maxIndex;
                minReal = min;
                minRealIndex = minIndex;
                payback = max.subtract(min);
            }
            index++;
        }

        System.out.println("最大回测：" + payback);
        System.out.println("最大值：<pos, value>" + maxRealIndex + ", " + maxReal.toString());
        System.out.println("最小值：<pos, value>" + minRealIndex + ", " + minReal.toString());

        long end = System.currentTimeMillis();
        System.out.println((end - start) + "ms");
    }



    public static void main(String[] args) {
        System.out.println("数据初始化....");
        init();
        System.out.println("数据初始化完成....");
//        System.out.println("-------------暴力算法：-------------");
//        violence();
        // 优化1
        System.out.println("--------------优化1：--------------");
//        help();
        // 优化2
        System.out.println("--------------优化2：--------------");
//        help2();
        // 绘图
        System.out.println("--------------优化2：--------------");
        help3();
//        new DrawDemo().go(rateOfReturn, o, d);

    }
}
