package pers.zkx.algo.leetcode.mid;

/**
 * 36. 有效的数独
 *
 * @author: zhangkuixing
 * @date: 2025/7/25 00:16
 */
public class Solution36 {
    public boolean isValidSudoku(char[][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                char number = board[i][j];
                if (number != '.') {
                    if (!validateNumber(board, i, j, number)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }


    private boolean validateNumber(char[][] board, int row, int col, char number) {
        for (int i = 0; i < 9; i++) {
            if (i == col) {
                continue;
            }
            if (board[row][i] == number) {
                return false;
            }
        }
        for (int i = 0; i < 9; i++) {
            if (i == row) {
                continue;
            }
            if (board[i][col] == number) {
                return false;
            }
        }

        int startRow = row / 3 * 3;
        int startCol = col / 3 * 3;
        for (int i = startRow; i < startRow + 3; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                if (i == row && j == col) {
                    continue;
                }
                if (board[i][j] == number) {
                    return false;
                }
            }
        }
        return true;
    }
}
