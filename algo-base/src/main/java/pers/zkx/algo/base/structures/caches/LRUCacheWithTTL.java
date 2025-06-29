package pers.zkx.algo.base.structures.caches;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * lru+ttl缓存实现。
 *
 * @author: zhangkuixing
 * @date: 2025/6/29 22:57
 */
public class LRUCacheWithTTL<K, V> {

    private final Map<K, Entry<K, V>> data = new HashMap<>();
    private Entry<K, V> head;
    private Entry<K, V> tail;
    private int cap;
    private long ttlMillis;

    private static final int DEFAULT_CAP = 100;
    private static final long DEFAULT_TTL_MILLIS = 60000; // 默认TTL为60秒


    public LRUCacheWithTTL() {
        this(DEFAULT_CAP, DEFAULT_TTL_MILLIS);
    }

    public LRUCacheWithTTL(int cap, long ttlMillis) {
        if (cap <= 0 || ttlMillis <= 0) {
            throw new RuntimeException("capacity and ttlMillis must be greater than 0!");
        }
        this.cap = cap;
        this.ttlMillis = ttlMillis;
    }


    public void resetCapacityAndTTL(int newCapacity, long newTtlMillis) {
        if (newCapacity <= 0 || newTtlMillis <= 0) {
            throw new RuntimeException("capacity and ttlMillis must be greater than 0!");
        }
        this.ttlMillis = newTtlMillis; // 先设置TTL
        cleanUp(); // 再清理过期数据

        // 删除超过新容量的数据
        while (data.size() > newCapacity) {
            Entry<K, V> evicted = evict();
            data.remove(evicted.getKey());
        }
        this.cap = newCapacity;
    }


    public V get(K key) {
        Entry<K, V> entry = data.get(key);
        if (entry == null) {
            return null;
        }
        if (System.currentTimeMillis() - entry.getTimestamp() > ttlMillis) {
            // 如果元素过期了, 删除该元素
            deleteNode(entry);
            data.remove(key);
            return null;
        }
        // 将访问的元素移动到链表尾部
        moveNodeToLast(entry);
        return entry.getValue();
    }

    public void cleanUp() {
        long currentTime = System.currentTimeMillis();
        Entry<K, V> current = head;
        while (current != null) {
            Entry<K, V> next = current.getNextEntry();
            if (currentTime - current.getTimestamp() > ttlMillis) {
                deleteNode(current);
                data.remove(current.getKey());
            }
            current = next;
        }
    }

    private void moveNodeToLast(Entry<K, V> entry) {
        // 已经是最后一个元素
        if (tail == entry) {
            return;
        }

        // 如果是中间节点, 需要将前后节点连接起来
        final Entry<K, V> prev = entry.getPreEntry();
        final Entry<K, V> next = entry.getNextEntry();
        if (prev != null) {
            prev.setNextEntry(next);
        }
        if (next != null) {
            next.setPreEntry(prev);
        }

        // 如果是头节点
        if (head == entry) {
            head = next;
        }
        // 将元素移动到链表尾部
        tail.setNextEntry(entry);
        entry.setPreEntry(tail);
        entry.setNextEntry(null);
        entry.setTimestamp(System.currentTimeMillis());
        tail = entry;
    }

    private void deleteNode(Entry<K, V> entry) {
        if (entry == null) {
            return;
        }
        // 如果是头节点
        if (head == entry) {
            head = entry.getNextEntry();
            if (head != null) {
                head.setPreEntry(null);
            }
        }
        // 如果是尾节点
        if (tail == entry) {
            tail = entry.getPreEntry();
            if (tail != null) {
                tail.setNextEntry(null);
            }
        }

        // 如果是中间节点
        final Entry<K, V> prev = entry.getPreEntry();
        final Entry<K, V> next = entry.getNextEntry();
        if (prev != null) {
            prev.setNextEntry(next);
        }
        if (next != null) {
            next.setPreEntry(prev);
        }
        entry.setPreEntry(null);
        entry.setNextEntry(null);
    }

    public void put(K key, V value) {
        if (data.containsKey(key)) {
            Entry<K, V> entry = data.get(key);
            entry.setValue(value);
            entry.setTimestamp(System.currentTimeMillis());
            moveNodeToLast(entry);
            return;
        }
        if (data.size() >= cap) {
            // 如果缓存已满，先清理过期元素
            cleanUp();
            if (data.size() >= cap) {
                // 如果清理后仍然满了，进行驱逐
                Entry<K, V> evicted = evict();
                data.remove(evicted.getKey());
            }
        }
        Entry<K, V> newEntry = new Entry<>();
        newEntry.setKey(key);
        newEntry.setValue(value);
        addNewEntry(newEntry);
        data.put(key, newEntry);
    }

    private void addNewEntry(Entry<K, V> newEntry) {
        newEntry.setTimestamp(System.currentTimeMillis());
        if (data.isEmpty()) {
            head = newEntry;
            tail = newEntry;
            return;
        }
        tail.setNextEntry(newEntry);
        newEntry.setPreEntry(tail);
        newEntry.setNextEntry(null);
        tail = newEntry;
    }


    /**
     * 从缓存中获取最近使用最少的元素
     *
     * @return 最近使用最少的元素
     */
    private Entry<K, V> evict() {
        if (head == null) {
            throw new RuntimeException("cache cannot be empty!");
        }
        Entry<K, V> evicted = head;
        head = evicted.getNextEntry();
        if (head != null) {
            head.setPreEntry(null);
        } else {
            tail = null;
        }
        evicted.setNextEntry(null);
        return evicted;
    }

    @Getter
    @Setter
    static final class Entry<I, J> {
        private Entry<I, J> preEntry;
        private Entry<I, J> nextEntry;
        private I key;
        private J value;
        long timestamp = System.currentTimeMillis();

        Entry() {
        }

        Entry(Entry<I, J> preEntry, Entry<I, J> nextEntry, I key, J value) {
            this.preEntry = preEntry;
            this.nextEntry = nextEntry;
            this.key = key;
            this.value = value;
        }
    }
}
