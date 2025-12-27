package pers.zkx.algo.leetcode.mid;

/**
 * 31. 下一个排列
 * @author: zhangkuixing
 * @date: 2025/11/21 00:24
 */
public class Solution31 {
    public void nextPermutation(int[] nums) {
        if(nums==null || nums.length<=1){
            return ;
        }
        int n = nums.length;
        int index = n-2;
        while(index>=0&&nums[index]>=nums[index+1]){
            index--;
        }
        if(index>=0){
            int j = n-1;
            while(j>=0 && nums[j] <= nums[index]){
                j--;
            }
            swap(nums,index,j);
        }
        reverse(nums,index+1,n-1);
    }

    private void swap(int[] nums,int l,int r){
        int tmp = nums[l];
        nums[l] = nums[r];
        nums[r]= tmp;
    }

    private void reverse(int[] nums,int l ,int r){
        while(l<=r){
            swap(nums,l,r);
            l++;
            r--;
        }
    }

    public static void main(String[] args) {
        Solution31 solution31 = new Solution31();
        int[] nums = {3,2,1};
        solution31.nextPermutation(nums);
        for(int num:nums){
            System.out.print(num+" ");
        }
    }
}
