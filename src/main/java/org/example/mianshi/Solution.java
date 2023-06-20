package org.example.mianshi;

import java.util.Arrays;

/**
 * @author arthur
 */
public class Solution {

    public  static ListNode removeNthFormEnd(ListNode head,int n){

        ListNode p=head;
        ListNode q=head;
        for(int i=0;i<n;i++){
            p=p.next;
        }
        if(p==null){
            head=head.next;
            return head;
        }
        while(p.next!=null){
            p=p.next;
            q=q.next;
        }
        q.next=q.next.next;
        return head;
    }

    /**
     * 第一种解法：合并数组排序，找到中位数
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int[] result = Arrays.copyOf(nums1, nums1.length + nums2.length);
        System.arraycopy(nums2, 0, result, nums1.length, nums2.length);
        Arrays.sort(result);
        double d = (double) (result[(nums1.length + nums2.length - 1) / 2] + result[(nums1.length + nums2.length) / 2])/ 2;
        return d;
    }



    /**
     * 第三种解法：二分查找法
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public static double findMedianSortedArrays3(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        int left = (m + n + 1) / 2;
        int right = (m + n + 2) / 2;
        return (find(nums1, 0, nums2, 0, left) + find(nums1, 0, nums2, 0, right)) / 2.0;
    }

    /**
     * 在nums1和nums2中找出第k小的元素
     *
     * @param nums1 nums1数组
     * @param i     nums1数组的起始位置
     * @param nums2 nums2数组
     * @param j     nums2数组的起始位置
     * @param k     需要找到的元素的序号
     * @return 第k小的元素值
     */
    public static int find(int[] nums1, int i, int[] nums2, int j, int k) {
        if (i >= nums1.length)
            return nums2[j + k - 1];
        if (j >= nums2.length)
            return nums1[i + k - 1];
        if (k == 1) {
            return Math.min(nums1[i], nums2[j]);
        }

        int mid1 = (i + k / 2 - 1 < nums1.length) ? nums1[i + k / 2 - 1] : Integer.MAX_VALUE;
        int mid2 = (j + k / 2 - 1 < nums2.length) ? nums2[j + k / 2 - 1] : Integer.MAX_VALUE;
        if (mid1 < mid2) {
            return find(nums1, i + k / 2, nums2, j, k - k / 2);
        } else {
            return find(nums1, i, nums2, j + k / 2, k - k / 2);
        }
    }

    public static void main(String[] args) {
        int[] nums1 = { 9, 10, 11, 12, 13, 14 };
        int[] nums2 = { 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21 };
//      int[] nums1 = { 1 };
//      int[] nums2 = { 2 };
        System.out.println(findMedianSortedArrays3(nums1, nums2));
    }
}
