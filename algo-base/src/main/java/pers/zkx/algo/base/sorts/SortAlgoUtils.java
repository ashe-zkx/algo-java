package pers.zkx.algo.base.sorts;

/**
 * 排序算法工具类
 *
 * @author: zhangkuixing
 * @date: 2025/6/29 18:52
 */
public class SortAlgoUtils {

    /**
     * 交换数组中的两个元素
     *
     * @param array 数组
     * @param i     第一个元素的索引
     * @param j     第二个元素的索引
     * @param <T>   元素类型
     */
    public static <T> void swap(T[] array, int i, int j) {
        if (i != j) {
            T temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }


    /**
     * 判断两个元素的大小关系
     *
     * @param firstElement  第一个元素
     * @param secondElement 第二个元素
     * @param <T>           元素类型，必须实现Comparable接口
     * @return 如果第一个元素小于第二个元素，则返回true，否则返回false
     */
    public static <T extends Comparable<T>> boolean less(T firstElement, T secondElement) {
        return firstElement.compareTo(secondElement) < 0;
    }

    /**
     * 判断两个元素的大小关系
     *
     * @param firstElement  第一个元素
     * @param secondElement 第二个元素
     * @param <T>           元素类型，必须实现Comparable接口
     * @return 如果第一个元素大于第二个元素，则返回true，否则返回false
     */
    public static <T extends Comparable<T>> boolean greater(T firstElement, T secondElement) {
        return firstElement.compareTo(secondElement) > 0;
    }
}
