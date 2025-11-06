package pers.zkx.algo.leetcode.week.week468;

/**
 * @author: zhangkuixing
 * @date: 2025/9/21 10:29
 */
public class Solution2 {
    public long maxTotalValue(int[] nums, int k) {
        int max = -1;
        int min = Integer.MAX_VALUE;
        for (int num : nums) {
            max = Math.max(max, num);
            min = Math.min(min, num);
        }
        return (long) (max + min) * k;
    }
}
