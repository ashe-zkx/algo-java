package pers.zkx.algo.leetcode.mid;

/**
 * 2348 全0子数组的数目
 *
 * @author: zhangkuixing
 * @date: 2025/8/19 00:03
 */
public class Solution2348 {
    public long zeroFilledSubarray(int[] nums) {
        long ans = 0;
        if (nums == null || nums.length == 0) {
            return ans;
        }
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                continue;
            }
            int j = i;
            while (j < nums.length && nums[j] == 0) {
                j++;
            }
            long len = j - i;
            // 等差
            ans += (len * (len + 1)) / 2;
            // 更新i到j-1，避免重复计算
            i = j - 1;
        }
        return ans;
    }

    public static void main(String[] args) {
        Solution2348 solution = new Solution2348();
        int[] nums = {2, 10, 2019};
        System.out.println(solution.zeroFilledSubarray(nums)); // 输出：6
    }
}
