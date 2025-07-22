package pers.zkx.algo.leetcode.simple;

/**
 * @author: zhangkuixing
 * @date: 2025/7/23 00:14
 */
public class Solution125 {
    public boolean isPalindrome(String s) {
        if (s == null || s.length() <= 1) {
            return true;
        }
        s = s.toLowerCase();

        int left = 0;
        int right = s.length() - 1;
        boolean flag = true;

        while (left < right) {
            while (left < right && isValidChar(s.charAt(left))) {
                left++;
            }
            while (left < right && isValidChar(s.charAt(right))) {
                right--;
            }

            if (left >= right) {
                break;
            } else {
                if (s.charAt(left) != s.charAt(right)) {
                    flag = false;
                    break;
                }
                left++;
                right--;
            }
        }
        return flag;
    }

    private boolean isValidChar(char c) {
        return (c < 'a' || c > 'z') && (c < '0' || c > '9');
    }


    public static void main(String[] args) {
        Solution125 solution125 = new Solution125();

        String s = "A man, a plan, a canal: Panama";
        boolean result = solution125.isPalindrome(s);
        System.out.println("Is palindrome: " + result);
    }
}
