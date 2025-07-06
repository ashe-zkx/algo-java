package pers.zkx.algo.base.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author: zhangkuixing
 * @date: 2025/7/4
 */
public class ListRandomUtilsTest {

    private ListRandomUtils listRandomUtils;

    @BeforeEach
    void setUp() {
        listRandomUtils = new ListRandomUtils();
    }

    @Test
    void testGenerateRandomList() {
        List<Integer> list = listRandomUtils.generateRandomList(10);
        
        Assertions.assertNotNull(list);
        Assertions.assertEquals(10, list.size());
        
        // Check that all elements are within expected range (0 to size-1, which is 0-9)
        for (Integer value : list) {
            Assertions.assertNotNull(value);
            Assertions.assertTrue(value >= 0 && value < 10);
        }
    }

    @Test
    void testGenerateRandomListZeroSize() {
        List<Integer> list = listRandomUtils.generateRandomList(0);
        
        Assertions.assertNotNull(list);
        Assertions.assertEquals(0, list.size());
    }

    @Test
    void testGenerateRandomListWithRange() {
        int size = 15;
        int min = 5;
        int max = 20;
        List<Integer> list = listRandomUtils.generateRandomList(size, min, max);
        
        Assertions.assertNotNull(list);
        Assertions.assertEquals(size, list.size());
        
        // Check that all elements are within specified range
        for (Integer value : list) {
            Assertions.assertNotNull(value);
            Assertions.assertTrue(value >= min && value <= max, 
                "Value " + value + " is not in range [" + min + ", " + max + "]");
        }
    }

    @Test
    void testGenerateRandomListWithNegative() {
        int size = 10;
        int min = -10;
        int max = 10;
        List<Integer> list = listRandomUtils.generateRandomListWithNegative(size, min, max);
        
        Assertions.assertNotNull(list);
        Assertions.assertEquals(size, list.size());
        
        // Check that all elements are within specified range
        for (Integer value : list) {
            Assertions.assertNotNull(value);
            Assertions.assertTrue(value >= min && value <= max,
                "Value " + value + " is not in range [" + min + ", " + max + "]");
        }
    }

    @Test
    void testGenerateRandomDoubleList() {
        int size = 10;
        double min = -5.5;
        double max = 15.7;
        List<Double> list = listRandomUtils.generateRandomDoubleList(size, min, max);
        
        Assertions.assertNotNull(list);
        Assertions.assertEquals(size, list.size());
        
        // Check that all elements are within specified range
        for (Double value : list) {
            Assertions.assertNotNull(value);
            Assertions.assertTrue(value >= min && value <= max,
                "Value " + value + " is not in range [" + min + ", " + max + "]");
        }
    }

    @Test
    void testGenerateRandomListSingleElement() {
        List<Integer> list = listRandomUtils.generateRandomList(1);
        
        Assertions.assertNotNull(list);
        Assertions.assertEquals(1, list.size());
        Assertions.assertNotNull(list.get(0));
        Assertions.assertTrue(list.get(0) >= 0 && list.get(0) < 1);
        Assertions.assertEquals(0, (int) list.get(0)); // Only possible value is 0
    }

    @Test
    void testGenerateRandomListSameMinMax() {
        int size = 5;
        int value = 42;
        List<Integer> list = listRandomUtils.generateRandomList(size, value, value);
        
        Assertions.assertNotNull(list);
        Assertions.assertEquals(size, list.size());
        
        // All values should be exactly the same
        for (Integer element : list) {
            Assertions.assertEquals(value, (int) element);
        }
    }

    @Test
    void testGenerateRandomDoubleListSameMinMax() {
        int size = 5;
        double value = 3.14;
        List<Double> list = listRandomUtils.generateRandomDoubleList(size, value, value);
        
        Assertions.assertNotNull(list);
        Assertions.assertEquals(size, list.size());
        
        // All values should be exactly the same
        for (Double element : list) {
            Assertions.assertEquals(value, element, 0.0001);
        }
    }

    @Test
    void testRandomness() {
        // Test that the method actually produces different values over multiple calls
        List<Integer> list1 = listRandomUtils.generateRandomList(100, 1, 1000);
        List<Integer> list2 = listRandomUtils.generateRandomList(100, 1, 1000);
        
        // It's extremely unlikely that two 100-element lists would be identical
        boolean hasDifference = false;
        for (int i = 0; i < list1.size(); i++) {
            if (!list1.get(i).equals(list2.get(i))) {
                hasDifference = true;
                break;
            }
        }
        
        Assertions.assertTrue(hasDifference, "Lists should be different due to randomness");
    }
}