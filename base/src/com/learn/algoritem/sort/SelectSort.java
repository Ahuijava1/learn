package com.learn.algoritem.sort;

/**
 * SelectSort
 * 选择排序
 *      O(n2)
 * @author zhengchaohui
 * @date 2020/8/11 14:42
 */
public class SelectSort {
    public static void main(String[] args) {

        int[] array = new int[]{1,2,6,2,5,2};
        new SelectSort().selectSort1(array);
        new SelectSort().selectSort2(array);
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + ", ");
        }

    }

    /**
     * 找到最大值替换
     * @param array 待排序数组
     */
    private void selectSort1(int[] array){

        if (array.length == 0 || array.length == 1){
            return;
        }

        for (int i = 0; i < array.length; i++) {
            int max = array[0];
            int maxIndex = 0;
            for (int j = 0; j < array.length - i; j++) {
               if (max < array[j]){
                   max = array[j];
                   maxIndex = j;
               }
            }
            int temp = array[maxIndex];
            array[maxIndex] = array[array.length - i - 1];
            array[array.length - i - 1] = temp;

        }
    }

    /**
     * 找到最小值替换
     *      这种更简洁
     * @param array 待排序数组
     */
    private void selectSort2(int[] array){
        if (array.length == 0 || array.length == 1){
            return;
        }

        // 为什么这里可以减一呢？
        // 因为最后一个数已经不用排序了
        for (int i = 0; i < array.length - 1; i++) {
            int min = i;
            for (int j = i; j < array.length ; j++) {
                if (array[min] > array[j]){
                    min = j;
                }
            }
            int temp = array[i];
            array[i] = array[min];
            array[min] = temp;

        }
    }

}
