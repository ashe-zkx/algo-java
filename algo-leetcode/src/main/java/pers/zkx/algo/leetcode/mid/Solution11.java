package pers.zkx.algo.leetcode.mid;

/**
 * 11 . 盛最多水的容器
 *
 * @author: zhangkuixing
 * @date: 2025/9/13 00:08
 */
public class Solution11 {
    public int maxArea(int[] height) {
        int left = 0, right = height.length - 1;
        int maxArea = 0;
        while (left < right) {
            int area = Math.min(height[left], height[right]) * (right - left);
            maxArea = Math.max(maxArea, area);
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }
        return maxArea;
    }
}
