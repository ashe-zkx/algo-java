package pers.zkx.algo.leetcode.mid;

/**
 * 加油站
 *
 * @author: zhangkuixing
 * @date: 2025/7/19 23:07
 */
public class Solution134 {
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int totalGas = 0;
        int totalCost = 0;
        int currentGas = 0;
        int startIndex = 0;

        for (int i = 0; i < gas.length; i++) {
            totalGas += gas[i];
            totalCost += cost[i];
            currentGas += gas[i] - cost[i];
            // 如果当前油量不足以到达下一个加油站，则重置起点
            if (currentGas < 0) {
                startIndex = i + 1;
                currentGas = 0;
            }
        }
        // 如果总油量小于总消耗，则无法完成环路
        return totalGas < totalCost ? -1 : startIndex;
    }


}
