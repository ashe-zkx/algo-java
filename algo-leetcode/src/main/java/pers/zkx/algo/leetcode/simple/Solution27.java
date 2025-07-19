package pers.zkx.algo.leetcode.simple;

/**
 * 移除元素
 *
 * @author: zhangkuixing
 * @date: 2025/7/14 23:25
 */
public class Solution27 {
    public int removeElement(int[] nums, int val) {
        int ans = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != val) {
                nums[ans] = nums[i];
                ans++;
            }
        }
        return ans;
    }
}
