package com.learn.algoritem.sort;

import java.util.Arrays;

/**
 * QuickSort
 * 快速排序
 * @author zhengchaohui
 * @date 2020/8/11 15:31
 */
public class QuickSort {
    public static void main(String[] args) {
        int[] array = new int[]{3,2,6,2,5,2,1,4,7,3};

        int low = 0;
        int high = array.length - 1;
        quickSort(array, low, high);
        System.out.println(Arrays.toString(array));
    }

    private static void quickSort(int[] array, int low, int high) {
        int l = low, h = high;
        if (low < high){
            int pivot = array[low];
            while (low < high){
                // high左移
                while (low < high && array[high] > pivot){
                    high--;
                }
                if (low < high){
                    array[low] = array[high];
                    low++;
                }
                // low右移
                while (low < high && array[low] < pivot){
                    low++;
                }
                if (low < high){
                    array[high] = array[low];
                    high--;
                }

            }

            array[low] = pivot;
            quickSort(array, l, low - 1);
            quickSort(array, low + 1, h);
        }

    }


}
