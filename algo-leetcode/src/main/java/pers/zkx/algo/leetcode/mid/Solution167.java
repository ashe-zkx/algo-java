package pers.zkx.algo.leetcode.mid;

/**
 * @author: zhangkuixing
 * @date: 2025/7/23 00:21
 */
public class Solution167 {

    public int[] twoSum(int[] numbers, int target) {
        int left = 0;
        int right = numbers.length - 1;

        while (left < right) {
            if (numbers[left] + numbers[right] == target) {
                break;
            }

            int midIndex = findIndex(numbers, target - numbers[left], left + 1, right - 1);
            if (midIndex != -1) {
                right = midIndex;
                break;
            } else {
                left++;
            }
        }
        return new int[]{left + 1, right + 1};
    }

    private int findIndex(int[] numbers, int target, int start, int end) {
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (numbers[mid] == target) {
                return mid;
            } else if (numbers[mid] < target) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        return -1; // Not found
    }

    public static void main(String[] args) {
        Solution167 solution167 = new Solution167();

        int[] numbers = {2, 7, 11, 15};
        int target = 9;
        int[] result = solution167.twoSum(numbers, target);
        System.out.println("Indices: " + result[0] + ", " + result[1]); // Expected output: Indices: 1, 2
    }
}
