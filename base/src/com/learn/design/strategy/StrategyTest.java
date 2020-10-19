package com.learn.design.strategy;

/**
 * StrategyTest
 * 测试类
 * @author zhengchaohui
 * @date 2020/10/19 15:24
 */
public class StrategyTest {
    public static void main(String[] args) {
        Context context = new Context(new OperationAdd());
        System.out.println(context.exec(10, 5));

        Context context2 = new Context(new OperationSubtract());
        System.out.println(context2.exec(10, 5));

        Context context3 = new Context(new OperationMultiply());
        System.out.println(context3.exec(10, 5));
    }
}
