package pers.zkx.algo.base.utils;

/**
 * 生成随机数数组的工具类
 *
 * @author: zhangkuixing
 * @date: 2025/6/29 22:17
 */
public class ArrayRandomUtils {
    // 生成固定大小的随机数数组
    public static Integer[] generateRandomArray(int size) {
        return java.util.stream.IntStream.range(0, size)
                .mapToObj(i -> (int) (Math.random() * size)) // 生成0-size之间的随机整数
                .toArray(Integer[]::new);
    }

    // 生成指定范围的随机数数组
    public static Integer[] generateRandomArray(int size, int min, int max) {
        return java.util.stream.IntStream.range(0, size)
                .mapToObj(i -> (int) (Math.random() * (max - min + 1)) + min) // 生成min-max之间的随机整数
                .toArray(Integer[]::new);
    }

    // 生成指定范围的随机数数组，包含负数
    public static Integer[] generateRandomArrayWithNegative(int size, int min, int max) {
        return java.util.stream.IntStream.range(0, size)
                .mapToObj(i -> (int) (Math.random() * (max - min + 1)) + min) // 生成min-max之间的随机整数
                .toArray(Integer[]::new);
    }

    // 生成指定范围的随机数数组，包含负数和小数
    public static Double[] generateRandomDoubleArray(int size, double min, double max) {
        return java.util.stream.IntStream.range(0, size)
                .mapToObj(i -> Math.random() * (max - min) + min) // 生成min-max之间的随机小数
                .toArray(Double[]::new);
    }
}
