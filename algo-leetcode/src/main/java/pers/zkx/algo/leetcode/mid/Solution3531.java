/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2025 All Rights Reserved.
 */
package pers.zkx.algo.leetcode.mid;

import java.util.Map;

/**
 * 3531. 统计被覆盖的建筑
 * @author kui yuan
 * @version $Id: Soluution3531, v 0.1 2025-12-11 23:07 your_name Exp $
 */
public class Solution3531 {
    class Node{
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
    }
    public int countCoveredBuildings(int n, int[][] buildings) {
        Map<Integer, Node> xMap = new java.util.HashMap<>();
        Map<Integer, Node> yMap = new java.util.HashMap<>();

        for(int[] building : buildings){
            int x1 = building[0];
            int y1 = building[1];

            Node xNode = xMap.computeIfAbsent(x1, k -> new Node());
            xNode.min = Math.min(xNode.min, y1);
            xNode.max = Math.max(xNode.max, y1);

            Node yNode = yMap.computeIfAbsent(y1, k -> new Node());
            yNode.min = Math.min(yNode.min, x1);
            yNode.max = Math.max(yNode.max, x1);
        }
        int count = 0;
        for(int[] building : buildings){
            int x1 = building[0];
            int y1 = building[1];

            Node xNode = xMap.get(x1);
            if (y1<=xNode.min || y1>=xNode.max){
                continue;
            }
            Node yNode = yMap.get(y1);
            if (x1<=yNode.min || x1>=yNode.max){
                continue;
            }
            count++;
        }
        return count;
    }
}
