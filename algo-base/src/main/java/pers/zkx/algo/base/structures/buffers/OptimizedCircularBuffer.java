package pers.zkx.algo.base.structures.buffers;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 安全的原子指针类，防止整数溢出
 */
class SafeAtomicPointer {
    private final AtomicInteger value = new AtomicInteger(0);
    private static final int RESET_THRESHOLD = Integer.MAX_VALUE - 1000000; // 预留安全边距

    /**
     * 原子地增加并返回之前的值
     */
    public int getAndIncrement() {
        int current;
        int next;
        do {
            current = value.get();
            // 如果接近溢出，重置为0
            next = (current >= RESET_THRESHOLD) ? 0 : current + 1;
        } while (!value.compareAndSet(current, next));
        return current;
    }

    /**
     * 获取当前值
     */
    public int get() {
        return value.get();
    }

    /**
     * 比较并设置值
     */
    public boolean compareAndSet(int expect, int update) {
        // 处理溢出情况下的CAS操作
        if (update >= RESET_THRESHOLD) {
            update = update % RESET_THRESHOLD;
        }
        return value.compareAndSet(expect, update);
    }

    /**
     * 安全地计算两个指针之间的差值
     */
    public static int safeDifference(int put, int get) {
        // 处理溢出重置的情况
        if (put < get) {
            // put指针已重置，get指针还未重置
            return put + (RESET_THRESHOLD - get);
        }
        return put - get;
    }
}

/**
 * 优化的环形缓冲区实现。
 * 环形缓冲区是一种固定大小的数据结构，采用先进先出（FIFO）方式操作。当缓冲区满时，新数据会覆盖最旧的数据。
 * 此实现是线程安全的，并使用无锁算法以获得高性能。
 *
 * @author: zhangkuixing
 * @date: 2025/7/1 00:00
 */
public class OptimizedCircularBuffer<T> {
    private final T[] buffer;
    private final int capacity;
    private final SafeAtomicPointer putPointer = new SafeAtomicPointer();
    private final SafeAtomicPointer getPointer = new SafeAtomicPointer();

    /**
     * 构造函数，初始化环形缓冲区。
     *
     * @param capacity 缓冲区大小，必须大于0。
     * @throws IllegalArgumentException 如果大小小于或等于0。
     */
    @SuppressWarnings("unchecked")
    public OptimizedCircularBuffer(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Buffer capacity must be positive");
        }
        this.capacity = capacity;
        this.buffer = (T[]) new Object[capacity];
    }

    /**
     * 向缓冲区中放入一个元素。
     * 如果缓冲区已满，则会覆盖最旧的元素。
     *
     * @param item 要放入的元素，不能为 null
     * @return 总是返回 true
     * @throws IllegalArgumentException 如果放入的元素为 null
     */
    public boolean put(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }

        int currentPut = putPointer.getAndIncrement();
        int index = Math.abs(currentPut % capacity);
        buffer[index] = item;

        // 处理覆盖逻辑
        int currentGet = getPointer.get();
        int diff = SafeAtomicPointer.safeDifference(currentPut, currentGet);
        if (diff >= capacity) {
            // 使用CAS确保只有领先的线程才能移动getPointer
            getPointer.compareAndSet(currentGet, currentGet + 1);
        }

        return true;
    }

    /**
     * 从缓冲区获取一个元素。
     * 如果缓冲区为空，则返回 null。
     *
     * @return 缓冲区头部的元素，如果为空则返回 null
     */
    public T get() {
        int currentGet;
        T item;

        do {
            currentGet = getPointer.get();
            int currentPut = putPointer.get();

            if (currentGet == currentPut) {
                // 缓冲区为空
                return null;
            }

            int index = Math.abs(currentGet % capacity);
            item = buffer[index];

            // 尝试原子地移动getPointer。
            // 如果CAS成功，说明我们成功地消费了一个元素。
            // 如果失败，意味着另一个get()或put()（在覆盖时）移动了getPointer，
            // 我们需要重试整个操作。
        } while (!getPointer.compareAndSet(currentGet, currentGet + 1));

        return item;
    }

    /**
     * 检查缓冲区是否为空。
     *
     * @return 如果缓冲区为空，返回 {@code true}；否则返回 {@code false}。
     */
    public boolean isEmpty() {
        return getPointer.get() == putPointer.get();
    }

    /**
     * 检查缓冲区是否已满。
     *
     * @return 如果缓冲区已满，返回 {@code true}；否则返回 {@code false}。
     */
    public boolean isFull() {
        return size() == capacity;
    }

    /**
     * 返回缓冲区中的元素数量。
     * 注意：在并发环境下，这个值可能不是精确的，因为它是由两个独立的原子变量计算得出的。
     *
     * @return 缓冲区中的元素数量
     */
    public int size() {
        int put = putPointer.get();
        int get = getPointer.get();
        return SafeAtomicPointer.safeDifference(put, get);
    }

    /**
     * 返回缓冲区的容量。
     *
     * @return 缓冲区的容量
     */
    public int capacity() {
        return capacity;
    }
}
