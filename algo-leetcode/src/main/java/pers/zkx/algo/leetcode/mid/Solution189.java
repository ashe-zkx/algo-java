package pers.zkx.algo.leetcode.mid;

/**
 * 轮转数组
 *
 * @author: zhangkuixing
 * @date: 2025/7/14 23:52
 */
public class Solution189 {
    public void rotate(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return;
        }
        int size = nums.length;
        if (k % (2 * size) == 0) {
            return;
        }
        int num = 0;
        for (int i = 0; i < size; i++) {
            int j = calIndex(i, k, size);
            if (i == j) {
                num++;
                continue;
            }
            int tmpI = i;
            int midValue = nums[i];
            while (j != tmpI && num < size) {
                // 交换 nums[i] 和 nums[j]
                int tmpValue = nums[j];
                nums[j] = midValue;

                midValue = tmpValue;
                tmpI = j;
                j = calIndex(j, k, size);


                num++;
                if (j == i) {
                    break;
                }
            }
        }
    }

    public int calIndex(int i, int k, int size) {
        return (i + k) % size;
    }

    public static void main(String[] args) {
        Solution189 solution189 = new Solution189();
//        int[] nums = {1, 2, 3, 4, 5, 6, 7};
//
//
//        solution189.rotate(nums, 3);
//        for (int num : nums) {
//            System.out.print(num + " ");
//        }
//        System.out.println();

        // -1,-100,3,99
        int[] nums2 = {-1, -100, 3, 99};
        solution189.rotate(nums2, 2);
        for (int num : nums2) {
            System.out.print(num + " ");
        }
        System.out.println();
    }


}
