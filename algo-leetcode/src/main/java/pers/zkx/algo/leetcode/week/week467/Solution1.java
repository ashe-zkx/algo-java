package pers.zkx.algo.leetcode.week.week467;

/**
 * @author: zhangkuixing
 * @date: 2025/9/14 10:49
 */
public class Solution1 {
    public int earliestTime(int[][] tasks) {
        int ans = tasks[0][0] + tasks[0][1];
        for (int i = 1; i < tasks.length; i++) {
            ans = Math.min(ans, tasks[i][0] + tasks[i][1]);
        }
        return ans;
    }
}
