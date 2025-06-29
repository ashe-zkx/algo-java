package pers.zkx.algo.base.sorts;

/**
 * 快排
 *
 * @author: zhangkuixing
 * @date: 2025/6/29 18:38
 */
public class QuickSort implements SortAlgoApi {
    @Override
    public <T extends Comparable<T>> T[] sort(T[] unsorted) {
        // 边界判断
        if (unsorted == null || unsorted.length == 0) {
            return unsorted;
        }
        if (unsorted.length == 1) {
            return unsorted;
        }
        doSort(unsorted, 0, unsorted.length - 1);
        return unsorted;
    }

    private <T extends Comparable<T>> void doSort(T[] array, int left, int right) {
        if (left >= right) {
            return;
        }
        int pivotIndex = partition(array, left, right);
        doSort(array, left, pivotIndex - 1);
        doSort(array, pivotIndex, right);
    }


    private <T extends Comparable<T>> int partition(T[] array, int left, int right) {
        int mid = (left + right) >>> 1;
        T pivot = array[mid];

        while (left <= right) {
            while (SortAlgoUtils.less(array[left], pivot)) {
                left++;
            }
            while (SortAlgoUtils.greater(array[right], pivot)) {
                right--;
            }
            if (left <= right) {
                SortAlgoUtils.swap(array, left, right);
                left++;
                right--;
            }
        }
        return left;
    }
}
