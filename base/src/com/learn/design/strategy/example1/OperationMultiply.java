package com.learn.design.strategy.example1;

/**
 * OperationMultiply
 * 乘法实现
 * @author zhengchaohui
 * @date 2020/10/19 15:20
 */
public class OperationMultiply implements Strategy{
    @Override
    public int doOperation(int a, int b) {
        // 可能会溢出，但是这里只是为了测试，不用理会
        return a * b;
    }
}
