package pers.zkx.algo.leetcode.mid;

/**
 * @author: zhangkuixing
 * @date: 2025/8/19 00:55
 */
public class Solution918 {
    public int maxSubarraySumCircular(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int[] dpMax = new int[nums.length];
        int[] dpMin = new int[nums.length];
        dpMin[0] = nums[0];
        dpMax[0] = nums[0];
        int maxSum = nums[0];
        int minSum = nums[0];
        for (int i = 1; i < nums.length; i++) {
            dpMax[i] = Math.max(dpMax[i - 1] + nums[i], nums[i]);
            dpMin[i] = Math.min(dpMin[i - 1] + nums[i], nums[i]);

            maxSum = Math.max(maxSum, dpMax[i]);
            minSum = Math.min(minSum, dpMin[i]);
        }
        int totalSum = 0;
        for (int num : nums) {
            totalSum += num;
        }
        if (totalSum == minSum) {
            return maxSum;
        }
        return Math.max(maxSum, totalSum - minSum);
    }
}
