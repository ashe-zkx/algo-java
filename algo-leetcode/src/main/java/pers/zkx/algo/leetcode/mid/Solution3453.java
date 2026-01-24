package pers.zkx.algo.leetcode.mid;

/**
 * Solution3453
 *
 * @author zhangkuixing
 * @since 2026-01-14
 */
public class Solution3453 {
    public double separateSquares(int[][] squares) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int[] square : squares) {
            min = Math.min(min, square[1]);
            max = Math.max(max, square[1] + square[2]);
        }

        double left = min;
        double right = max;
        while (left < right) {
            double mid = left + (right - left) / 2;
            int judgeRes = judge(mid, squares);
            if (judgeRes >= 0) {
                right = mid;
            } else {
                left = mid;
            }

            if (Math.abs(left - right) < 1e-5) {
                break;
            }
        }
        return right;
    }


    private int judge(double ans, int[][] squares) {
        double sum1 = 0;
        double sum2 = 0;
        for (int[] square : squares) {
            double y1 = square[1];
            double y2 = square[1] + square[2];

            if (ans>=y2){
                sum1 += (square[2]*square[2]);
            }else if (ans <= y1) {
                sum2 += (square[2]*square[2]);
            } else {
                double len1 = ans - y1;
                double len2 = y2 - ans;
                sum1 += (len1 * square[2]);
                sum2 += (len2 * square[2]);
            }
        }
        // 误差在 10-5 以内
        if (sum1 >= sum2) {
            return 1;
        } else {
            return -1;
        }
    }

    public static void main(String[] args) {
        Solution3453 solution3453 = new Solution3453();
        int[][] squares = {{7,16,4}, {3,13,4}};
        double res = solution3453.separateSquares(squares);
        System.out.println("res=" + res);
    }
}
