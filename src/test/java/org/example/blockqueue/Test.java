package org.example.blockqueue;

import java.util.Arrays;
import java.util.List;

/**
 * @author arthur
 */
public class Test {

    public static void main(String[] args) {
        int[] nums = new int[]{21, 55, 55, 101, 156, 156, 187};
        int i = removeDuplicates(nums);
        System.out.println(nums);
        int [] b = Arrays.copyOfRange(nums,0,i);//b:[3,9]
        System.out.println(b);

        int[] nums1 = new int[]{0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
        //slow =0 fast=0
        //比较nums[0]和nums[0]相等，fast++
        //比较nums[0]和nums[1]不相等，slow++，nums[slow]=nums[fast] 0,1,1,1,1,2,2,3,3,4

    }

    public static int removeDuplicates(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        int slow = 0, fast = 0;
        while (fast < nums.length) {
            if (nums[fast] != nums[slow]) {
                slow++;
                // 维护 nums[0..slow] 无重复
                nums[slow] = nums[fast];
            }
            fast++;
        }
        // 数组长度为索引 + 1
        return slow + 1;
    }
}
