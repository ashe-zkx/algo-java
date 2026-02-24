package pers.zkx.algo.leetcode.simple;


/**
 * Solution3379
 *
 * @author zhangkuixing
 * @since 2026-02-05
 */
public class Solution3379 {
    public int[] constructTransformedArray(int[] nums) {
        int[] result = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                result[i] = nums[i];
            } else if (nums[i] > 0) {
                // 向右移 nums[i] 位
                int index = (i + nums[i]) % nums.length;
                result[i] = nums[index];
            } else if (nums[i] < 0) {
                // 向左移 -nums[i] 位
                if (i+nums[i]>0){
                    result[i] = nums[i+nums[i]];
                }else{
                    int index = (nums.length-( Math.abs(i + nums[i]) % nums.length))% nums.length;
                    result[i] = nums[index];
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Solution3379 solution3379 = new Solution3379();
        int[] nums = {-10,-10,-4};
        int[] result = solution3379.constructTransformedArray(nums);
        for (int num : result) {
            System.out.print(num + " ");
        }
    }

}
