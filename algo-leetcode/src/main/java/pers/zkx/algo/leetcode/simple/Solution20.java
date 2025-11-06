package pers.zkx.algo.leetcode.simple;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

/**
 * @author: zhangkuixing
 * @date: 2025/8/11 00:36
 */
public class Solution20 {
    public boolean isValid(String s) {
        if (s == null || s.length() == 0) {
            return true;
        }

        Deque<Character> stack = new ArrayDeque<>();
        for (char c : s.toCharArray()) {
            if (c == '(' || c == '{' || c == '[') {
                stack.push(c);
            } else {
                if (stack.isEmpty()) {
                    return false;
                }
                // 括号不匹配
                char top = stack.pop();
                if ((c == ')' && top != '(') ||
                        (c == '}' && top != '{') ||
                        (c == ']' && top != '[')) {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }
}
