package pers.zkx.algo.base.string;

import java.util.Arrays;

/**
 * @author: zhangkuixing
 * @date: 2025/8/11 00:45
 */
public class KMP {
    public static void main(String[] args) {
        final String haystack = "AAAAABAAABA"; // This is the full string
        final String needle = "AAAA"; // This is the substring that we want to find
        kmpMatcher(haystack, needle);
    }

    public static void kmpMatcher(final String haystack, final String needle) {
        final int n = haystack.length();
        final int m = needle.length();

        final int[] pi = computePrefixFunction(needle);
        System.out.println("Prefix function: " + Arrays.toString(pi));
        int q = 0;
        for (int i = 0; i < n; i++) {
            while (q > 0 && haystack.charAt(i) != needle.charAt(q)) {
                q = pi[q - 1];
            }
            if (haystack.charAt(i) == needle.charAt(q)) {
                q++;
            }
            // 如果匹配成功，输出匹配位置
            if (q == m) {
                System.out.println("Pattern starts: " + (i + 1 - m));
                q = pi[q - 1];
            }
        }
    }

    private static int[] computePrefixFunction(final String p) {
        final int n = p.length();
        final int[] pi = new int[n];
        pi[0] = 0;
        int q = 0;
        for (int i = 1; i < n; i++) {
            // 未匹配成功，回溯
            while (q > 0 && p.charAt(q) != p.charAt(i)) {
                q = pi[q - 1];
            }
            if (p.charAt(q) == p.charAt(i)) {
                q++;
            }
            pi[i] = q;
        }
        return pi;
    }
}
