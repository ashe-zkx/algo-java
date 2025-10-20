package pers.zkx.algo.leetcode.mid;

import java.util.HashSet;
import java.util.Set;

/**
 * 162. 寻找峰值
 *
 * @author: zhangkuixing
 * @date: 2025/9/27 00:19
 */
public class Solution162 {
    public int findPeakElement(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] > nums[mid + 1]) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }


}
