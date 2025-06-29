package pers.zkx.algo.base.sorts;

/**
 * @author: zhangkuixing
 * @date: 2025/6/29 18:56
 */
public class QuickSortTest {

    public static void main(String[] args) {
        SortAlgoApi quickSort = new QuickSort();
        Integer[] unsortedArray = {3, 6, 8, 10, 1, 2, 1};
        Integer[] sortedArray = quickSort.sort(unsortedArray);

        System.out.println("Sorted Array: ");
        for (Integer num : sortedArray) {
            System.out.print(num + " ");
        }
    }
}
