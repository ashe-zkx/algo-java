package pers.zkx.algo.leetcode.mid;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * Solution3650
 *
 * @author zhangkuixing
 * @since 2026-01-27
 */
public class Solution3650 {
    public int minCost(int n, int[][] edges) {
        Map<Integer,Map<Integer,Integer>> weightMap = new java.util.HashMap<>();
        for(int[] edge:edges){
            int u = edge[0];
            int v = edge[1];
            int w = edge[2];
            weightMap.putIfAbsent(u,new java.util.HashMap<>());
            Map<Integer, Integer> integerIntegerMap = weightMap.get(u);
            integerIntegerMap.put(v, Math.min(integerIntegerMap.getOrDefault(v, Integer.MAX_VALUE), w));

            weightMap.putIfAbsent(v,new java.util.HashMap<>());
            Map<Integer, Integer> integerIntegerMap2 = weightMap.get(v);
            integerIntegerMap2.put(u, Math.min(integerIntegerMap2.getOrDefault(u, Integer.MAX_VALUE), w*2));

        }
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[0] = 0;
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);
        while(!queue.isEmpty()){
            int u = queue.poll();
            Map<Integer, Integer> weights = weightMap.get(u);
            if(weights==null){
                continue;
            }
            for(Map.Entry<Integer,Integer> entry:weights.entrySet()){
                int v = entry.getKey();
                int w = entry.getValue();
                if(dist[u]+w<dist[v]){
                    dist[v] = dist[u]+w;
                    queue.offer(v);
                }
            }
        }
        return dist[n-1]==Integer.MAX_VALUE?-1:dist[n-1];
    }

}
