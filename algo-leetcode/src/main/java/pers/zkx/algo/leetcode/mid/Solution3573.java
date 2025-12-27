package pers.zkx.algo.leetcode.mid;

/**
 * 3573. 股票的最大利润
 * @author: zhangkuixing
 * @date: 2025/12/17 23:48
 */
public class Solution3573 {
    public long maximumProfit(int[] prices, int k) {
        if(prices== null || prices.length==0 || k<=0){
            return 0L;
        }
        int n = prices.length;
        long[][] dp = new long[n][k+1];

        for(int j=1;j<=k;j++){
            // 维护一个maxDiff表示在第j-1次交易后，买入股票的最大利润
            long maxDiff = -prices[0];
            for(int i=1;i<n;i++){
                dp[i][j] = Math.max(dp[i-1][j], prices[i] + maxDiff);
                maxDiff = Math.max(maxDiff, dp[i][j-1] - prices[i]);
            }
        }
        return dp[n-1][k];
    }
}
