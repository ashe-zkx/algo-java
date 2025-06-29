package pers.zkx.algo.base.sorts;

import java.util.Arrays;
import java.util.List;

/**
 * 排序算法接口
 *
 * @author: zhangkuixing
 * @date: 2025/6/29 18:39
 */
public interface SortAlgoApi {
    /**
     * 排序方法
     *
     * @param unsorted 未排序的数组
     * @return 已排序的数组
     */
    <T extends Comparable<T>> T[] sort(T[] unsorted);

    /**
     * 排序方法，适用于列表
     *
     * @param unsorted 未排序的列表
     * @return 已排序的列表
     */
    @SuppressWarnings("unchecked")
    default <T extends Comparable<T>> List<T> sort(List<T> unsorted) {
        return Arrays.asList(sort(unsorted.toArray((T[]) new Comparable[unsorted.size()])));
    }
}
