package pers.zkx.algo.base.structures.bloomfilter;

import java.util.BitSet;

/**
 * 布隆过滤器的实现
 * <p>
 * 布隆过滤器是一种空间效率高的概率型数据结构，用于测试一个元素是否在一个集合中。
 * 它可能会误判一个元素在集合中（即产生假阳性），但不会漏掉一个实际存在的元素（即不会产生假阴性）。
 * </p>
 *
 * @author: zhangkuixing
 * @date: 2025/6/29 22:33
 */
public class BloomFilter<T> {

    private final int numberOfHashFunctions;
    private final BitSet bitArray;
    private final Hash<T>[] hashFunctions;


    /**
     * 构造一个布隆过滤器，指定哈希函数的数量和位数组的大小。
     *
     * @param numberOfHashFunctions 哈希函数的数量
     * @param bitArraySize          位数组的大小，决定了过滤器的容量
     * @throws IllegalArgumentException 如果哈希函数数量或位数组大小小于1
     */
    public BloomFilter(int numberOfHashFunctions, int bitArraySize) {
        if (numberOfHashFunctions < 1 || bitArraySize < 1) {
            throw new IllegalArgumentException("Number of hash functions and bit array size must be greater than 0");
        }
        this.numberOfHashFunctions = numberOfHashFunctions;
        this.bitArray = new BitSet(bitArraySize);
        this.hashFunctions = new Hash[numberOfHashFunctions];
        initializeHashFunctions();
    }

    /**
     * 初始化哈希函数，确保每个函数具有唯一的索引，以实现不同的哈希。
     */
    private void initializeHashFunctions() {
        for (int i = 0; i < numberOfHashFunctions; i++) {
            hashFunctions[i] = new Hash<>(i);
        }
    }

    /**
     * 将元素插入到布隆过滤器中。
     * <p>
     * 此方法使用所有定义的哈希函数对元素进行哈希，并设置位数组中相应的位置。
     * </p>
     *
     * @param key 要插入到布隆过滤器中的元素
     */
    public void insert(T key) {

        for (Hash<T> hash : hashFunctions) {
            int position = Math.abs(hash.compute(key) % bitArray.size());
            bitArray.set(position);
        }
    }

    public static int calculateBitArraySize(int expectedElements, double falsePositiveRate) {
        if (expectedElements <= 0 || falsePositiveRate <= 0 || falsePositiveRate >= 1) {
            throw new IllegalArgumentException("Expected elements must be greater than 0 and false positive rate must be between 0 and 1.");
        }
        return (int) Math.ceil(-expectedElements * Math.log(falsePositiveRate) / (Math.log(2) * Math.log(2)));
    }

    /**
     * 检查一个元素是否可能在布隆过滤器中。
     * <p>
     * 此方法检查每个哈希函数计算的位置的位。如果任何一个位置的位未设置，则该元素肯定不在过滤器中。
     * 如果所有位置的位都设置了，则该元素可能在过滤器中。
     * </p>
     *
     * @param key 要检查是否在布隆过滤器中的元素
     * @return 如果元素可能在过滤器中，则返回true；否则返回false
     */
    public boolean mightContain(T key) {
        for (Hash<T> hash : hashFunctions) {
            int position = Math.abs(hash.compute(key) % bitArray.size());
            if (!bitArray.get(position)) {
                return false;
            }
        }
        return true;
    }

    private record Hash<T>(int index) {

        /**
         * 计算哈希值
         *
         * @param key 要计算哈希值的元素
         * @return 哈希值
         */
        public int compute(T key) {
            return index * asciiString(String.valueOf(key));
        }

        /**
         * 计算字符串的ASCII值之和
         *
         * @param word 要计算ASCII值的字符串
         * @return 字符串中所有字符的ASCII值之和
         */
        private int asciiString(String word) {
            int sum = 0;
            for (char c : word.toCharArray()) {
                sum += c;
            }
            return sum;
        }
    }
}
