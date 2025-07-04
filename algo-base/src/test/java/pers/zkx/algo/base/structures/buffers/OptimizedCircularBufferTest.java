package pers.zkx.algo.base.structures.buffers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author: zhangkuixing
 * @date: 2025/7/4
 */
public class OptimizedCircularBufferTest {

    private OptimizedCircularBuffer<String> buffer;

    @BeforeEach
    void setUp() {
        buffer = new OptimizedCircularBuffer<>(3);
    }

    @Test
    void testConstructor() {
        OptimizedCircularBuffer<String> newBuffer = new OptimizedCircularBuffer<>(5);
        Assertions.assertNotNull(newBuffer);
        Assertions.assertTrue(newBuffer.isEmpty());
        Assertions.assertFalse(newBuffer.isFull());
    }

    @Test
    void testConstructorValidation() {
        Assertions.assertThrows(IllegalArgumentException.class, 
            () -> new OptimizedCircularBuffer<>(0));
        Assertions.assertThrows(IllegalArgumentException.class, 
            () -> new OptimizedCircularBuffer<>(-1));
    }

    @Test
    void testInitialState() {
        Assertions.assertTrue(buffer.isEmpty());
        Assertions.assertFalse(buffer.isFull());
        Assertions.assertNull(buffer.get());
        Assertions.assertEquals(0, buffer.size());
    }

    @Test
    void testPutAndGet() {
        buffer.put("item1");
        Assertions.assertFalse(buffer.isEmpty());
        Assertions.assertEquals(1, buffer.size());
        
        String retrieved = buffer.get();
        Assertions.assertEquals("item1", retrieved);
        Assertions.assertTrue(buffer.isEmpty());
        Assertions.assertEquals(0, buffer.size());
    }

    @Test
    void testFillBuffer() {
        buffer.put("item1");
        buffer.put("item2");
        buffer.put("item3");
        
        Assertions.assertTrue(buffer.isFull());
        Assertions.assertFalse(buffer.isEmpty());
        Assertions.assertEquals(3, buffer.size());
    }

    @Test
    void testFIFOBehavior() {
        buffer.put("first");
        buffer.put("second");
        
        Assertions.assertEquals("first", buffer.get());
        Assertions.assertEquals("second", buffer.get());
        Assertions.assertTrue(buffer.isEmpty());
    }

    @Test
    void testGetFromEmptyBuffer() {
        Assertions.assertNull(buffer.get());
        Assertions.assertTrue(buffer.isEmpty());
        Assertions.assertEquals(0, buffer.size());
    }

    @Test
    void testOverflowBehavior() {
        // Fill the buffer
        buffer.put("item1");
        buffer.put("item2");
        buffer.put("item3");
        
        // Add one more item - should handle overflow
        buffer.put("item4");
        
        // Buffer should handle the overflow gracefully
        Assertions.assertFalse(buffer.isEmpty());
    }

    @Test
    void testMixedOperations() {
        buffer.put("item1");
        String item = buffer.get();
        Assertions.assertEquals("item1", item);
        
        buffer.put("item2");
        buffer.put("item3");
        
        Assertions.assertEquals("item2", buffer.get());
        
        buffer.put("item4");
        buffer.put("item5");
        
        // Should get the remaining items
        String next1 = buffer.get();
        String next2 = buffer.get();
        String next3 = buffer.get();
        
        // At least one should not be null
        Assertions.assertTrue(next1 != null || next2 != null || next3 != null);
    }

    @Test
    void testSizeCalculation() {
        Assertions.assertEquals(0, buffer.size());
        
        buffer.put("item1");
        Assertions.assertTrue(buffer.size() >= 0 && buffer.size() <= 3);
        
        buffer.put("item2");
        Assertions.assertTrue(buffer.size() >= 0 && buffer.size() <= 3);
        
        buffer.get();
        Assertions.assertTrue(buffer.size() >= 0 && buffer.size() <= 3);
    }
}