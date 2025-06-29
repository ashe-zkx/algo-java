package pers.zkx.algo.base.structures.caches;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 优化的线程安全LRU缓存，支持TTL过期
 *
 * @author: zhangkuixing
 * @date: 2025/6/29 23:51
 */
public class OptimizedLRUCacheWithTTL<K, V> {

    private final ConcurrentHashMap<K, Entry<K, V>> data;
    private Entry<K, V> head;
    private Entry<K, V> tail;
    private volatile int cap;
    private volatile long ttlMillis;

    // 线程安全
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
    private final ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();

    // 统计信息
    private final AtomicLong hitCount = new AtomicLong(0);
    private final AtomicLong missCount = new AtomicLong(0);
    private volatile long lastCleanupTime = System.currentTimeMillis();

    private static final int DEFAULT_CAP = 100;
    private static final long DEFAULT_TTL_MILLIS = 60000; // 默认TTL为60秒
    private static final long CLEANUP_INTERVAL = 30000; // 清理间隔30秒

    public OptimizedLRUCacheWithTTL() {
        this(DEFAULT_CAP, DEFAULT_TTL_MILLIS);
    }

    public OptimizedLRUCacheWithTTL(int cap, long ttlMillis) {
        if (cap <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than 0, got: " + cap);
        }
        if (ttlMillis <= 0) {
            throw new IllegalArgumentException("TTL must be greater than 0, got: " + ttlMillis);
        }
        this.cap = cap;
        this.ttlMillis = ttlMillis;
        this.data = new ConcurrentHashMap<>(cap);
        initializeList();
    }

    private void initializeList() {
        // 使用哨兵节点简化边界处理
        head = new Entry<>();
        tail = new Entry<>();
        head.nextEntry = tail;
        tail.preEntry = head;
    }

    public void resetCapacityAndTTL(int newCapacity, long newTtlMillis) {
        if (newCapacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than 0, got: " + newCapacity);
        }
        if (newTtlMillis <= 0) {
            throw new IllegalArgumentException("TTL must be greater than 0, got: " + newTtlMillis);
        }

        writeLock.lock();
        try {
            this.ttlMillis = newTtlMillis;
            cleanUpExpiredEntries();

            // 删除超过新容量的数据
            while (data.size() > newCapacity) {
                Entry<K, V> evicted = evictLRU();
                data.remove(evicted.key);
            }
            this.cap = newCapacity;
        } finally {
            writeLock.unlock();
        }
    }

    public V get(K key) {
        if (key == null) {
            return null;
        }

        // 定期清理过期数据
        periodicCleanup();

        readLock.lock();
        try {
            Entry<K, V> entry = data.get(key);
            if (entry == null) {
                missCount.incrementAndGet();
                return null;
            }

            if (isExpired(entry)) {
                readLock.unlock();
                writeLock.lock();
                try {
                    // 双重检查
                    entry = data.get(key);
                    if (entry != null && isExpired(entry)) {
                        removeEntry(entry);
                        data.remove(key);
                    }
                    missCount.incrementAndGet();
                    return null;
                } finally {
                    writeLock.unlock();
                }
            } else {
                // 升级为写锁来移动节点
                readLock.unlock();
                writeLock.lock();
                try {
                    // 双重检查
                    entry = data.get(key);
                    if (entry != null && !isExpired(entry)) {
                        moveToTail(entry);
                        hitCount.incrementAndGet();
                        return entry.value;
                    } else {
                        missCount.incrementAndGet();
                        return null;
                    }
                } finally {
                    writeLock.unlock();
                }
            }
        } finally {
            if (readLock.tryLock()) {
                readLock.unlock();
            }
        }
    }

    private boolean isExpired(Entry<K, V> entry) {
        return System.currentTimeMillis() - entry.timestamp > ttlMillis;
    }

    private void periodicCleanup() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastCleanupTime > CLEANUP_INTERVAL) {
            writeLock.lock();
            try {
                if (currentTime - lastCleanupTime > CLEANUP_INTERVAL) {
                    cleanUpExpiredEntries();
                    lastCleanupTime = currentTime;
                }
            } finally {
                writeLock.unlock();
            }
        }
    }

    public void cleanUp() {
        writeLock.lock();
        try {
            cleanUpExpiredEntries();
        } finally {
            writeLock.unlock();
        }
    }

    private void cleanUpExpiredEntries() {
        Entry<K, V> current = head.nextEntry;
        while (current != tail) {
            Entry<K, V> next = current.nextEntry;
            if (isExpired(current)) {
                removeEntry(current);
                data.remove(current.key);
            }
            current = next;
        }
    }

    private void moveToTail(Entry<K, V> entry) {
        if (entry.preEntry == tail.preEntry) {
            return; // 已经在尾部
        }

        // 从当前位置移除
        entry.preEntry.nextEntry = entry.nextEntry;
        entry.nextEntry.preEntry = entry.preEntry;

        // 插入到尾部
        entry.preEntry = tail.preEntry;
        entry.nextEntry = tail;
        tail.preEntry.nextEntry = entry;
        tail.preEntry = entry;

        entry.timestamp = System.currentTimeMillis();
    }

    private void removeEntry(Entry<K, V> entry) {
        entry.preEntry.nextEntry = entry.nextEntry;
        entry.nextEntry.preEntry = entry.preEntry;
    }

    public void put(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Key and value cannot be null");
        }

        writeLock.lock();
        try {
            Entry<K, V> existing = data.get(key);
            if (existing != null) {
                existing.value = value;
                existing.timestamp = System.currentTimeMillis();
                moveToTail(existing);
                return;
            }

            if (data.size() >= cap) {
                cleanUpExpiredEntries();
                if (data.size() >= cap) {
                    Entry<K, V> evicted = evictLRU();
                    data.remove(evicted.key);
                }
            }

            Entry<K, V> newEntry = new Entry<>(key, value);
            addToTail(newEntry);
            data.put(key, newEntry);
        } finally {
            writeLock.unlock();
        }
    }

    private void addToTail(Entry<K, V> entry) {
        entry.preEntry = tail.preEntry;
        entry.nextEntry = tail;
        tail.preEntry.nextEntry = entry;
        tail.preEntry = entry;
    }

    private Entry<K, V> evictLRU() {
        Entry<K, V> first = head.nextEntry;
        if (first == tail) {
            throw new IllegalStateException("Cache is empty, cannot evict");
        }
        removeEntry(first);
        return first;
    }

    // 统计信息方法
    public long getHitCount() {
        return hitCount.get();
    }

    public long getMissCount() {
        return missCount.get();
    }

    public double getHitRate() {
        long hits = hitCount.get();
        long total = hits + missCount.get();
        return total == 0 ? 0.0 : (double) hits / total;
    }

    public int size() {
        readLock.lock();
        try {
            return data.size();
        } finally {
            readLock.unlock();
        }
    }

    public void clear() {
        writeLock.lock();
        try {
            data.clear();
            initializeList();
            hitCount.set(0);
            missCount.set(0);
        } finally {
            writeLock.unlock();
        }
    }

    @Getter
    @Setter
    static final class Entry<K, V> {
        private Entry<K, V> preEntry;
        private Entry<K, V> nextEntry;
        private K key;
        private V value;
        private long timestamp;

        Entry() {
            this.timestamp = System.currentTimeMillis();
        }

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
            this.timestamp = System.currentTimeMillis();
        }
    }
}
