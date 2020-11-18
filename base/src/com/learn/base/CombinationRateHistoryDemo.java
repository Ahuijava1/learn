package com.learn.base;

import com.learn.base.po.RateDailyRecord;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * CombinationRateHistoryDemo
 * （暂时针对一个组合策略）
 * 计算近一个月、近三个月、近一年和成立以来的组合收益率
 *
 * @author zhengchaohui
 * @date 2020/11/17 15:52
 */
public class CombinationRateHistoryDemo {

    /**
     * 所有收益
     */
    private static List<RateDailyRecord> rateDailyRecords = new ArrayList<>();
    /**
     * 近一个月、近三个月、近一年和成立以来
     */
    private static BigDecimal[] rates = {
            new BigDecimal("0.0"),
            new BigDecimal("0.0"),
            new BigDecimal("0.0"),
            new BigDecimal("0.0")};

    private static BigDecimal[] tradeFreq =  {
            new BigDecimal("0.0"),
            new BigDecimal("0.0"),
            new BigDecimal("0.0"),
            new BigDecimal("0.0")};

    private static int[] days = {0, 0, 0, 0};

    /*
     * 数据init
     */
    static {
        Calendar calendar = Calendar.getInstance();
        rateDailyRecords.add(new RateDailyRecord(new BigDecimal(-5), 1, calendar.getTime()));
        calendar.add(Calendar.DATE, -1);
        rateDailyRecords.add(new RateDailyRecord(new BigDecimal(10), 2, calendar.getTime()));
        calendar.add(Calendar.DATE, -1);
        rateDailyRecords.add(new RateDailyRecord(new BigDecimal(-10), 0, calendar.getTime()));
        calendar.add(Calendar.DATE, -1);
        rateDailyRecords.add(new RateDailyRecord(new BigDecimal(-5), 1, calendar.getTime()));
        calendar.add(Calendar.DATE, -1);
        rateDailyRecords.add(new RateDailyRecord(new BigDecimal(-10), 0, calendar.getTime()));
        calendar.add(Calendar.DATE, -1);
        rateDailyRecords.add(new RateDailyRecord(new BigDecimal(5), 2, calendar.getTime()));
        calendar.add(Calendar.DATE, -30);
        rateDailyRecords.add(new RateDailyRecord(new BigDecimal(0), 1, calendar.getTime()));
        calendar.add(Calendar.DATE, -60);
        rateDailyRecords.add(new RateDailyRecord(new BigDecimal(5), 0, calendar.getTime()));
        calendar.add(Calendar.DATE, -365);
        rateDailyRecords.add(new RateDailyRecord(new BigDecimal(0), 3, calendar.getTime()));

        rateDailyRecords = rateDailyRecords.stream().sorted(Comparator.comparing(RateDailyRecord::getDate).reversed()).collect(Collectors.toList());
        rateDailyRecords.forEach(System.out::println);
        System.out.println("----------------------");
    }

    public static void main(String[] args) {
        Calendar monthCalendar = Calendar.getInstance();
        monthCalendar.add(Calendar.DATE, -30);
        monthCalendar.set(Calendar.HOUR_OF_DAY, 24);
        monthCalendar.set(Calendar.MINUTE, 0);
        monthCalendar.set(Calendar.SECOND, 0);

        Calendar quarterlyCalendar = Calendar.getInstance();
        quarterlyCalendar.add(Calendar.DATE, -90);
        quarterlyCalendar.set(Calendar.HOUR_OF_DAY, 24);
        quarterlyCalendar.set(Calendar.MINUTE, 0);
        quarterlyCalendar.set(Calendar.SECOND, 0);

        Calendar yearCalendar = Calendar.getInstance();
        yearCalendar.add(Calendar.DATE, -365);
        yearCalendar.set(Calendar.HOUR_OF_DAY, 24);
        yearCalendar.set(Calendar.MINUTE, 0);
        yearCalendar.set(Calendar.SECOND, 0);

        rateDailyRecords.forEach(e -> {

            if (e.getDate().getTime() > monthCalendar.getTimeInMillis()) {
                // 近30天
                rates[0] = rates[0].add(e.getRate());
                tradeFreq[0] = tradeFreq[0].add(BigDecimal.valueOf(e.getTradeFreq()));
                days[0]++;
                System.out.println(e);
            } else if (e.getDate().getTime() > quarterlyCalendar.getTimeInMillis()) {
                // 近90天
                rates[1] = rates[1].add(e.getRate());
                tradeFreq[1] = tradeFreq[1].add(BigDecimal.valueOf(e.getTradeFreq()));
                days[1]++;
            } else if (e.getDate().getTime() > yearCalendar.getTimeInMillis()) {
                // 近365天
                rates[2] = rates[2].add(e.getRate());
                tradeFreq[2] = tradeFreq[2].add(BigDecimal.valueOf(e.getTradeFreq()));
                days[2]++;
            } else {
                // 成立以来
                rates[3] = rates[3].add(e.getRate());
                tradeFreq[3] = tradeFreq[3].add(BigDecimal.valueOf(e.getTradeFreq()));
                days[3]++;
            }
        });

        // 叠加
        for (int i = 1; i < rates.length ; i++) {
            rates[i] = rates[i].add(rates[i - 1]);
            tradeFreq[i] = tradeFreq[i].add(tradeFreq[i - 1]);
            days[i] += days[i - 1];
        }

//        rates[1] = rates[1].add(rates[0]);
//        rates[2] = rates[2].add(rates[1]);
//        rates[3] = rates[3].add(rates[2]);
//
//        tradeFreq[1] = tradeFreq[1].add(tradeFreq[0]);
//        tradeFreq[2] = tradeFreq[2].add(tradeFreq[1]);
//        tradeFreq[3] = tradeFreq[3].add(tradeFreq[2]);
//
//        days[1] += days[0];
//        days[2] += days[1];
//        days[3] += days[2];

        for (int i = 0; i < rates.length; i++) {
            System.out.println("收益：" + rates[i] + "%");
            System.out.println("交易频率：" + tradeFreq[i].multiply(BigDecimal.valueOf(100)).divide(BigDecimal.valueOf(days[i]), 2, BigDecimal.ROUND_DOWN) + "%");
        }
    }
}
