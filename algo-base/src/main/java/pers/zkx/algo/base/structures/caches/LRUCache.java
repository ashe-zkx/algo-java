package pers.zkx.algo.base.structures.caches;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * lru缓存实现。基于双向链表和哈希表实现。
 *
 * @author: zhangkuixing
 * @date: 2025/6/29 22:57
 */
public class LRUCache<K, V> {

    private final Map<K, Entry<K, V>> data = new HashMap<>();
    private Entry<K, V> head;
    private Entry<K, V> tail;
    private int cap;
    private static final int DEFAULT_CAP = 100;


    public LRUCache() {
        setCapacity(DEFAULT_CAP);
    }

    public LRUCache(int cap) {
        setCapacity(cap);
    }


    private void setCapacity(int newCapacity) {
        if (newCapacity <= 0) {
            throw new RuntimeException("capacity must be greater than 0!");
        }
        // 删除超过新容量的数据
        for (int i = data.size(); i > newCapacity; i--) {
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
        // 将访问的元素移动到链表尾部
        moveNodeToLast(entry);
        return entry.getValue();
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
        tail = entry;
    }

    public void put(K key, V value) {
        if (data.containsKey(key)) {
            Entry<K, V> entry = data.get(key);
            entry.setValue(value);
            moveNodeToLast(entry);
            return;
        }
        if (data.size() == cap) {
            Entry<K, V> evicted = evict();
            data.remove(evicted.getKey());
        }
        Entry<K, V> newEntry = new Entry<>();
        newEntry.setKey(key);
        newEntry.setValue(value);
        addNewEntry(newEntry);
        data.put(key, newEntry);


    }

    private void addNewEntry(Entry<K, V> newEntry) {
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
        head.setPreEntry(null);
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
