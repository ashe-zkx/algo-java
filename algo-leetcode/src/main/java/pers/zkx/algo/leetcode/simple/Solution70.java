package pers.zkx.algo.leetcode.simple;

/**
 * 70 - 爬楼梯
 *
 * @author: zhangkuixing
 * @date: 2025/9/6 00:48
 */
public class Solution70 {
    public int climbStairs(int n) {
        if (n == 0) {
            return 0;
        } else if (n == 1) {
            return 1;
        } else if (n == 2) {
            return 2;
        }
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;
        dp[2] = 2;
        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }
}
