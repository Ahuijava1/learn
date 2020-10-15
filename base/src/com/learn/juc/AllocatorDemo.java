package com.learn.juc;

import java.util.ArrayList;
import java.util.List;

/**
 * AllocatorDemo
 * 破坏死锁四大条件之一的请求并保持条件
 * @author zhengchaohui
 * @date 2020/9/17 17:39
 */
public class AllocatorDemo {

    public static void main(String[] args) {
        Account src = new Account();
        src.name = "小明";
        Account target = new Account();
        target.name = "小红";
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                src.transfer(target, 20);
            }).start();
            new Thread(() -> {
                target.transfer(src, 20);
            }).start();
        }
    }
}

class Allocator{

    private List<Object> resources = new ArrayList<>(2);
    /**
     *     一次请求所有资源，这里要求两个资源都没有被使用才能加入
     */
    synchronized boolean apply(Object src, Object target) {
        if (resources.contains(src) || resources.contains(target)) {
            return false;
        }
        resources.add(src);
        resources.add(target);
        return true;
    }

    /**
     *     归还资源
     */
    synchronized void free(Object src, Object target){
        resources.remove(src);
        resources.remove(target);
    }
}

class Account{
    private Allocator allocator = new Allocator();
    private int balance = 100;
    String name;
    void transfer(Account target, int amt){
        // 一直等待
        while (!allocator.apply(this, target)){}

        try {
            synchronized (this) {
                synchronized (target) {
                    if (this.balance >= amt) {
                        this.balance -= amt;
                        target.balance += amt;
                        System.out.println("转账成功！" + this.name + " : " + this.balance + target.name + " : " + target.balance);
                    } else {
                        System.out.println("转账失败！" + this.name + " : " + this.balance + target.name + " : " + target.balance);
                    }
                }
            }
        } finally {
            allocator.free(this, target);
        }
    }
}
