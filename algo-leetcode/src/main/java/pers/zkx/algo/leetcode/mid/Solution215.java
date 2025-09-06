package pers.zkx.algo.leetcode.mid;


import java.util.Random;


/**
 * 215 数组中的第K个最大元素
 *
 * @author: zhangkuixing
 * @date: 2025/9/6 22:41
 */
public class Solution215 {

    public int findKthLargest(int[] nums, int k) {
        return quickselect(nums, 0, nums.length - 1, nums.length - k);
    }


    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    int quickselect(int[] nums, int l, int r, int target) {
        if (l == r) {
            return nums[target];
        }
        int x = nums[l];
        int i = l - 1;
        int j = r + 1;
        while (i < j) {
            do {
                i++;
            } while (nums[i] < x);
            do {
                j--;
            } while (nums[j] > x);
            if (i < j) {
                swap(nums, i, j);
            }
        }
        if (target <= j) {
            return quickselect(nums, l, j, target);
        } else {
            return quickselect(nums, j + 1, r, target);
        }
    }

    public static void main(String[] args) {
        Solution215 s = new Solution215();
        System.out.println(s.findKthLargest(new int[]{3, 1, 1, 1, 1, 1}, 2));

    }
}
