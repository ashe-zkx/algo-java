package pers.zkx.algo.base.structures.buffers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author: zhangkuixing
 * @date: 2025/7/4
 */
public class CircularBufferTest {

    private CircularBuffer<String> buffer;

    @BeforeEach
    void setUp() {
        buffer = new CircularBuffer<>(3);
    }

    @Test
    void testConstructor() {
        CircularBuffer<String> newBuffer = new CircularBuffer<>(5);
        Assertions.assertNotNull(newBuffer);
        Assertions.assertTrue(newBuffer.isEmpty());
        Assertions.assertFalse(newBuffer.isFull());
    }

    @Test
    void testConstructorValidation() {
        Assertions.assertThrows(IllegalArgumentException.class, 
            () -> new CircularBuffer<>(0));
        Assertions.assertThrows(IllegalArgumentException.class, 
            () -> new CircularBuffer<>(-1));
    }

    @Test
    void testInitialState() {
        Assertions.assertTrue(buffer.isEmpty());
        Assertions.assertFalse(buffer.isFull());
        Assertions.assertNull(buffer.get());
    }

    @Test
    void testPutAndGet() {
        buffer.put("item1");
        Assertions.assertFalse(buffer.isEmpty());
        
        String retrieved = buffer.get();
        Assertions.assertEquals("item1", retrieved);
        Assertions.assertTrue(buffer.isEmpty());
    }

    @Test
    void testPutNullItem() {
        Assertions.assertThrows(IllegalArgumentException.class, 
            () -> buffer.put(null));
    }

    @Test
    void testFillBuffer() {
        buffer.put("item1");
        buffer.put("item2");
        buffer.put("item3");
        
        Assertions.assertTrue(buffer.isFull());
        Assertions.assertFalse(buffer.isEmpty());
    }

    @Test
    void testOverflow() {
        // Fill the buffer
        buffer.put("item1");
        buffer.put("item2");
        buffer.put("item3");
        
        // Add one more item - should overwrite the oldest
        buffer.put("item4");
        
        // The buffer should still be full
        Assertions.assertTrue(buffer.isFull());
        
        // First item should be item2 (item1 was overwritten)
        Assertions.assertEquals("item2", buffer.get());
        Assertions.assertEquals("item3", buffer.get());
        Assertions.assertEquals("item4", buffer.get());
        
        Assertions.assertTrue(buffer.isEmpty());
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
    void testMixedOperations() {
        buffer.put("item1");
        String item = buffer.get();
        Assertions.assertEquals("item1", item);
        
        buffer.put("item2");
        buffer.put("item3");
        
        Assertions.assertEquals("item2", buffer.get());
        
        buffer.put("item4");
        buffer.put("item5");
        
        Assertions.assertEquals("item3", buffer.get());
        Assertions.assertEquals("item4", buffer.get());
        Assertions.assertEquals("item5", buffer.get());
        
        Assertions.assertTrue(buffer.isEmpty());
    }

    @Test
    void testGetFromEmptyBuffer() {
        Assertions.assertNull(buffer.get());
        Assertions.assertTrue(buffer.isEmpty());
    }

    @Test
    void testStateAfterOperations() {
        // Empty
        Assertions.assertTrue(buffer.isEmpty());
        Assertions.assertFalse(buffer.isFull());
        
        // Partially filled
        buffer.put("item1");
        Assertions.assertFalse(buffer.isEmpty());
        Assertions.assertFalse(buffer.isFull());
        
        // Full
        buffer.put("item2");
        buffer.put("item3");
        Assertions.assertFalse(buffer.isEmpty());
        Assertions.assertTrue(buffer.isFull());
        
        // Back to empty
        buffer.get();
        buffer.get();
        buffer.get();
        Assertions.assertTrue(buffer.isEmpty());
        Assertions.assertFalse(buffer.isFull());
    }
}