/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2025 All Rights Reserved.
 */
package pers.zkx.algo.leetcode.week2.w171;

/**
 *
 * @author kui yuan
 * @version $Id: MinInversionCount, v 0.1 2025-12-06 23:11 your_name Exp $
 */
public class MinInversionCount {
    public long minInversionCount(int[] nums, int k) {
        int limit = nums.length - k;
        long ans = Long.MAX_VALUE;
        for (int i=0;i<=limit;i++){
            // 获取长度为 k 的子数组
            int[] subArray = new int[k];
            System.arraycopy(nums, i, subArray, 0, k);
            ans = Math.min(ans, countInversions(subArray,ans));

        }
        return ans;
    }

    private long countInversions(int[] arr,long ans) {
        if (arr == null || arr.length <=1) {
            return 0;
        }
        return mergeSortAndCount(arr, 0, arr.length - 1,ans);
    }
    private long mergeSortAndCount(int[] arr, int left, int right,long ans) {
        long count = 0;
        if (left < right) {
            int mid = (left + right) / 2;
            count += mergeSortAndCount(arr, left, mid,ans);
            if (count>ans){
                return count;
            }
            count += mergeSortAndCount(arr, mid + 1, right,ans);
            if (count>ans){
                return count;
            }
            count += mergeAndCount(arr, left, mid, right,ans);
            if (count>ans){
                return count;
            }
        }
        return count;
    }

    private  long mergeAndCount(int[] arr, int left, int mid, int right, long ans) {
        int leftLimit = mid - left + 1;
        int rightLimit = right - mid;

        int[] leftArr = new int[leftLimit];
        int[] rightArr = new int[rightLimit];
        System.arraycopy(arr, left, leftArr, 0, leftLimit);
        System.arraycopy(arr, mid + 1, rightArr, 0, rightLimit);

        int leftIndex = 0, rightIndex = 0;
        int arrIndex = left;
        long swaps = 0;
        while (leftIndex < leftLimit && rightIndex < rightLimit) {
            if (leftArr[leftIndex] <= rightArr[rightIndex]) {
                arr[arrIndex++] = leftArr[leftIndex++];
            } else {
                arr[arrIndex++] = rightArr[rightIndex++];
                swaps += (leftLimit - leftIndex);
                if (swaps >= ans) {
                    return swaps;
                }
            }
        }
        while (leftIndex < leftLimit) {
            arr[arrIndex++] = leftArr[leftIndex++];
        }
        while (rightIndex < rightLimit) {
            arr[arrIndex++] = rightArr[rightIndex++];
        }
        return swaps;
    }
}
