package pers.zkx.algo.leetcode.hard;

import java.util.ArrayList;
import java.util.List;

/**
 * 文本左右对齐
 *
 * @author: zhangkuixing
 * @date: 2025/7/22 23:42
 */
public class Solution68 {
    public List<String> fullJustify(String[] words, int maxWidth) {
        List<String> ans = new ArrayList<>();

        if (words == null || words.length == 0) {
            return ans;
        }
        int n = words.length;
        List<String> mid = new ArrayList<>();
        int currentWidth = 0;
        for (int i = 0; i < n; i++) {
            String word = words[i];
            if (currentWidth + word.length() > maxWidth) {
                // 当前行已满，进行处理
                ans.add(format(mid, maxWidth));
                mid.clear();
                currentWidth = 0;
            }
            mid.add(word);
            // +1 是为了空格
            currentWidth += word.length() + 1;
        }
        // 处理最后一行
        if (!mid.isEmpty()) {
            ans.add(format(mid, maxWidth));
        }
        // 最后一行需要左对齐
        if (!ans.isEmpty()) {
            String last = ans.getLast();
            String[] s = last.split(" ");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < s.length; i++) {
                if (s[i].isEmpty()) {
                    continue; // 跳过空字符串
                }
                sb.append(s[i]);
                if (i < s.length - 1) {
                    sb.append(" ");
                }
            }
            // 添加空格到最后一行
            addSpace(sb, maxWidth - sb.length());
            ans.set(ans.size() - 1, sb.toString());
        }

        return ans;
    }

    private String format(List<String> mid, int maxWidth) {
        StringBuilder sb = new StringBuilder();
        if (mid == null || mid.isEmpty()) {
            return addSpace(sb, maxWidth).toString();
        }
        if (mid.size() == 1) {
            sb.append(mid.getFirst());
            addSpace(sb, maxWidth - mid.getFirst().length());
        } else {
            int sumLength = 0;
            for (String word : mid) {
                sumLength += word.length();
            }
            int avgSpace = (maxWidth - sumLength) / (mid.size() - 1);
            int extraSpace = (maxWidth - sumLength) % (mid.size() - 1);
            for (int i = 0; i < mid.size(); i++) {
                sb.append(mid.get(i));
                if (i < mid.size() - 1) {
                    // 添加平均空格
                    addSpace(sb, avgSpace);
                    // 如果有多余的空格，分配给前面的单词
                    if (extraSpace > 0) {
                        sb.append(" ");
                        extraSpace--;
                    }
                }
            }
        }
        return sb.toString();
    }

    private StringBuilder addSpace(StringBuilder stringBuilder, int num) {
        if (num <= 0) {
            return stringBuilder;
        }
        return stringBuilder.append(" ".repeat(num));
    }

    public static void main(String[] args) {
        Solution68 solution = new Solution68();
        String[] words = {"What", "must", "be", "acknowledgment", "shall", "be"};
        int maxWidth = 16;
        List<String> result = solution.fullJustify(words, maxWidth);
        for (String line : result) {
            System.out.println("\"" + line + "\"");
        }
    }
}
