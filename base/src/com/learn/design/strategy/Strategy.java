package com.learn.design.strategy;

/**
 * Strategy
 * 策略类
 * @author zhengchaohui
 * @date 2020/10/19 15:19
 */
public interface Strategy {

    /**
     * 操作方法
     * @param a 操作数1
     * @param b 操作数2
     * @return int
     */
    int doOperation(int a, int b);
}
