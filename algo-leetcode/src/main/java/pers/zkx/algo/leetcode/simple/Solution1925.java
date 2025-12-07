/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2025 All Rights Reserved.
 */
package pers.zkx.algo.leetcode.simple;

/**
 * 1925 统计平方和三元组的数目
 *
 * @author kui yuan
 * @version $Id: Solution1925, v 0.1 2025-12-08 00:22 your_name Exp $
 */
public class Solution1925 {
    public int countTriples(int n) {
        int count = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                int sum = i*i+j*j;
                int k = (int)Math.sqrt(sum);
                if (k*k==sum && k<=n){
                    count++;
                }
            }
        }
        return count;
    }

    public static void main(String[] args) {
        Solution1925 sol = new Solution1925();
        System.out.println(sol.countTriples(5));
    }

}
