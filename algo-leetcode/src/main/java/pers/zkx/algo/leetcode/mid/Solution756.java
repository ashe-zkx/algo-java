package pers.zkx.algo.leetcode.mid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Solution756 - 金字塔转换矩阵
 *
 * @author zhangkuixing
 * @since 2025-12-29
 */
public class Solution756 {

    private Map<Character,Map<Character, Set<Character>>> map = new HashMap<Character,Map<Character,Set<Character>>>();
    private Map<String, Boolean> memo = new HashMap<>();
    public boolean pyramidTransition(String bottom, List<String> allowed) {
        if (bottom == null || bottom.isEmpty() || allowed == null || allowed.isEmpty()) {
            return false;
        }
        map.clear();
        memo.clear();

        for (String str : allowed) {
            char c1 = str.charAt(0);
            char c2 = str.charAt(1);
            char c3 = str.charAt(2);
            map.putIfAbsent(c1, new HashMap<>());
            map.get(c1).putIfAbsent(c2, new java.util.HashSet<>());
            map.get(c1).get(c2).add(c3);
        }
        return dfs(bottom, new StringBuilder());
    }


    private boolean dfs(String s,StringBuilder sb) {
        if (s.length() == 1) return true;
        if (sb.length() + 1 == s.length()) {
            return dfs(sb.toString(), new StringBuilder());
        }

        String key = s+"."+sb.toString();
        if (memo.containsKey(key)) {
            return memo.get(key);
        }

        int index = sb.length();
        char c1 = s.charAt(index);
        char c2 = s.charAt(index + 1);
        if (!map.containsKey(c1) || !map.get(c1).containsKey(c2)) {
            memo.put(key, false);
            return false;
        }
        for (char c3 : map.get(c1).get(c2)) {
            sb.append(c3);
            if (dfs(s, sb)) {
                memo.put(key, true);
                sb.deleteCharAt(sb.length() - 1);
                return true;
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        memo.put(key, false);
        return false;
    }

    public static void main(String[] args) {
        Solution756 solution756 = new Solution756();
        System.out.println(solution756.pyramidTransition("BCD", java.util.Arrays.asList("BCC","CDE","CEA","FFF")));
    }
}
