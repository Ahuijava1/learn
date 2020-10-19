package com.learn.design.strategy;

/**
 * OperationAdd
 * 加法实现
 * @author zhengchaohui
 * @date 2020/10/19 15:20
 */
public class OperationAdd implements Strategy{
    @Override
    public int doOperation(int a, int b) {
        return a + b;
    }
}
