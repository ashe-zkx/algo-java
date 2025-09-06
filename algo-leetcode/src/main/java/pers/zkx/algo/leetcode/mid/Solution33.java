package pers.zkx.algo.leetcode.mid;

/**
 * 33 搜索旋转排序数组
 *
 * @author: zhangkuixing
 * @date: 2025/8/19 00:10
 */
public class Solution33 {
    public int search(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        return find(nums, target, 0, nums.length - 1);
    }

    private int find(int[] nums, int target, int left, int right) {
        if (left > right) {
            return -1;
        }
        int mid = left + (right - left) / 2;
        if (nums[mid] == target) {
            return mid;
        }
        if (nums[left] <= nums[mid]) {
            if (nums[left] <= target && target < nums[mid]) {
                return find(nums, target, left, mid - 1);
            } else {
                return find(nums, target, mid + 1, right);
            }
        } else {
            if (nums[mid] < target && target <= nums[right]) {
                return find(nums, target, mid + 1, right);
            } else {
                return find(nums, target, left, mid - 1);
            }
        }
    }
}
