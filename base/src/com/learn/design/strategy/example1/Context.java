package com.learn.design.strategy.example1;

/**
 * Context
 * 上下文类
 * @author zhengchaohui
 * @date 2020/10/19 15:23
 */
public class Context {

    private Strategy strategy;

    public Context() {}

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public int exec(int a, int b) {
        return strategy.doOperation(a, b);
    }
}
