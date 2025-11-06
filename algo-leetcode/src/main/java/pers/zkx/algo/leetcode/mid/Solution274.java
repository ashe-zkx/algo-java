package pers.zkx.algo.leetcode.mid;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * H指数
 *
 * @author: zhangkuixing
 * @date: 2025/7/16 23:41
 */
public class Solution274 {
    public int hIndex(int[] citations) {
        if (citations == null || citations.length == 0) {
            return 0;
        }
        Arrays.sort(citations);
        int ans = 0;
        int n = citations.length;
        for (int i = 0; i < n; i++) {
            // 计算当前的h指数
            int h = n - i;
            if (citations[i] >= h) {
                ans = h;
                break;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        // [1,3,1]
        int[] citations = {1, 3, 1};
        Solution274 solution274 = new Solution274();
        int hIndex = solution274.hIndex(citations);
        System.out.println("hIndex = " + hIndex);

        // [0]
        int[] citations2 = {0};
        int hIndex2 = solution274.hIndex(citations2);
        System.out.println("hIndex2 = " + hIndex2);

        // [100]
        int[] citations3 = {100};
        int hIndex3 = solution274.hIndex(citations3);
        System.out.println("hIndex3 = " + hIndex3);
    }
}
