package pers.zkx.algo.leetcode.simple;

import java.util.ArrayList;
import java.util.List;

/**
 * Solution1356
 *
 * @author zhangkuixing
 * @since 2026-02-25
 */
public class Solution1356 {
    public int[] sortByBits(int[] arr) {
        List<Node> nodes = new ArrayList<>(arr.length);
        for (int value : arr) {
            nodes.add(new Node(value));
        }
        nodes.sort((a, b) -> {
            if (a.bitCount != b.bitCount) {
                return a.bitCount - b.bitCount;
            } else {
                return a.value - b.value;
            }
        });
        int[] result = new int[arr.length];
        for (int i = 0; i < nodes.size(); i++) {
            result[i] = nodes.get(i).value;
        }
        return result;
    }

    class Node {
        int value;
        int bitCount;
        public Node(int value) {
            this.value = value;
            this.bitCount = Integer.bitCount(value);
        }
    }
}
