package pers.zkx.algo.leetcode.simple;

import java.util.Arrays;
/**
 * 1984. 学生分数的最小差值
 * https://leetcode.cn/problems/minimum-difference-between-highest-and-lowest-of-k-scores/
 *
 * @author zkx
 * @date 2024/6/12 10:159
 */
public class Solution1984 {

    public int minimumDifference(int[] nums, int k) {
        if(k<=1){
            return 0;
        }
        Arrays.sort(nums);
        int ans = Integer.MAX_VALUE;
        for(int i=0;i<nums.length;i++){
            if(i+k-1<nums.length){
                ans = Math.min(ans, nums[i+k-1]-nums[i]);
            }
        }
        return ans;
    }

}
