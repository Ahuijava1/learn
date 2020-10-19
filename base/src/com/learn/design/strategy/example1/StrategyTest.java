package com.learn.design.strategy.example1;

/**
 * StrategyTest
 * 测试类
 * @author zhengchaohui
 * @date 2020/10/19 15:24
 */
public class StrategyTest {
    public static void main(String[] args) {

        Context context = new Context();

        context.setStrategy(new OperationAdd());
        System.out.println(context.exec(10, 5));

        context.setStrategy(new OperationSubtract());
        System.out.println(context.exec(10, 5));

        context.setStrategy(new OperationMultiply());
        System.out.println(context.exec(10, 5));
    }
}
