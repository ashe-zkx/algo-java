package pers.zkx.algo.leetcode.week.week466;

/**
 * Q2 转换字符串的最小操作数
 *
 * @author: zhangkuixing
 * @date: 2025/9/7 10:38
 */
public class Solution2 {
    public int minOperations(String s) {
        int[] chars = new int[26];
        for (int i = 0; i < s.length(); i++) {
            chars[s.charAt(i) - 'a']++;
        }
        int ans = 0;
        boolean flag;
        for (int i = 1; i < 26; i++) {
            if (chars[i] != 0) {
                flag = false;
                // 找到下一个不为0的
                for (int j = i + 1; j < 26; j++) {
                    if (chars[j] != 0) {
                        flag = true;
                        ans += (j - i);
                        break;
                    }
                }
                if (!flag) {
                    ans = ans + (26 - i);
                }
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        Solution2 s = new Solution2();
        System.out.println(s.minOperations("yz"));
    }
}
