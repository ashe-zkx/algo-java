package pers.zkx.algo.leetcode.mid;

/**
 * 寻找重复数
 *
 * @author: zhangkuixing
 * @date: 2025/7/8 00:48
 */
public class Solution287 {

    public int findDuplicate(int[] nums) {
        int left = 1, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int count = 0;
            for (int num : nums) {
                if (num <= mid) {
                    count++;
                }
            }

            if (count <= mid) {
                // 重复数在右侧
                left = mid + 1;
            } else {
                // 重复数在左侧
                right = mid - 1;
            }
        }
        return left; // left 即为重复数
    }

}
