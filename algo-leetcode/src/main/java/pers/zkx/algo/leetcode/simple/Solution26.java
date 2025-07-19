package pers.zkx.algo.leetcode.simple;

/**
 * 删除有序数组中的·重复项
 *
 * @author: zhangkuixing
 * @date: 2025/7/14 23:27
 */
public class Solution26 {
    public int removeDuplicates(int[] nums) {
        int ans = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i == 0) {
                nums[ans++] = nums[i];
            } else if (nums[i] != nums[ans - 1]) {
                nums[ans++] = nums[i];
            }
        }
        return ans;
    }
}
