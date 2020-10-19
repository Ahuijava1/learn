package com.learn.design.singleton;

import java.util.Random;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Emperor
 * 皇帝类
 *  饿汉模式
 * @author zhengchaohui
 * @date 2020/10/19 17:04
 */
public class Emperor {

    private static final Emperor emperor = new Emperor();

    private Emperor() {}

    public static Emperor getEmperor() {
        return emperor;
    }

    public static void saySomething() {
        System.out.println("朕是始皇帝！");
    }
}

/**
 * 懒汉模式
 */
class Emperor1 {
    /**
     * volatile防止指令重排序
     */
    private static volatile Emperor1 emperor1 = null;

    private Emperor1(){}

    /**
     * 线程安全 DCL
     * @return
     */
    public static Emperor1 getEmperor1() {
        if (emperor1 == null) {
            synchronized (Emperor1.class) {
                if (emperor1 == null) {
                    emperor1 = new Emperor1();
                }
            }
        }
        return emperor1;
    }

    public static void saySomething() {
        System.out.println("朕是始皇帝！");
    }
}

/**
 * 具体个数的多例
 */
class Emperor2 {
    private static final Integer EMPEROR_NUM = 3;
    private String name;
    private static Vector<Emperor2> vector = new Vector<>(EMPEROR_NUM);
    static {
        for (Integer i = 0; i < EMPEROR_NUM; i++) {
            vector.add(new Emperor2("始皇" + (i + 1) + "帝"));
        }
    }

    private Emperor2(String _name) {
        this.name = _name;
    }

    private String getName(){
        return name;
    }

    public static void saySomething() {
        System.out.println(vector.get(new Random().nextInt(EMPEROR_NUM)).getName() + ": 众卿平身！");
    }
}