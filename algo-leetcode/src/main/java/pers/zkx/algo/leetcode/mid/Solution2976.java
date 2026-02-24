package pers.zkx.algo.leetcode.mid;

import java.util.*;

/**
 * Solution2976
 *
 * @author zhangkuixing
 * @since 2026-01-29
 */
public class Solution2976 {

    public long minimumCost(String source, String target, char[] original, char[] changed, int[] cost) {
        int[][] dp = new int[26][26];
        int inf = Integer.MAX_VALUE / 2;
        for (int i=0;i<26;i++){
            Arrays.fill(dp[i], inf);
        }

        int len = original.length;
        for (int i = 0; i < len; i++) {
            int u = original[i] - 'a';
            int v = changed[i] - 'a';
            dp[u][v] = Math.min(dp[u][v], cost[i]);
        }
        for (int k = 0; k < 26; k++) {
            for (int i = 0; i < 26; i++) {
                for (int j = 0; j < 26; j++) {
                    if (dp[i][k] != inf && dp[k][j] != inf) {
                        dp[i][j] = Math.min(dp[i][j], dp[i][k] + dp[k][j]);
                    }
                }
            }
        }
        long totalCost = 0;
        int n = source.length();
        for (int i = 0; i < n; i++) {
            int u = source.charAt(i) - 'a';
            int v = target.charAt(i) - 'a';
            if (u == v) continue;

            if (dp[u][v] == inf) {
                return -1;
            }
            totalCost += dp[u][v];
        }
        return totalCost;
    }



    public static void main(String[] args) {
        Solution2976 solution2976 = new Solution2976();
        String source = "aaadbdcdac";
        String target = "cdbabaddba";
        // ["a","c","b","d","b","a","c"]
        char[] original = {'a', 'c', 'b','d','b','a','c'};
        // ["c","a","d","b","c","b","d"]
        char[] changed = {'c', 'a', 'd','b','c','b','d'};
        //[7,2,1,3,6,1,7]
        int[] cost = {7,2,1,3,6,1,7};
        long res = solution2976.minimumCost(source, target, original, changed, cost);
        System.out.println(res);
    }

}
