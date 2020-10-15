package com.learn.algoritem.sort;

import java.util.Arrays;

/**
 * ShellSort
 * 希尔排序
 * @author zhengchaohui
 * @date 2020/9/29 16:34
 */
public class ShellSort {
    public static void main(String[] args) {
        int[] arr = new int[]{3,2,6,2,5,2,1,4,7,3};
        new ShellSort().shellSort(arr);
        System.out.println(Arrays.toString(arr));

    }
    private int[] shellSort(int[] arr) {
        if (arr.length <= 1) {
            return arr;
        }
        // 第一层，增量层
        for(int d = arr.length / 2; d > 0; d /= 2) {
            for (int i = d; i < arr.length; i+=d) {
                for (int j = i; j > 0; j-=d) {
                    if (arr[j] < arr[j - d]) {
                        int temp = arr[j];
                        arr[j] = arr[j - d];
                        arr[j - d] = temp;
                    }
                }
            }
        }
        return arr;
    }
}
