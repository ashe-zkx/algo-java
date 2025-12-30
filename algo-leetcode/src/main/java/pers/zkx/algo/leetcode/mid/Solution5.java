package pers.zkx.algo.leetcode.mid;

/**
 * 5. 最长回文子串
 * @author: zhangkuixing
 * @date: 2025/12/22 23:25
 */
public class Solution5 {

    public String longestPalindrome(String s) {
        String res = "";
        for(int i=0;i<s.length();i++){
            String str1 = palindrome(s, i, i);
            String str2 = palindrome(s, i, i + 1);
            String longerStr = str1.length() > str2.length() ? str1 : str2;
            if(longerStr.length() > res.length()){
                res = longerStr;
            }
        }
        return res;
    }

    private String palindrome(String s, int left, int right){
        while(left>=0 && right<s.length() && s.charAt(left) == s.charAt(right)){
            left--;
            right++;
        }
        return s.substring(left + 1, right);
    }


}
