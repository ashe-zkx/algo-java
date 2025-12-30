package pers.zkx.algo.leetcode.mid;

/**
 * 75. 颜色分类
 * @author: zhangkuixing
 * @date: 2025/11/21 00:57
 */
public class Solution75 {
    public void sortColors(int[] nums) {
        if(nums==null || nums.length<=1){
            return ;
        }
        int n = nums.length;
        int low =0;
        int mid =0;
        int high = n-1;
        while(mid<=high){
            if(nums[mid]==0){
                swap(nums,low,mid);
                low++;
                mid++;
            }else if(nums[mid]==1){
                mid++;
            }else{
                swap(nums,mid,high);
                high--;
            }
        }

    }
    private void swap(int[] nums,int l,int r){
        int tmp = nums[l];
        nums[l] = nums[r];
        nums[r]= tmp;
    }
}
