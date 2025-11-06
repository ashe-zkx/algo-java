package pers.zkx.algo.leetcode.week.week468;

/**
 * @author: zhangkuixing
 * @date: 2025/9/21 10:29
 */
public class Solution1 {
    public int evenNumberBitwiseORs(int[] nums) {
        int res = 0;
        for (int num : nums) {
            if ((num & 1) == 0) {
                res |= num;
            }
        }
        return res;
    }
}
