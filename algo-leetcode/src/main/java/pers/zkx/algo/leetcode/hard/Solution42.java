package pers.zkx.algo.leetcode.hard;

import java.util.*;

/**
 * 接雨水
 *
 * @author: zhangkuixing
 * @date: 2025/7/19 23:54
 */
public class Solution42 {
    public int trap(int[] height) {
        if (height == null || height.length <= 2) {
            return 0;
        }
        int maxHeight = 0;
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < height.length; i++) {
            if (!map.containsKey(height[i])) {
                map.put(height[i], new ArrayList<>());
            }
            map.get(height[i]).add(i);
            if (maxHeight < height[i]) {
                maxHeight = height[i];
            }
        }
        int left = height.length;
        int right = -1;
        int heightNum = 0;
        int ans = 0;
        for (int i = maxHeight; i > 0; i--) {
            List<Integer> list = map.get(i);
            if (list != null && !list.isEmpty()) {
                left = Math.min(left, list.getFirst());
                right = Math.max(right, list.getLast());
            } else {
                list = new ArrayList<>();
            }
            heightNum += list.size();
            if (left == right) {
                continue;
            }
            if (left < right) {
                ans += (right - left + 1) - heightNum;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        Solution42 solution = new Solution42();
        int[] height = {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        int result = solution.trap(height);
        System.out.println("Total water trapped: " + result); // 输出应为6
    }

}
