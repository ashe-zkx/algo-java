package pers.zkx.algo.leetcode.learing;

/**
 * Q1
 *
 * @author zhangkuixing
 * @since 2025-12-30
 */
public class Array1Q1 {
    public int[] getConcatenation(int[] nums) {
        int [] ans = new int[nums.length*2];
        for (int i=0; i<nums.length; i++) {
            ans[i] = nums[i];
            ans[i+nums.length] = nums[i];
        }
        return ans;
    }

}
