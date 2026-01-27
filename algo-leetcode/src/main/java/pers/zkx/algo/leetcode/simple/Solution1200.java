package pers.zkx.algo.leetcode.simple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Solution1200
 *
 * @author zhangkuixing
 * @since 2026-01-26
 */
public class Solution1200 {
    public List<List<Integer>> minimumAbsDifference(int[] arr) {
        if (arr==null || arr.length==0){
            return new ArrayList<>();
        }
        Arrays.sort(arr);
        List<List<Integer>> ans = new ArrayList<>();
        int min = Integer.MAX_VALUE;
        for(int i=1;i<arr.length;i++){
            int diff = Math.abs(arr[i]-arr[i-1]);
            if(diff<min){
                min = diff;
                ans.clear();
                List<Integer> pair = new ArrayList<>();
                pair.add(arr[i-1]);
                pair.add(arr[i]);
                ans.add(pair);
            }else if(diff==min){
                List<Integer> pair = new ArrayList<>();
                pair.add(arr[i-1]);
                pair.add(arr[i]);
                ans.add(pair);
            }
        }
        return ans;
    }
}
