/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2025 All Rights Reserved.
 */
package pers.zkx.algo.leetcode.week2.w171;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author kui yuan
 * @version $Id: MaxPoints, v 0.1 2025-12-06 23:00 your_name Exp $
 */
public class MaxPoints {
    public long maxPoints(int[] technique1, int[] technique2, int k) {
        long ans = 0;
        int n = technique1.length;
        Set<Integer> set = new HashSet<>();
        List<Long> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            long num1 = technique1[i];
            long num2 = technique2[i];
            if (num1 == num2 || num1 > num2) {
                ans+=num1;
                set.add(i);
            }else{
                ans+=num2;
                list.add(num1-num2);
            }
        }
        if (set.size()  >= k) {
            return ans;
        }else{
            list.sort((a,b)->Long.compare(b,a));
            int size = k - set.size();
            for (int i = 0; i < size; i++) {
                ans += list.get(i);
            }
        }
        return ans;
    }
}
