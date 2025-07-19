package pers.zkx.algo.leetcode.mid;

/**
 * @author: zhangkuixing
 * @date: 2025/7/14 23:29
 */
public class Solution80 {
    public int removeDuplicates(int[] nums) {
        int ans = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != nums[i - 1]) {
                nums[ans++] = nums[i];
            } else {
                if (ans < 2) {
                    // 如果当前元素等于前一个元素，并且答案数组长度小于2，则保留当前元素
                    nums[ans++] = nums[i];
                    continue;
                }
                if (nums[i] == nums[ans - 2]) {
                    // 如果等于前两个元素，则跳过
                    continue;
                } else {
                    // 否则，保留当前元素
                    nums[ans++] = nums[i];
                }
            }

        }
        return ans;
    }

    public static void main(String[] args) {

        int[] nums = {0, 0, 1, 1, 1, 1, 2, 3, 3};
        Solution80 solution80 = new Solution80();
        int ans = solution80.removeDuplicates(nums);
        System.out.println(ans);
        for (int i = 0; i < ans; i++) {
            System.out.print(nums[i] + " ");
        }
        System.out.println();
    }
}
