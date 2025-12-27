package pers.zkx.algo.leetcode.simple;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: zhangkuixing
 * @date: 2025/12/18 23:35
 */
public class Solution2824 {
    public int countPairs(List<Integer> nums, int target) {
        int ans = 0;
        nums.sort(Integer::compareTo);

        int n = nums.size();
        for(int i=0;i<n;i++){
            int complement = target - nums.get(i);

            if(complement < nums.get(i)){
                continue;
            }
            int num = find(i + 1, n - 1, nums, complement);
            ans += num;
        }
        return ans;
    }


    public int find(int l,int r , List<Integer> nums, int target){
        int ans = 0;
        if(nums.get(r)<target){
            return r - l + 1;
        }
        int oriLeft = l;
        // 找比 target 小的数量
        while(l<=r){
            int mid = l + (r - l)/2;
            if(nums.get(mid)<target){
                ans = mid - oriLeft + 1;
                l = mid + 1;
            }else{
                r = mid - 1;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        Solution2824 solution2824 = new Solution2824();
        List<Integer> nums = new ArrayList<>(List.of(-1,1,2,3,1));
        System.out.println(solution2824.countPairs(nums, 2));
    }
}
