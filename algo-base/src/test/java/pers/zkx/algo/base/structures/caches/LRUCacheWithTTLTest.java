package pers.zkx.algo.base.structures.caches;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author: zhangkuixing
 * @date: 2025/7/4
 */
public class LRUCacheWithTTLTest {

    private LRUCacheWithTTL<String, String> cache;

    @BeforeEach
    void setUp() {
        cache = new LRUCacheWithTTL<>(3, 5000); // capacity: 3, TTL: 5 seconds
    }

    @Test
    void testDefaultConstructor() {
        LRUCacheWithTTL<String, String> defaultCache = new LRUCacheWithTTL<>();
        Assertions.assertNotNull(defaultCache);
    }

    @Test
    void testConstructorValidation() {
        Assertions.assertThrows(RuntimeException.class, 
            () -> new LRUCacheWithTTL<>(0, 1000));
        Assertions.assertThrows(RuntimeException.class, 
            () -> new LRUCacheWithTTL<>(-1, 1000));
        Assertions.assertThrows(RuntimeException.class, 
            () -> new LRUCacheWithTTL<>(10, 0));
        Assertions.assertThrows(RuntimeException.class, 
            () -> new LRUCacheWithTTL<>(10, -1));
    }

    @Test
    void testPutAndGet() {
        cache.put("key1", "value1");
        cache.put("key2", "value2");
        
        Assertions.assertEquals("value1", cache.get("key1"));
        Assertions.assertEquals("value2", cache.get("key2"));
    }

    @Test
    void testGetNonExistentKey() {
        Assertions.assertNull(cache.get("nonexistent"));
    }

    @Test
    void testLRUEviction() {
        cache.put("key1", "value1");
        cache.put("key2", "value2");
        cache.put("key3", "value3");
        cache.put("key4", "value4"); // Should evict key1
        
        Assertions.assertNull(cache.get("key1")); // Evicted
        Assertions.assertEquals("value2", cache.get("key2"));
        Assertions.assertEquals("value3", cache.get("key3"));
        Assertions.assertEquals("value4", cache.get("key4"));
    }

    @Test
    void testUpdateExistingKey() {
        cache.put("key1", "value1");
        cache.put("key1", "value2"); // Update
        
        Assertions.assertEquals("value2", cache.get("key1"));
    }

    @Test
    void testLRUOrder() {
        cache.put("key1", "value1");
        cache.put("key2", "value2");
        cache.put("key3", "value3");
        
        // Access key1 to make it most recently used
        cache.get("key1");
        
        // Add another key, should evict key2 (least recently used)
        cache.put("key4", "value4");
        
        Assertions.assertEquals("value1", cache.get("key1")); // Still there
        Assertions.assertNull(cache.get("key2")); // Evicted
        Assertions.assertEquals("value3", cache.get("key3")); // Still there
        Assertions.assertEquals("value4", cache.get("key4")); // Still there
    }

    @Test
    void testResetCapacityAndTTL() {
        cache.put("key1", "value1");
        cache.put("key2", "value2");
        cache.put("key3", "value3");
        
        // Reset to smaller capacity
        cache.resetCapacityAndTTL(2, 2000);
        
        // Should have reduced the number of items
        // At least one should have been evicted
        int accessibleItems = 0;
        if (cache.get("key1") != null) accessibleItems++;
        if (cache.get("key2") != null) accessibleItems++;
        if (cache.get("key3") != null) accessibleItems++;
        
        Assertions.assertTrue(accessibleItems <= 2);
    }

    @Test
    void testResetCapacityAndTTLValidation() {
        Assertions.assertThrows(RuntimeException.class, 
            () -> cache.resetCapacityAndTTL(0, 1000));
        Assertions.assertThrows(RuntimeException.class, 
            () -> cache.resetCapacityAndTTL(-1, 1000));
        Assertions.assertThrows(RuntimeException.class, 
            () -> cache.resetCapacityAndTTL(10, 0));
        Assertions.assertThrows(RuntimeException.class, 
            () -> cache.resetCapacityAndTTL(10, -1));
    }

    @Test
    void testCleanUpMethod() {
        cache.put("key1", "value1");
        cache.put("key2", "value2");
        
        // Call cleanup (should not fail even if items aren't expired)
        cache.cleanUp();
        
        // Items should still be there since they're not expired
        Assertions.assertEquals("value1", cache.get("key1"));
        Assertions.assertEquals("value2", cache.get("key2"));
    }

    @Test
    void testEmptyCache() {
        Assertions.assertNull(cache.get("any"));
    }

    @Test
    void testSingleItemCache() {
        LRUCacheWithTTL<String, String> singleCache = new LRUCacheWithTTL<>(1, 5000);
        
        singleCache.put("key1", "value1");
        Assertions.assertEquals("value1", singleCache.get("key1"));
        
        singleCache.put("key2", "value2"); // Should evict key1
        Assertions.assertNull(singleCache.get("key1"));
        Assertions.assertEquals("value2", singleCache.get("key2"));
    }
}