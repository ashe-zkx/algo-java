package pers.zkx.algo.leetcode.week.week467;

import java.util.Arrays;

/**
 * @author: zhangkuixing
 * @date: 2025/9/14 10:59
 */
public class Solution3 {
    public boolean[] subsequenceSumAfterCapping(int[] nums, int k) {
        Arrays.sort(nums);
        boolean[] ans = new boolean[nums.length];
        boolean[][] dp = new boolean[nums.length + 1][k + 1];
        int totalSum = nums[0];
        dp[0][nums[0]] = true;
        for (int i = 1; i < nums.length; i++) {
            totalSum += nums[i];
            dp[i][0] = true;
            for (int j = 0; j <= k; j++) {
                if (j > nums[i]) {
                    dp[i][j] = dp[i - 1][j - nums[i]];
                } else if (j < nums[i]) {
                    dp[i][j] = dp[i - 1][j];
                } else if (j == nums[i]) {
                    dp[i][j] = true;
                }
            }
        }
        if (totalSum < k) {
            return ans;
        }
        boolean haveExactK = false;
        for (int i = 0; i < nums.length; i++) {
            if (haveExactK) {
                ans[i] = true;
                continue;
            }
            int key = i + 1;
            int idx = findFirstLowEqual(nums, key);
            int keyNum = nums.length;
            if (idx >= 0) {
                keyNum = nums.length - idx - 1;
            }

            if (idx == -1) {
                if (k % key == 0 && k / key <= nums.length) {
                    ans[i] = true;
                    continue;
                } else {
                    continue;
                }
            }

            if (dp[idx][k]) {
                ans[i] = true;
                haveExactK = true;
                continue;
            } else {
                int indexNum = 1;
                while (indexNum <= keyNum && (k - indexNum * key) >= 0) {
                    if (dp[idx][k - indexNum * key]) {
                        ans[i] = true;
                        break;
                    }
                    indexNum++;
                }
                if (k % key == 0 && k / key <= keyNum) {
                    ans[i] = true;
                }
            }
        }
        return ans;
    }


    private int findFirstLowEqual(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int mid = left + (right - left + 1) / 2;
            if (nums[mid] <= target) {
                left = mid;
            } else {
                right = mid - 1;
            }
        }
        if (nums[left] > target) {
            return -1;
        }
        return left;
    }

    public static void main(String[] args) {
        Solution3 solution3 = new Solution3();
        int[] nums = {1, 2, 3, 4, 5};
        int k = 3;
        boolean[] ans = solution3.subsequenceSumAfterCapping(nums, k);
        System.out.println(Arrays.toString(ans));

        System.out.println(solution3.findFirstLowEqual(nums, k));
    }
}
