package pers.zkx.algo.leetcode.simple;

/**
 * 283. 移动零
 *
 * @author: zhangkuixing
 * @date: 2025/9/13 00:04
 */
public class Solution283 {
    public void moveZeroes(int[] nums) {
        int j = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                if (i != j) {
                    nums[j] = nums[i];
                    nums[i] = 0;
                }
                j++;
            }
        }
    }
}
