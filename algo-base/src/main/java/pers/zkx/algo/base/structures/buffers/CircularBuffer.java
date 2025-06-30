package pers.zkx.algo.base.structures.buffers;

import java.awt.event.ItemEvent;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 环形缓冲区实现。
 * 环形缓冲区是一种固定大小的数据结构，采用先进先出（FIFO）方式操作。当缓冲区满时，覆盖旧数据。
 *
 * @author: zhangkuixing
 * @date: 2025/6/30 23:54
 */
public class CircularBuffer<T> {
    private final T[] buffer;
    private final CircularPointer putPointer;
    private final CircularPointer getPointer;
    private final AtomicInteger size = new AtomicInteger(0);

    /**
     * 构造函数，初始化环形缓冲区。
     *
     * @param size 缓冲区大小，必须大于0。
     * @throws IllegalArgumentException 如果大小小于或等于0。
     */
    @SuppressWarnings("unchecked")
    public CircularBuffer(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Buffer size must be positive");
        }
        // noinspection unchecked
        this.buffer = (T[]) new Object[size];
        this.putPointer = new CircularPointer(0, size);
        this.getPointer = new CircularPointer(0, size);
    }

    /**
     * 检查缓冲区是否为空。
     *
     * @return 如果缓冲区为空，返回 {@code true}；否则返回 {@code false}。
     */
    public boolean isEmpty() {
        return size.get() == 0;
    }

    /**
     * 检查缓冲区是否已满。
     *
     * @return 如果缓冲区已满，返回 {@code true}；否则返回 {@code false}。
     */
    public boolean isFull() {
        return size.get() == buffer.length;
    }

    public T get() {
        if (isEmpty()) {
            return null;
        }
        T item = buffer[getPointer.getAndIncrement()];
        size.decrementAndGet();
        return item;
    }

    public boolean put(T t) {
        if (t == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        if (isFull()) {
            // 如果缓冲区已满，覆盖最旧的数据
            getPointer.getAndIncrement();
        } else {
            size.incrementAndGet();
        }
        buffer[putPointer.getAndIncrement()] = t;
        return true;
    }


    private static class CircularPointer {
        private int pointer;
        private final int max;

        public CircularPointer(int pointer, int max) {
            this.pointer = pointer;
            this.max = max;
        }

        /**
         * 获取当前指针位置，并将指针向前移动一位。
         * 如果指针到达最大值，则回绕到0。
         *
         * @return 当前指针位置
         */
        public int getAndIncrement() {
            int current = pointer;
            pointer = (pointer + 1) % max;
            return current;
        }

    }
}
