package pers.zkx.algo.leetcode.mid;

/**
 *
 * @author: zhangkuixing
 * @date: 2025/12/25 00:06
 */
public class Solution8 {
    public int myAtoi(String s) {
        boolean isNegative = false;
        int index = 0;
        // 去除前导空格
        while(index < s.length() && s.charAt(index) == ' '){
            index++;
        }
        // 处理符号
        if(index < s.length()) {
            if (s.charAt(index) == '-') {
                isNegative = true;
                index++;
            } else if (s.charAt(index) == '+') {
                index++;
            }
        }
        long result = 0;
        while(index < s.length() && Character.isDigit(s.charAt(index))) {
            int digit = s.charAt(index) - '0';
            result = result * 10 + digit;
            // 检查溢出
            if (!isNegative && result > Integer.MAX_VALUE) {
                return Integer.MAX_VALUE;
            } else if (isNegative && -result < Integer.MIN_VALUE) {
                return Integer.MIN_VALUE;
            }
            index++;
        }
        return isNegative ? (int)(-result) : (int)result;
    }
}
