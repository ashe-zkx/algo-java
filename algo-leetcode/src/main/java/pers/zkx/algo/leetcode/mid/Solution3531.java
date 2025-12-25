/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2025 All Rights Reserved.
 */
package pers.zkx.algo.leetcode.mid;

import java.util.HashMap;
import java.util.Map;

/**
 * 3531. 统计被覆盖的建筑
 * @author kui yuan
 * @version $Id: Solution3531, v 0.1 2025-12-11 23:07 your_name Exp $
 */
public class Solution3531 {
    
    static class Range {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        
        void update(int value) {
            min = Math.min(min, value);
            max = Math.max(max, value);
        }
        
        boolean isInside(int value) {
            return value > min && value < max;
        }
    }
    
    public int countCoveredBuildings(int n, int[][] buildings) {
        Map<Integer, Range> xRanges = new HashMap<>();
        Map<Integer, Range> yRanges = new HashMap<>();

        // 构建每个坐标轴上的范围
        for (int[] building : buildings) {
            int x = building[0], y = building[1];
            xRanges.computeIfAbsent(x, k -> new Range()).update(y);
            yRanges.computeIfAbsent(y, k -> new Range()).update(x);
        }
        
        // 统计被覆盖的建筑
        int count = 0;
        for (int[] building : buildings) {
            int x = building[0], y = building[1];
            if (xRanges.get(x).isInside(y) && yRanges.get(y).isInside(x)) {
                count++;
            }
        }
        return count;
    }
}