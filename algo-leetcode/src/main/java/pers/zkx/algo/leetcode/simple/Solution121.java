package pers.zkx.algo.leetcode.simple;

/**
 * 买卖股票的最佳时机
 *
 * @author: zhangkuixing
 * @date: 2025/7/15 23:35
 */
public class Solution121 {
    public int maxProfit(int[] prices) {
        int[][] dp = new int[prices.length][2];
        for (int i = 0; i < prices.length; i++) {
            if (i == 0) {
                dp[i][0] = prices[0];
                dp[i][1] = 0;
            } else {
                dp[i][0] = Math.min(dp[i - 1][0], prices[i]);
                dp[i][1] = Math.max(prices[i] - dp[i - 1][0], dp[i - 1][1]);
            }
        }
        return dp[prices.length - 1][1];
    }

    public static void main(String[] args) {
        Solution121 solution121 = new Solution121();
        int[] prices = {7, 1, 5, 3, 6, 4};
        System.out.println(solution121.maxProfit(prices)); // 输出: 5
        prices = new int[]{7, 6, 4, 3, 1};
        System.out.println(solution121.maxProfit(prices)); // 输出: 0
    }
}
