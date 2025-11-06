package pers.zkx.algo.leetcode.hard;

/**
 * 分发糖果
 *
 * @author: zhangkuixing
 * @date: 2025/7/19 23:29
 */
public class Solution135 {

    public int candy(int[] ratings) {
        if (ratings == null || ratings.length == 0) {
            return 0;
        }
        int[] candies = new int[ratings.length];
        for (int i = 0; i < ratings.length; i++) {
            dfs(ratings, candies, i);
        }
        int totalCandies = 0;
        for (int candy : candies) {
            totalCandies += candy;
        }
        return totalCandies;
    }

    private void dfs(int[] ratings, int[] candies, int index) {
        // 如果当前索引已经处理过，直接返回
        if (candies[index] > 0) {
            return;
        }

        // 初始化当前索引的糖果数量
        candies[index] = 1;

        // 向左检查
        if (index > 0 && ratings[index] > ratings[index - 1]) {
            dfs(ratings, candies, index - 1);
            candies[index] = Math.max(candies[index], candies[index - 1] + 1);
        }

        // 向右检查
        if (index < ratings.length - 1 && ratings[index] > ratings[index + 1]) {
            dfs(ratings, candies, index + 1);
            candies[index] = Math.max(candies[index], candies[index + 1] + 1);
        }
    }

    public static void main(String[] args) {
        Solution135 solution = new Solution135();
        int[] ratings = {1, 2, 2};
        int result = solution.candy(ratings);
        System.out.println("Total candies needed: " + result); // 输出应为5
    }
}
