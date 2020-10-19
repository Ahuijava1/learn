package com.learn.design.singleton;

/**
 * Minister
 * 大臣类
 * @author zhengchaohui
 * @date 2020/10/19 17:06
 */
public class Minister {
    public static void main(String[] args) {
        Emperor emperor = Emperor.getEmperor();
        emperor.saySomething();
        Emperor.saySomething();

        Emperor1 emperor1 = Emperor1.getEmperor1();
        emperor1.saySomething();
        Emperor1.saySomething();

        for (int i = 0; i < 10; i++) {
            Emperor2.saySomething();
        }
    }
}
