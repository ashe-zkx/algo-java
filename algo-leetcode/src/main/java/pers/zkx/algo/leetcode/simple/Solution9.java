package pers.zkx.algo.leetcode.simple;

/**
 * 回文数
 *
 * @author: zhangkuixing
 * @date: 2025/7/8 00:29
 */
public class Solution9 {

    public boolean isPalindrome(int x) {
        if (x < 0) {
            return false;
        }
        if (x == 0) {
            return true;
        }
        int original = x;
        int reversed = 0;
        while (x != 0) {
            int tmp = x % 10;
            reversed = reversed * 10 + tmp;
            x /= 10;
        }
        return reversed == original;
    }


}
