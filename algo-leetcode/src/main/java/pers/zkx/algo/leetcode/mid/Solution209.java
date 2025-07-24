package pers.zkx.algo.leetcode.mid;

/**
 * 长度最小的子数组
 *
 * @author: zhangkuixing
 * @date: 2025/7/25 00:03
 */
public class Solution209 {
    public int minSubArrayLen(int target, int[] nums) {
        int left = 0;
        int right = 0;
        int minLength = Integer.MAX_VALUE;
        int currentSum = 0;
        while (right < nums.length) {
            currentSum += nums[right];
            while (currentSum >= target) {
                minLength = Math.min(minLength, right - left + 1);
                currentSum -= nums[left];
                left++;
            }
            right++;
        }
        return minLength == Integer.MAX_VALUE ? 0 : minLength;
    }
}
