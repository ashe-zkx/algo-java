package pers.zkx.algo.leetcode.learing;

/**
 * Q2
 *
 * @author zhangkuixing
 * @since 2025-12-30
 */
public class Array1Q2 {
    public int[] shuffle(int[] nums, int n) {
        int [] ans = new int[nums.length];
        for (int i=0; i<n; i++) {
            ans[2*i] = nums[i];
            ans[2*i+1] = nums[i+n];
        }
        return ans;
    }
}
