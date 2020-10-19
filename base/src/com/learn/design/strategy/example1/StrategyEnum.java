package com.learn.design.strategy.example1;

/**
 * StrategyEnum
 * 策略枚举
 * @author zhengchaohui
 * @date 2020/10/19 15:47
 */
public enum StrategyEnum {
    /**
     *
     */
    ADD("+"){
        @Override
        int doOperation(int a, int b) {
            return a + b;
        }
    },
    SUB("-"){
        @Override
        int doOperation(int a, int b) {
            return a - b;
        }
    };

    String value = "";
    private StrategyEnum(String _value) {
        this.value = _value;
    }

    public String getValue() {
        return value;
    }

     abstract int doOperation(int a, int b);
}


class Client {
    public static void main(String[] args) {
        System.out.println(StrategyEnum.ADD.doOperation(1,2));
        System.out.println(StrategyEnum.SUB.doOperation(2,1));
    }
}