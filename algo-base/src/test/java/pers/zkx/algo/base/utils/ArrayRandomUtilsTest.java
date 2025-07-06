package pers.zkx.algo.base.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author: zhangkuixing
 * @date: 2025/7/4
 */
public class ArrayRandomUtilsTest {

    @Test
    void testGenerateRandomArray() {
        Integer[] array = ArrayRandomUtils.generateRandomArray(10);
        
        Assertions.assertNotNull(array);
        Assertions.assertEquals(10, array.length);
        
        // Check that all elements are within expected range (0 to size-1, which is 0-9)
        for (Integer value : array) {
            Assertions.assertNotNull(value);
            Assertions.assertTrue(value >= 0 && value < 10);
        }
    }

    @Test
    void testGenerateRandomArrayZeroSize() {
        Integer[] array = ArrayRandomUtils.generateRandomArray(0);
        
        Assertions.assertNotNull(array);
        Assertions.assertEquals(0, array.length);
    }

    @Test
    void testGenerateRandomArrayWithRange() {
        int size = 15;
        int min = 5;
        int max = 20;
        Integer[] array = ArrayRandomUtils.generateRandomArray(size, min, max);
        
        Assertions.assertNotNull(array);
        Assertions.assertEquals(size, array.length);
        
        // Check that all elements are within specified range
        for (Integer value : array) {
            Assertions.assertNotNull(value);
            Assertions.assertTrue(value >= min && value <= max, 
                "Value " + value + " is not in range [" + min + ", " + max + "]");
        }
    }

    @Test
    void testGenerateRandomArrayWithNegative() {
        int size = 10;
        int min = -10;
        int max = 10;
        Integer[] array = ArrayRandomUtils.generateRandomArrayWithNegative(size, min, max);
        
        Assertions.assertNotNull(array);
        Assertions.assertEquals(size, array.length);
        
        // Check that all elements are within specified range
        for (Integer value : array) {
            Assertions.assertNotNull(value);
            Assertions.assertTrue(value >= min && value <= max,
                "Value " + value + " is not in range [" + min + ", " + max + "]");
        }
    }

    @Test
    void testGenerateRandomDoubleArray() {
        int size = 10;
        double min = -5.5;
        double max = 15.7;
        Double[] array = ArrayRandomUtils.generateRandomDoubleArray(size, min, max);
        
        Assertions.assertNotNull(array);
        Assertions.assertEquals(size, array.length);
        
        // Check that all elements are within specified range
        for (Double value : array) {
            Assertions.assertNotNull(value);
            Assertions.assertTrue(value >= min && value <= max,
                "Value " + value + " is not in range [" + min + ", " + max + "]");
        }
    }

    @Test
    void testGenerateRandomArraySingleElement() {
        Integer[] array = ArrayRandomUtils.generateRandomArray(1);
        
        Assertions.assertNotNull(array);
        Assertions.assertEquals(1, array.length);
        Assertions.assertNotNull(array[0]);
        Assertions.assertTrue(array[0] >= 0 && array[0] < 1);
        Assertions.assertEquals(0, (int) array[0]); // Only possible value is 0
    }

    @Test
    void testGenerateRandomArraySameMinMax() {
        int size = 5;
        int value = 42;
        Integer[] array = ArrayRandomUtils.generateRandomArray(size, value, value);
        
        Assertions.assertNotNull(array);
        Assertions.assertEquals(size, array.length);
        
        // All values should be exactly the same
        for (Integer element : array) {
            Assertions.assertEquals(value, (int) element);
        }
    }

    @Test
    void testGenerateRandomDoubleArraySameMinMax() {
        int size = 5;
        double value = 3.14;
        Double[] array = ArrayRandomUtils.generateRandomDoubleArray(size, value, value);
        
        Assertions.assertNotNull(array);
        Assertions.assertEquals(size, array.length);
        
        // All values should be exactly the same
        for (Double element : array) {
            Assertions.assertEquals(value, element, 0.0001);
        }
    }

    @Test
    void testRandomness() {
        // Test that the method actually produces different values over multiple calls
        Integer[] array1 = ArrayRandomUtils.generateRandomArray(100, 1, 1000);
        Integer[] array2 = ArrayRandomUtils.generateRandomArray(100, 1, 1000);
        
        // It's extremely unlikely that two 100-element arrays would be identical
        boolean hasDifference = false;
        for (int i = 0; i < array1.length; i++) {
            if (!array1[i].equals(array2[i])) {
                hasDifference = true;
                break;
            }
        }
        
        Assertions.assertTrue(hasDifference, "Arrays should be different due to randomness");
    }
}