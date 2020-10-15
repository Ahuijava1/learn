package com.learn.algoritem.sort;

/**
 * InsertSort
 * 插入排序
 *  从头到尾依次扫描未排序序列，将扫描到的每个元素插入有序序列的适当位置。
 *  （如果待插入的元素与有序序列中的某个元素相等，则将待插入元素插入到相等元素的后面。）
 * @author zhengchaohui
 * @date 2020/9/29 15:26
 */
public class InsertSort {
    public static void main(String[] args) {
        int[] arr = new int[]{3,2,6,2,5,2,1,4,7};
        new InsertSort().insertSort(arr);
        for (int i : arr) {
            System.out.println(i);
        }
    }

    private int[] insertSort(int[] arr) {
        if (arr.length == 0 || arr.length == 1){
            return arr;
        }
        for(int i = 1; i < arr.length; i++) {
            int temp = arr[i];
            // 第一个待排序数字与已排序队列相比（从已排序后面往前）
            int j = i;
            // 找到比它小的数的下标
            while(j > 0 && temp < arr[j - 1]) {
                arr[j] = arr[j - 1];
                j--;
            }
            if (j != i) {
                arr[j] = temp;
            }
        }

        return arr;
    }
}
