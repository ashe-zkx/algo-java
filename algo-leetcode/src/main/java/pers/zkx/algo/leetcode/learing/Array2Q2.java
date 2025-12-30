package pers.zkx.algo.leetcode.learing;

import java.util.*;

/**
 * Q1
 *
 * @author zhangkuixing
 * @since 2025-12-30
 */
public class Array2Q2 {
    public int[] smallerNumbersThanCurrent(int[] nums) {
        Map<Integer, Integer> sizeMap = new HashMap<>();
        for (int num : nums) {
            sizeMap.put(num, sizeMap.getOrDefault(num, 0) + 1);
        }
        int[] copy = Arrays.copyOf(nums, nums.length);
        Arrays.sort(copy);
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < copy.length; i++) {
            map.put(copy[i], Math.max(0, (i + 1) - sizeMap.get(copy[i])));
//            System.out.println(copy[i] + " " + map.get(copy[i]));
        }
        int[] ans = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            ans[i] = map.get(nums[i]);
        }
        return ans;
    }

    public static void main(String[] args) {
        Array2Q2 array2Q2 = new Array2Q2();
        int[] nums = {8,1,2,2,3};
        int[] ans = array2Q2.smallerNumbersThanCurrent(nums);
        System.out.println(Arrays.toString(ans));
    }

}
