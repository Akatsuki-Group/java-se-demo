package org.example.algorithm;

/**
 * 希尔排序是首个时间复杂度突破O(n^2)的排序算法，它是对前面说到的插入排序的改进版。下面就来看看它究竟是怎么进行排序的吧。
 * <p>
 * 希尔排序就是先定义一个增量，然后根据这个增量将几个元素分成一组，最终可能会得到很多个组，在组内进行插入排序，每个组都完成排序后再缩小增量，再排序，直到增量为1，也就是所有排序都成为了一组，这个时候就变成了最简单的插入排序了。
 * <p>
 * 接合图来将，第一遍，84,50为一组，83,70为一组，88,60为一组，87,80为一组，61,99为一组，然后在组内进行插入排序。第二遍50,60,61,83,87为一组，70,80,84,88,99为一组，然后进行插入排序，第三遍所有元素为一组，进行插入排序。
 * <p>
 * 比较前面讲到的插入排序算法，插入排序算法有可能会遇到一个非常坏的情况，就是数组末尾的元素是一个非常小的数，他要进行插入就要逐个比较相邻的元素，如果这个数组非常长，那么就要比较很多次了，而希尔排序减少了这种可能，数组末尾比较小的数在增量比较大的时候就已经插入到前面去了，所以希尔排序比传统的插入排序更快！
 * <p>
 * 原文链接：https://blog.csdn.net/qq_48219653/article/details/121191661
 *
 * @author arthur
 */
public class ShellSort {
    public static void main(String[] args) {
        //定义数组
        int[] arr = {99, 55, 2, 3, 9, 10, 22, 34, 67, 89, 69, 92, 101, 102};
        //增量
        int gap = arr.length;
        //排序
        sort(arr, gap);
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }

    public static void sort(int[] arr, int gap) {
        //确定新一轮分组的增量
        gap = gap / 2;
        //对数组进行分组
        for (int i = 0; i < gap; i++) {
            for (int j = i + gap; j < arr.length; j += gap) {
                //获取当前元素，然后在本组内部向前比较并排序
                int current = arr[j];
                for (int k = j - gap; k >= i; k -= gap) {
                    if (arr[k] > current) {
                        //插入
                        arr[k + gap] = arr[k];
                        arr[k] = current;
                    }
                }
            }
        }

        if (gap > 1) {
            sort(arr, gap);
        }
    }
}
