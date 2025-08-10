package pers.zkx.algo.leetcode.mid;

import java.util.Arrays;

/**
 * @author: zhangkuixing
 * @date: 2025/8/11 00:24
 */
public class Solution128 {
    public int longestConsecutive(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        // 排序
        Arrays.sort(nums);
        int maxLength = 1;
        int currentLength = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != nums[i - 1]) { // 跳过重复元素
                if (nums[i] == nums[i - 1] + 1) {
                    currentLength++;
                } else {
                    maxLength = Math.max(maxLength, currentLength);
                    currentLength = 1;
                }
            }
        }
        maxLength = Math.max(maxLength, currentLength);
        return maxLength;
    }

    public int longestConsecutive2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        // 使用哈希表
        java.util.Set<Integer> numSet = new java.util.HashSet<>();
        for (int num : nums) {
            numSet.add(num);
        }
        int maxLength = 0;
        for (int num : numSet) {
            if (!numSet.contains(num - 1)) {
                int currentNum = num;
                int currentLength = 1;
                while (numSet.contains(currentNum + 1)) {
                    currentNum++;
                    currentLength++;
                }
                maxLength = Math.max(maxLength, currentLength);
            }
        }
        return maxLength;
    }
}
