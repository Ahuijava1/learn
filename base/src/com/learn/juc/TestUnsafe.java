package com.learn.juc;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * 测试一下UnSafe类
 *      通过反射获取Unsafe
 * @author ZhengChaoHui
 * @Date 2020/7/8 22:12
 */
public class TestUnsafe {
    private static Unsafe unsafe;
    private static long startOffset = 0;
    private volatile long state = 0;
    static {
        try {
            //获取Unsafe的成员变量theUnsafe
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            //设置为可用
            field.setAccessible(true);
            //获取unsafe
            unsafe = (Unsafe) field.get(null);
            //获取state在TestUnsafe的偏移位置
            startOffset = unsafe.objectFieldOffset(TestUnsafe.class.getDeclaredField("state"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        TestUnsafe testUnsafe = new TestUnsafe();
        System.out.println(testUnsafe.state);

        Boolean success = unsafe.compareAndSwapInt(testUnsafe, startOffset, 0, 1);
        //true
        System.out.println(success);
        //1
        System.out.println(testUnsafe.state);
    }
}
