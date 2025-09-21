package pers.zkx.algo.leetcode.week.week467;

import java.util.*;

/**
 * @author: zhangkuixing
 * @date: 2025/9/14 10:52
 */
public class Solution2 {

    public int[] maxKDistinct(int[] nums, int k) {
        Arrays.sort(nums);
        List<Integer> list = new ArrayList<>();
        Set<Integer> set = new HashSet<>();
        for (int i = nums.length - 1; i >= 0; i--) {
            if (!set.contains(nums[i])) {
                set.add(nums[i]);
                list.add(nums[i]);
            }
            if (set.size() == k) {
                break;
            }
        }
        return list.stream().mapToInt(Integer::intValue).toArray();
    }
}
