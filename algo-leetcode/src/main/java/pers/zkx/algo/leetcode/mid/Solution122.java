package pers.zkx.algo.leetcode.mid;

/**
 * 买卖股票的最佳时机2
 *
 * @author: zhangkuixing
 * @date: 2025/7/15 23:48
 */
public class Solution122 {

    public int maxProfit(int[] prices) {
        // 0：有股票
        // 1. 无股票
        int[][] dp = new int[prices.length][2];
        for (int i = 0; i < prices.length; i++) {
            if (i == 0) {
                // 第一天买入股票
                dp[i][0] = -prices[0];
                // 第一天没有股票
                dp[i][1] = 0;
            } else {
                // 第i天有股票的最大收益 = i-1天有股票的最大收益 或者 i-1天没有股票的最大收益 - 今天买入股票的价格
                dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] - prices[i]);
                // 第i天没有股票的最大收益 = i-1天没有股票的最大收益 或者 i-1天有股票的最大收益 + 今天卖出股票的价格
                dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] + prices[i]);
            }
        }
        return dp[prices.length - 1][1];
    }

    public static void main(String[] args) {
        Solution122 solution122 = new Solution122();
        int[] prices = {7, 1, 5, 3, 6, 4};
        System.out.println(solution122.maxProfit(prices)); // 输出: 7
        prices = new int[]{1, 2, 3, 4, 5};
        System.out.println(solution122.maxProfit(prices)); // 输出: 4
        prices = new int[]{7, 6, 4, 3, 1};
        System.out.println(solution122.maxProfit(prices)); // 输出: 0
    }
}
