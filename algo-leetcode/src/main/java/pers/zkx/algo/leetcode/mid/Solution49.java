package pers.zkx.algo.leetcode.mid;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 49. 字母异位词分组
 *
 * @author: zhangkuixing
 * @date: 2025/7/25 23:53
 */
public class Solution49 {
    public List<List<String>> groupAnagrams(String[] strs) {
        if (strs == null || strs.length == 0) {
            return List.of();
        }
        Map<String, List<String>> map = new HashMap<>();
        for (String str : strs) {
            char[] charArray = str.toCharArray();
            Arrays.sort(charArray);
            String key = new String(charArray);
            map.putIfAbsent(key, new java.util.ArrayList<>());
            map.get(key).add(str);
        }
        return new java.util.ArrayList<>(map.values());
    }
}
