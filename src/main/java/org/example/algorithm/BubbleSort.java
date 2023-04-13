package org.example.algorithm;

import java.util.Arrays;

/**
 * 冒泡排序算法
 * （1）比较前后相邻的二个数据，如果前面数据大于后面的数据，就将这二个数据交换。
 * （2）这样对数组的第 0 个数据到 N-1 个数据进行一次遍历后，最大的一个数据就“沉”到数组第N-1 个位置。
 * （3）N=N-1，如果 N 不为 0 就重复前面二步，否则排序完成。
 *
 * @author arthur
 */
public class BubbleSort {
    public static void main(String[] args) {
        int[] arr = {3, 9, -1, 10, -2};
        //bubbleSort(arr);
        bubbleSort1(arr);
        System.out.println(Arrays.toString(arr));
    }


    public static void bubbleSort(int[] arr) {
        //冒泡排序的时间复杂度为O(n*n)
        int temp = 0;//临时变量
        for (int j = 0; j < arr.length - 1; j++) {
            for (int i = 0; i < arr.length - 1 - j; i++) {
                if (arr[i] > arr[i + 1]) {
                    //三角交换
                    temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                }
            }
        }
    }

    /**
     * 优化有的排序
     * @param arr
     */
    public static void bubbleSort1(int[] arr) {
        //冒泡排序的时间复杂度为O(n*n)
        int temp = 0;//临时变量
        boolean flag = false;//用于优化冒泡排序，判断是否进行过交换
        for (int j = 0; j < arr.length - 1; j++) {
            for (int i = 0; i < arr.length - 1 - j; i++) {
                if (arr[i] > arr[i + 1]) {
                    //三角交换
                    temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                    flag = true;
                }
            }
            //如果没有进入三角交换则证明数组已经有序，直接退出循环即可
            //如果进入了三角交换，把flag赋值为false，来判断下一次循环是否进入三角交换
            if (!flag) {
                break;
            } else {
                flag = false;
            }
        }
    }
}
