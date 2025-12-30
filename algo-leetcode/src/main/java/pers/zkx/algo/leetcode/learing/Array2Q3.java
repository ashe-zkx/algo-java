package pers.zkx.algo.leetcode.learing;

import java.util.*;

/**
 * Q1
 *
 * @author zhangkuixing
 * @since 2025-12-30
 */
public class Array2Q3 {
    public List<Integer> findDisappearedNumbers(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }
        List<Integer> ans = new ArrayList<>();
        for (int i=1; i<=nums.length; i++) {
            if (!set.contains(i)) {
                ans.add(i);
            }
        }
        return ans;
    }


}
