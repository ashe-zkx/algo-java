/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2025 All Rights Reserved.
 */
package pers.zkx.algo.leetcode.mid;

/**
 * 2483. 商店的最小代价
 * @author kui yuan
 * @version $Id: Solution2483, v 0.1 2025-12-26 00:44 your_name Exp $
 */
public class Solution2483 {
    public int bestClosingTime(String customers) {
        if (customers==null || customers.equals("")) {
            return 0;
        }
        int n = customers.length();
        int[] prefixC = new int[n+1];
        int[] suffixN = new int[n+1];
        int sum =0;
        for (int i = 0; i < n; i++) {
            if (customers.charAt(i)=='N') {
                sum++;
            }
            prefixC[i+1]=sum;
        }
        sum=0;
        for (int i = n-1; i >=0 ; i--) {
            if (customers.charAt(i)=='Y') {
                sum++;
            }
            suffixN[i]=sum;
        }
        int minCost = Integer.MAX_VALUE;
        int bestHour = 0;
        for (int i = 0; i <= n; i++) {
            int cost = prefixC[i] + suffixN[i];
            if (cost < minCost) {
                minCost = cost;
                bestHour = i;
            }
        }
        return bestHour;
    }

    public static void main(String[] args) {
        Solution2483 solution2483 = new Solution2483();
        String customers = "YYNY";
        int result = solution2483.bestClosingTime(customers);
        System.out.println(result); // Expected output: 2
    }
}
