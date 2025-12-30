package pers.zkx.algo.leetcode.learing;

/**
 * Q3
 *
 * @author zhangkuixing
 * @since 2025-12-31
 */
public class Array1Q3 {
    public int findMaxConsecutiveOnes(int[] nums) {
        int ans=0;
        int count=0;
        for (int num : nums) {
            if (num == 1) {
                count++;
            } else {
                ans = Math.max(ans, count);
                count = 0;
            }
        }
        ans = Math.max(ans, count);
        return ans;
    }
}
