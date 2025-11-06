package pers.zkx.algo.leetcode.week.week466;

import java.util.Arrays;

/**
 * Q1：数组元素相等的最小操作数,可以选择任意子数组，并将每个
 *
 * @author: zhangkuixing
 * @date: 2025/9/7 10:30
 */
public class Solution1 {

    public int minOperations(int[] nums) {
        int value = nums[0];
        boolean flag = true;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != value) {
                flag = false;
                break;
            }
        }
        if (flag) {
            return 0;
        } else {
            return 1;
        }
    }
}
