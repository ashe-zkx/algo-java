package pers.zkx.algo.leetcode.simple;

/**
 * 搜索插入位置
 *
 * @author: zhangkuixing
 * @date: 2025/7/8 00:34
 */
public class Solution35 {
    public int searchInsert(int[] nums, int target) {
        if (nums == null || nums.length == 0)
            return 0;
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return left;
    }
}
