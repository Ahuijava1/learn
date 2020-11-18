package com.learn.base.po;

import java.math.BigDecimal;
import java.util.Date;

/**
 * RateDailyRecord
 * 每天保存的数据
 * @author zhengchaohui
 * @date 2020/11/17 15:57
 */
public class RateDailyRecord {

    /**
     * 当天收益率
     */
    private BigDecimal rate;

    /**
     * 交易频率
     */
    private Integer tradeFreq;

    /**
     * 日期
     */
    private Date date;

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getTradeFreq() {
        return tradeFreq;
    }

    public void setTradeFreq(Integer tradeFreq) {
        this.tradeFreq = tradeFreq;
    }

    public RateDailyRecord(BigDecimal rate, Date date) {
        this.rate = rate;
        this.date = date;
    }

    public RateDailyRecord(BigDecimal rate, Integer tradeFreq, Date date) {
        this.rate = rate;
        this.tradeFreq = tradeFreq;
        this.date = date;
    }


    @Override
    public String toString() {
        return "RateDailyRecord{" +
                "rate=" + rate +
                ", tradeFreq=" + tradeFreq +
                ", date=" + date +
                '}';
    }
}
