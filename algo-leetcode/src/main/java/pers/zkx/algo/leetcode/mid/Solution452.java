package pers.zkx.algo.leetcode.mid;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author: zhangkuixing
 * @date: 2025/8/11 00:31
 */
public class Solution452 {
    public int findMinArrowShots(int[][] points) {
        if (points == null || points.length == 0) {
            return 0;
        }
        Arrays.sort(points, Comparator.comparingInt(a -> a[1]));
        int arrows = 1;
        int currentEnd = points[0][1];
        for (int i = 1; i < points.length; i++) {
            if (points[i][0] > currentEnd) {
                arrows++;
                currentEnd = points[i][1];
            }
        }
        return arrows;
    }

}
