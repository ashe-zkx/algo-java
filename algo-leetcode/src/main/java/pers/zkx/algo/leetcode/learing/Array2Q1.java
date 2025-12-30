package pers.zkx.algo.leetcode.learing;

import java.util.HashSet;
import java.util.Set;

/**
 * Q1
 *
 * @author zhangkuixing
 * @since 2025-12-30
 */
public class Array2Q1 {
    public int[] findErrorNums(int[] nums) {
        int[] ans = new int[2];
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            if (set.contains(num)) {
                ans[0] = num;
            } else {
                set.add(num);
            }
        }
        for (int i=1; i<=nums.length; i++) {
            if (!set.contains(i)) {
                ans[1] = i;
                break;
            }
        }
        return ans;
    }

}
