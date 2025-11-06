package pers.zkx.algo.leetcode.week.week466;

import java.util.*;

/**
 * Q3 碗子数组的数目，选择一个l，r，使得两边高，中间低
 *
 * @author: zhangkuixing
 * @date: 2025/9/7 10:48
 */
public class Solution3 {


    // 保留原始 O(n^2) 暴力用于验证（未做任何微调）
    private long bowlSubarraysBrute(int[] nums) {
        if (nums == null || nums.length < 3) {
            return 0;
        }
        long ans = 0;
        for (int i = 0; i < nums.length - 2; i++) {
            int leftValue = nums[i];
            int midMaxValue = nums[i + 1];
            int rightValue = nums[i + 2];
            int minSideValue = Math.min(leftValue, rightValue);
            if (leftValue <= midMaxValue) {
                continue;
            }
            if (minSideValue > midMaxValue) {
                ans++;
            }
            for (int j = i + 3; j < nums.length; j++) {
                midMaxValue = Math.max(midMaxValue, rightValue);
                rightValue = nums[j];
                minSideValue = Math.min(leftValue, rightValue);
                if (leftValue <= midMaxValue) {
                    break;
                }
                if (minSideValue > midMaxValue) {
                    ans++;
                }
            }
        }
        return ans;
    }
}
