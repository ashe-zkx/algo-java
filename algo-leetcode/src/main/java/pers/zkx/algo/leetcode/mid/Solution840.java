package pers.zkx.algo.leetcode.mid;

/**
 * Solution840 - 矩阵中的幻方
 *
 * @author zhangkuixing
 * @since 2025-12-30
 */
public class Solution840 {
    public int numMagicSquaresInside(int[][] grid) {
        int l = grid.length;
        if (l < 3){
            return 0;
        }
        int w = grid[0].length;
        if (w < 3){
            return 0;
        }

        int count = 0;

        for (int i=0; i<=l-3; i++) {
            for (int j=0; j<=w-3; j++) {
                if (judge(grid, i, j)) {
                    count++;
                }
            }
        }
        return count;
    }


    private boolean judge(int[][] grid, int row, int col) {
        boolean[] seen = new boolean[10];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int num = grid[row + i][col + j];
                if (num < 1 || num > 9 || seen[num]) {
                    return false;
                }
                seen[num] = true;
            }
        }

        int sum = grid[row][col] + grid[row][col + 1] + grid[row][col + 2];
        for (int i = 0; i < 3; i++) {
            int rowSum = grid[row + i][col] + grid[row + i][col + 1] + grid[row + i][col + 2];
            int colSum = grid[row][col + i] + grid[row + 1][col + i] + grid[row + 2][col + i];
            if (rowSum != sum || colSum != sum) {
                return false;
            }
        }

        int diag1 = grid[row][col] + grid[row + 1][col + 1] + grid[row + 2][col + 2];
        int diag2 = grid[row][col + 2] + grid[row + 1][col + 1] + grid[row + 2][col];
        return diag1 == sum && diag2 == sum;
    }

}
