package pers.zkx.algo.leetcode.mid;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author: zhangkuixing
 * @date: 2025/8/23 02:05
 */
public class Solution155 {

    private LinkedList<Integer> stack = new LinkedList<>();
    private LinkedList<Integer> minStack = new LinkedList<>();

    public Solution155() {

    }

    public void push(int val) {
        stack.push(val);
        if (minStack.isEmpty()) {
            minStack.push(val);
        } else {
            minStack.push(Math.min(minStack.peek(), val));
        }
    }

    public void pop() {
        if (!stack.isEmpty()) {
            stack.pop();
            minStack.pop();
        }
    }

    public int top() {
        return stack.peek();
    }

    public int getMin() {
        return minStack.peek();
    }
}
