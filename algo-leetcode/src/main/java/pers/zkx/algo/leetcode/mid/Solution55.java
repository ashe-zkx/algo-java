package pers.zkx.algo.leetcode.mid;

/**
 * 跳跃游戏
 *
 * @author: zhangkuixing
 * @date: 2025/7/16 23:29
 */
public class Solution55 {

    public boolean canJump(int[] nums) {
        int[] dp = new int[nums.length];
        dp[0] = 1; // 起点可以跳跃

        for (int i = 1; i < nums.length; i++) {
            dp[i] = 0; // 初始化为不可达
            for (int j = 0; j < i; j++) {
                // 如果从j可以跳到i
                if (dp[j] == 1 && j + nums[j] >= i) {
                    dp[i] = 1; // 标记i为可达
                    break; // 找到一个可达路径就可以了
                }
            }
        }
        return dp[nums.length - 1] == 1;
    }
}
