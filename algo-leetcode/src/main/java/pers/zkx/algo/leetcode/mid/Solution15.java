package pers.zkx.algo.leetcode.mid;

import java.util.*;

/**
 * 15. 三数之和
 *
 * @author: zhangkuixing
 * @date: 2025/9/13 00:15
 */
public class Solution15 {

    public List<List<Integer>> threeSum(int[] nums) {
        if (nums == null || nums.length < 3) {
            return new ArrayList<>();
        }
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 2; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            if (nums[i] > 0) {
                break;
            }
            int secondIndex = i + 1;
            int thirdIndex = nums.length - 1;
            while (secondIndex < thirdIndex) {
                int sum = nums[i] + nums[secondIndex] + nums[thirdIndex];
                if (sum == 0) {
                    res.add(Arrays.asList(nums[i], nums[secondIndex], nums[thirdIndex]));
                    while (secondIndex < thirdIndex && nums[secondIndex] == nums[secondIndex + 1]) {
                        secondIndex++;
                    }
                    while (secondIndex < thirdIndex && nums[thirdIndex] == nums[thirdIndex - 1]) {
                        thirdIndex--;
                    }
                    secondIndex++;
                    thirdIndex--;
                } else if (sum < 0) {
                    secondIndex++;
                } else {
                    thirdIndex--;
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        Solution15 solution15 = new Solution15();
        int[] nums = {-1, 0, 1, 2, -1, -4};
        System.out.println(solution15.threeSum(nums));
    }

}
