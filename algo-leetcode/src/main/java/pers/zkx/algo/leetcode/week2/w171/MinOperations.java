/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2025 All Rights Reserved.
 */
package pers.zkx.algo.leetcode.week2.w171;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author kui yuan
 * @version $Id: MinOperations, v 0.1 2025-12-06 22:40 your_name Exp $
 */
public class MinOperations {
    public int[] minOperations(int[] nums) {
        if (nums == null || nums.length == 0) {
            return new int[0];
        }
        int n = nums.length;
        int[] result = new int[n];
        Map<Integer, Boolean> map = new HashMap<>();
        for (int i = 0; i < n; i++) {

            if (check(nums[i],map)){
                result[i]=0;
                map.put(nums[i],true);
                continue;
            }else{
                map.put(nums[i],false);
            }
            int count = 1;
            int num = nums[i];
            while (true) {
                if (check(num-count,map)) {
                    result[i]=count;
                    map.put(num-count,true);
                    break;
                }else{
                    map.put(num-count,false);
                }
                if (check(num+count,map)) {
                    result[i]=count;
                    map.put(num+count,true);
                    break;
                }else{
                    map.put(num+count,false);
                }
                count++;
            }
        }
        return result;
    }

    private boolean check(int num, Map<Integer, Boolean> map ){
        if (num==0 || num==1){
            return true;
        }
        if (map.containsKey(num)){
            return map.get(num);
        }
        StringBuilder stringBuilder = new StringBuilder();
        while (num>0){
            stringBuilder.append(num%2);
            num/=2;
        }
        String s = stringBuilder.toString();
        int left=0;
        int right=s.length()-1;
        while (left<right){
            if(s.charAt(left)!=s.charAt(right)){
                return false;
            }
            left++;
            right--;
        }
        return true;
    }



}
