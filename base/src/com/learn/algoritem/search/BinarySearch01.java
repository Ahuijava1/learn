package com.learn.algoritem.search;

/**
 * BinarySearch01
 * 二分查找
 *      最多查询logN次
 *      数组必须为有序（默认为从小到大排序）
 *  最好   平均    最坏
 *  O(1) O(logN) O(logN)
 * @author zhengchaohui
 * @date 2020/8/11 14:03
 */
public class BinarySearch01 {
    private final static int ARRAY_LENGTH = 100_000_000;
    public static void main(String[] args) {

        int[] array = new int[ARRAY_LENGTH];
        for (int i = 0; i < ARRAY_LENGTH; i++) {
            array[i] = i;
        }
        // 目标值
        int target = ARRAY_LENGTH - 1;

        // 二分查询
        System.out.println("---------------------二分查询-----------------------------");
        long start = System.currentTimeMillis();

        int targetIndex = new BinarySearch01().binarySearch(array, target);
        System.out.println("targetIndex: " + targetIndex);
        System.out.println("time: " + (System.currentTimeMillis() - start));

        // 遍历查询
        System.out.println("---------------------遍历查询-----------------------------");
        long start1 = System.currentTimeMillis();
        for (int i = 0; i < array.length; i++) {
            if (array[i] == target){
                System.out.println("targetIndex: " + i);
            }
        }
        System.out.println("time: " + (System.currentTimeMillis() - start1));

    }

    /**
     * 二分查找
     * @param array 待查询数组
     * @param target 目标值
     */
    private int  binarySearch(int[] array, int target){
        if (array.length == 0){
            return -1;
        }
        if (array.length == 1){
            return array[0] == target?0:-1;
        }
        int low = 0;
        int high = array.length - 1;
        int mid = (low + high)/2;

        while (low <= high){
            // 找到返回
            if (array[mid] == target){
                return mid;
            // 中间值大于目标值，指针往左移动
            } else if (array[mid] > target){
                high = mid - 1;
            // 中间值小于目标值，指针往右移动
            } else {
                low = mid + 1;
            }
            // 更新中间下标
            mid = (low + high)/2;
        }

        return -1;
    }
}
