package pers.zkx.algo.base.utils;

import java.util.List;

/**
 * 生成随机数列表的工具类
 *
 * @author: zhangkuixing
 * @date: 2025/6/29 22:15
 */
public class ListRandomUtils {
    // 生成固定大小的随机数列表
    public List<Integer> generateRandomList(int size) {
        return java.util.stream.IntStream.range(0, size)
                .mapToObj(i -> (int) (Math.random() * size)) // 生成0-size之间的随机整数
                .toList();
    }

    // 生成指定范围的随机数列表
    public List<Integer> generateRandomList(int size, int min, int max) {
        return java.util.stream.IntStream.range(0, size)
                .mapToObj(i -> (int) (Math.random() * (max - min + 1)) + min) // 生成min-max之间的随机整数
                .toList();
    }

    // 生成指定范围的随机数列表，包含负数
    public List<Integer> generateRandomListWithNegative(int size, int min, int max) {
        return java.util.stream.IntStream.range(0, size)
                .mapToObj(i -> (int) (Math.random() * (max - min + 1)) + min) // 生成min-max之间的随机整数
                .toList();
    }

    // 生成指定范围的随机数列表，包含负数和小数
    public List<Double> generateRandomDoubleList(int size, double min, double max) {
        return java.util.stream.IntStream.range(0, size)
                .mapToObj(i -> Math.random() * (max - min) + min) // 生成min-max之间的随机小数
                .toList();
    }
}
