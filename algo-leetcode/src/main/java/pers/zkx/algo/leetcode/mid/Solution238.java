package pers.zkx.algo.leetcode.mid;

/**
 * 除自身以外的乘积
 *
 * @author: zhangkuixing
 * @date: 2025/7/19 22:53
 */
public class Solution238 {
    public int[] productExceptSelf(int[] nums) {
        int[] ans = new int[nums.length];
        // 前缀积
        int prefix = 1;
        for (int i = 0; i < nums.length; i++) {
            ans[i] = prefix;
            prefix *= nums[i];
        }
        // 后缀积
        int suffix = 1;
        for (int i = nums.length - 1; i >= 0; i--) {
            ans[i] *= suffix;
            suffix *= nums[i];
        }
        return ans;
    }
}
