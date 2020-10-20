package com.learn.base;

/**
 * Base
 *
 * @author zhengchaohui
 * @date 2020/10/20 15:18
 */
public class Base
{
    public void methodOne()
    {
        System.out.print("A");
        methodTwo();
    }

    public void methodTwo()
    {
        System.out.print("B");
    }

    public static void main(String[] args) {
        new Derived().methodOne();
    }
}

class Derived extends Base
{
    @Override
    public void methodOne()
    {
        super.methodOne();
        System.out.print("C");
    }

    @Override
    public void methodTwo()
    {
        super.methodTwo();
        System.out.print("D");
    }
}