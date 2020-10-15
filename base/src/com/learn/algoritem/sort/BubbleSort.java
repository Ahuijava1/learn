package com.learn.algoritem.sort;

import java.util.Arrays;

/**
 * BubbleSort
 * 冒泡排序
 * @author zhengchaohui
 * @date 2020/9/29 16:08
 */
public class BubbleSort {
    public static void main(String[] args) {
        int[] arr = new int[]{3,2,6,2,5,2,1,4,7,3};
        new BubbleSort().bubbleSort(arr);
        System.out.println(Arrays.toString(arr));

    }

    private int[] bubbleSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            boolean flag = true;
            for (int j = 0; j < arr.length - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    flag = false;
                }
            }

            if (flag) {
                return arr;
            }
        }
        return arr;
    }
}
