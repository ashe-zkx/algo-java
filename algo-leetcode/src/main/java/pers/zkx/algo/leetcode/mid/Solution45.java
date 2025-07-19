package pers.zkx.algo.leetcode.mid;

/**
 * 跳跃游戏2
 *
 * @author: zhangkuixing
 * @date: 2025/7/16 23:34
 */
public class Solution45 {
    public int jump(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int[] dp = new int[nums.length];
        dp[0] = 0;

        for (int i = 1; i < nums.length; i++) {
            dp[i] = Integer.MAX_VALUE; // 初始化为不可达
            for (int j = 0; j < i; j++) {
                // 如果从j可以跳到i
                if (j + nums[j] >= i && dp[j] != Integer.MAX_VALUE) {
                    dp[i] = Math.min(dp[i], dp[j] + 1); // 更新最小跳跃次数
                }
            }
        }
        return dp[nums.length - 1] == Integer.MAX_VALUE ? -1 : dp[nums.length - 1];
    }
}
