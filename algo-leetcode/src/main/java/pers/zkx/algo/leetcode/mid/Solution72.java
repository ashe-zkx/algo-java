/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2025 All Rights Reserved.
 */
package pers.zkx.algo.leetcode.mid;

/**
 * 72. 编辑距离
 * @author kui yuan
 * @version $Id: Solution72, v 0.1 2025-12-11 23:51 your_name Exp $
 */
public class Solution72 {
    public int minDistance(String word1, String word2) {
        int n = word1.length();
        int m = word2.length();
        // dp[i][j] represents the minimum number of operations required to convert word1[0..i-1] to word2[0..j-1]
        int[][] dp = new int[n + 1][m + 1];
        // Initialize the base cases
        for (int i = 1; i <= n; i++) {
            dp[i][0] = i;
        }
        for (int j = 1; j <= m; j++) {
            dp[0][j] = j;
        }
        // Fill the dp table
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                // If the characters at the current positions are the same, no operation is needed
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    // Otherwise, take the minimum of the three possible operations
                    dp[i][j] = Math.min(dp[i - 1][j] + 1, Math.min(dp[i][j - 1] + 1, dp[i - 1][j - 1] + 1));
                }
            }
        }
        // The minimum number of operations required is stored in dp[n][m]
        return dp[n][m];
    }
}
