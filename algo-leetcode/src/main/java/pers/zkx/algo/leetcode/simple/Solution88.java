package pers.zkx.algo.leetcode.simple;

/**
 * 合并两个有序数组
 *
 * @author: zhangkuixing
 * @date: 2025/7/14 23:19
 */
public class Solution88 {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int[] merged = new int[m + n];
        int i = 0, j = 0, k = 0;
        while (i < m && j < n) {

            while (i < m && nums1[i] <= nums2[j]) {
                merged[k++] = nums1[i++];
            }
            while (j < n && nums2[j] < nums1[i]) {
                merged[k++] = nums2[j++];
            }
        }
        while (i < m) {
            merged[k++] = nums1[i++];
        }
        while (j < n) {
            merged[k++] = nums2[j++];
        }
        for (int l = 0; l < merged.length; l++) {
            nums1[l] = merged[l];
        }
    }
}
