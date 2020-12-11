package com.learn.base;

import java.math.BigDecimal;

/**
 * Demo1
 *
 * @author zhengchaohui
 * @date 2020/12/10 16:29
 */
public class Demo1 {
    private static BigDecimal amount = BigDecimal.valueOf(3000);
    private static int trade_days_of_month = 22;
    private static int years = 5;
    private static int months_of_year = 12;
    private static int days = trade_days_of_month * months_of_year * years;
    private static double rate = 1.015;
    public static void main(String[] args) {
        System.out.println("--------------- 初始资金:" + amount.setScale(4, BigDecimal.ROUND_DOWN) + "￥ ---------------");
        double monthRate = 1;
        for (int i = 0; i < days; i++) {
            monthRate *= rate;
        }
        System.out.println("一个月收益率：" + (monthRate - 1) * 100 + "%");
        BigDecimal temp = BigDecimal.valueOf(3000);
        int month = 0;
        for (int i = 0; i < days; i++) {
            month++;
            amount = amount.multiply(BigDecimal.valueOf(rate));
            if (month % trade_days_of_month == 0) {
                System.out.println("第" + (month / trade_days_of_month) + "个月，总资产："
                        + amount.setScale(4, BigDecimal.ROUND_DOWN) + "￥，盈利金额："
                        + amount.subtract(temp).setScale(4, BigDecimal.ROUND_DOWN)
                        + "￥");
                temp = amount.add(BigDecimal.ZERO);
            }
        }


    }
}
