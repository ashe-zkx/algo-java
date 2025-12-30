package pers.zkx.algo.leetcode.mid;

import java.util.Deque;

/**
 * @author: zhangkuixing
 * @date: 2025/11/19 00:13
 */
public class Solution733 {
    public int[][] floodFill(int[][] image, int sr, int sc, int color) {
        if(image[sr][sc] == color) {
            return image;
        }
        Deque<int[]> q = new java.util.ArrayDeque<>();
        q.offer(new int[]{sr, sc});
        int originalColor = image[sr][sc];
        int[] directions = new int[]{-1, 0, 1, 0, -1};
        while (!q.isEmpty()) {
            int[] polled = q.poll();
            int r = polled[0];
            int c = polled[1];
            image[r][c] = color;
            for (int i = 0; i < 4; i++) {
                int newR = r + directions[i];
                int newC = c + directions[i + 1];
                if (newR >= 0 && newR < image.length && newC >= 0 && newC < image[0].length && image[newR][newC] == originalColor) {
                    q.offer(new int[]{newR, newC});
                }
            }
        }
        return  image;
    }
}
