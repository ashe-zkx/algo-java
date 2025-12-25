/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2025 All Rights Reserved.
 */
package pers.zkx.algo.leetcode.week2.w172;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author kui yuan
 * @version $Id: MinOperations, v 0.1 2025-12-20 22:31 your_name Exp $
 */
public class MinOperations {
    public int minOperations(int[] nums) {
        int n = nums.length;
        Map<Integer, Integer> map = new HashMap<>();
        int sameCount = 0;
        for (int num : nums) {
            if (map.containsKey(num)) {
                if (map.get(num) == 1) {
                    sameCount++;
                }
                map.put(num, map.get(num) + 1);
            }else{
                map.put(num, 1);
            }
        }
        if(sameCount ==0){
            return 0;
        }
        int operations = 0;
        int index = 0;
        int limit;
        while (sameCount > 0){
            limit = 3;
            while (limit > 0 && index < n){
                int num = nums[index];
                limit--;
                if (map.get(num) > 1){
                    map.put(num, map.get(num) -1);
                    if (map.get(num) == 1){
                        sameCount--;
                    }
                }
                index++;
            }
            operations++;
        }
        return operations;
    }

}
