package pers.zkx.algo.leetcode.mid;

import java.util.Stack;

/**
 * Solution394
 *
 * @author zhangkuixing
 * @since 2026-01-25
 */
public class Solution394 {

    public String decodeString(String s) {

        Stack<Integer> numStack = new Stack<>();
        Stack<String> strStack = new Stack<>();
        StringBuilder ans = new StringBuilder();

        int number = 0;
        int index = 0;
        int size = s.length();
        for (;index < size;index++) {
            char c = s.charAt(index);
            if (Character.isDigit(c)) {
                number = number * 10 + c - '0';
            } else if (c == '[') {
                numStack.push(number);
                number = 0;
                index++;
                StringBuilder tmp = new StringBuilder();
                for (; index < size; index++) {
                    char ch = s.charAt(index);
                    if (ch == ']' || Character.isDigit(ch)) {
                        break;
                    } else {
                        tmp.append(ch);
                    }
                }
                strStack.push(tmp.toString());
                index--;
            } else if (c == ']') {
                int count = numStack.pop();
                String str = strStack.pop();
                StringBuilder decoded = new StringBuilder();
                decoded.append(String.valueOf(str).repeat(Math.max(0, count)));
                if (!strStack.isEmpty()) {
                    String prev = strStack.pop();
                    prev += decoded.toString();
                    strStack.push(prev);
                } else {
                    ans.append(decoded);
                }
            } else {
                if (!strStack.isEmpty()) {
                    String prev = strStack.pop();
                    prev += c;
                    strStack.push(prev);
                }else{
                    ans.append(c);
                }
            }
        }
        return ans.toString();
    }

    public static void main(String[] args) {

        Solution394 solution394 = new Solution394();
        String s = "3[z]2[2[y]pq4[2[jk]e1[f]]]ef";
        String res = solution394.decodeString(s);
        System.out.println(res);
    }

}
