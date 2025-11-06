package pers.zkx.algo.leetcode.mid;

import java.util.LinkedList;

/**
 * @author: zhangkuixing
 * @date: 2025/8/23 02:22
 */
public class Solution150 {
    public int evalRPN(String[] tokens) {
        if (tokens == null || tokens.length == 0) {
            return 0;
        }
        LinkedList<Integer> stack = new LinkedList<>();
        for (String token : tokens) {
            if (token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/")) {
                int num1 = stack.pop();
                int num2 = stack.pop();

                switch (token) {
                    case "+":
                        stack.push(num2 + num1);
                        break;
                    case "-":
                        stack.push(num2 - num1);
                        break;
                    case "*":
                        stack.push(num2 * num1);
                        break;
                    case "/":
                        // 注意：这里需要向下取整
                        stack.push(num2 / num1);
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown operator: " + token);
                }
            } else {
                stack.push(Integer.valueOf(token));
            }
        }
        return stack.pop();
    }
}
