package pers.zkx.algo.leetcode.mid;

/**
 * 198. 打家劫舍
 *
 * @author: zhangkuixing
 * @date: 2025/9/20 16:01
 */
public class Solution198 {
    public int rob(int[] nums) {
        int[] dp = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            if (i == 0) {
                dp[i] = nums[i];
            } else if (i == 1) {
                dp[i] = Math.max(nums[i], dp[i - 1]);
            } else {
                dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[i]);
            }
        }
        return dp[nums.length - 1];
    }
}
