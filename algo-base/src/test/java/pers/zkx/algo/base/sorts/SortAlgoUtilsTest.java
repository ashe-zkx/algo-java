package pers.zkx.algo.base.sorts;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author: zhangkuixing
 * @date: 2025/7/4
 */
public class SortAlgoUtilsTest {

    @Test
    void testSwapDifferentIndices() {
        Integer[] array = {1, 2, 3, 4, 5};
        SortAlgoUtils.swap(array, 1, 3);
        
        Integer[] expected = {1, 4, 3, 2, 5};
        Assertions.assertArrayEquals(expected, array);
    }

    @Test
    void testSwapSameIndex() {
        Integer[] array = {1, 2, 3, 4, 5};
        Integer[] originalArray = array.clone();
        
        SortAlgoUtils.swap(array, 2, 2);
        
        // Array should remain unchanged
        Assertions.assertArrayEquals(originalArray, array);
    }

    @Test
    void testSwapFirstAndLast() {
        String[] array = {"first", "middle", "last"};
        SortAlgoUtils.swap(array, 0, 2);
        
        String[] expected = {"last", "middle", "first"};
        Assertions.assertArrayEquals(expected, array);
    }

    @Test
    void testSwapWithNullElements() {
        String[] array = {"a", null, "c"};
        SortAlgoUtils.swap(array, 0, 1);
        
        String[] expected = {null, "a", "c"};
        Assertions.assertArrayEquals(expected, array);
    }

    @Test
    void testLessWithIntegers() {
        Assertions.assertTrue(SortAlgoUtils.less(1, 2));
        Assertions.assertFalse(SortAlgoUtils.less(2, 1));
        Assertions.assertFalse(SortAlgoUtils.less(2, 2));
    }

    @Test
    void testLessWithStrings() {
        Assertions.assertTrue(SortAlgoUtils.less("apple", "banana"));
        Assertions.assertFalse(SortAlgoUtils.less("banana", "apple"));
        Assertions.assertFalse(SortAlgoUtils.less("apple", "apple"));
    }

    @Test
    void testLessWithDoubles() {
        Assertions.assertTrue(SortAlgoUtils.less(1.5, 2.5));
        Assertions.assertFalse(SortAlgoUtils.less(2.5, 1.5));
        Assertions.assertFalse(SortAlgoUtils.less(1.5, 1.5));
    }

    @Test
    void testGreaterWithIntegers() {
        Assertions.assertTrue(SortAlgoUtils.greater(2, 1));
        Assertions.assertFalse(SortAlgoUtils.greater(1, 2));
        Assertions.assertFalse(SortAlgoUtils.greater(2, 2));
    }

    @Test
    void testGreaterWithStrings() {
        Assertions.assertTrue(SortAlgoUtils.greater("banana", "apple"));
        Assertions.assertFalse(SortAlgoUtils.greater("apple", "banana"));
        Assertions.assertFalse(SortAlgoUtils.greater("apple", "apple"));
    }

    @Test
    void testGreaterWithDoubles() {
        Assertions.assertTrue(SortAlgoUtils.greater(2.5, 1.5));
        Assertions.assertFalse(SortAlgoUtils.greater(1.5, 2.5));
        Assertions.assertFalse(SortAlgoUtils.greater(1.5, 1.5));
    }

    @Test
    void testLessAndGreaterConsistency() {
        Integer a = 5;
        Integer b = 10;
        
        // Exactly one of less, greater, or equal should be true
        boolean less = SortAlgoUtils.less(a, b);
        boolean greater = SortAlgoUtils.greater(a, b);
        boolean equal = a.compareTo(b) == 0;
        
        // Count how many are true
        int trueCount = (less ? 1 : 0) + (greater ? 1 : 0) + (equal ? 1 : 0);
        Assertions.assertEquals(1, trueCount);
    }

    @Test
    void testSwapSingleElementArray() {
        Integer[] array = {42};
        Integer[] originalArray = array.clone();
        
        SortAlgoUtils.swap(array, 0, 0);
        
        Assertions.assertArrayEquals(originalArray, array);
    }

    @Test
    void testComparisonWithNegativeNumbers() {
        Assertions.assertTrue(SortAlgoUtils.less(-5, -2));
        Assertions.assertTrue(SortAlgoUtils.greater(-2, -5));
        Assertions.assertTrue(SortAlgoUtils.less(-10, 0));
        Assertions.assertTrue(SortAlgoUtils.greater(0, -10));
    }

    @Test
    void testSwapWithCharacters() {
        Character[] array = {'a', 'b', 'c', 'd'};
        SortAlgoUtils.swap(array, 0, 3);
        
        Character[] expected = {'d', 'b', 'c', 'a'};
        Assertions.assertArrayEquals(expected, array);
    }
}