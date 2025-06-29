package pers.zkx.algo.base.sorts;

import org.junit.jupiter.api.Test;
import pers.zkx.algo.base.utils.ArrayRandomUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

/**
 * @author: zhangkuixing
 * @date: 2025/6/29 22:02
 */
public abstract class SortAlgoApiTest {

    abstract SortAlgoApi getSortAlgoApi();

    @Test
    void shouldAcceptWhenEmptyArrayIsPassed() {
        Integer[] array = new Integer[]{};
        Integer[] expected = new Integer[]{};

        Integer[] sorted = getSortAlgoApi().sort(array);

        assertArrayEquals(expected, sorted);
    }

    @Test
    void shouldAcceptWhenEmptyListIsPassed() {
        List<Integer> list = new ArrayList<>();
        List<Integer> expected = new ArrayList<>();

        List<Integer> sorted = getSortAlgoApi().sort(list);

        assertIterableEquals(expected, sorted);
    }

    @Test
    void shouldAcceptWhenSingleValuedArrayIsPassed() {
        Integer[] array = new Integer[]{2};
        Integer[] expected = new Integer[]{2};

        Integer[] sorted = getSortAlgoApi().sort(array);

        assertArrayEquals(expected, sorted);
    }

    @Test
    void shouldAcceptWhenSingleValuedListIsPassed() {
        List<Integer> list = List.of(2);
        List<Integer> expected = List.of(2);

        List<Integer> sorted = getSortAlgoApi().sort(list);

        assertIterableEquals(expected, sorted);
    }

    @Test
    void shouldAcceptWhenListWithAllPositiveValuesIsPassed() {
        Integer[] array = ArrayRandomUtils.generateRandomArrayWithNegative(100, -1000, 1000);
        Integer[] expected = Arrays.stream(array).sorted().toArray(Integer[]::new);
        Integer[] sorted = getSortAlgoApi().sort(array);

        assertArrayEquals(expected, sorted);
    }


    @Test
    void shouldAcceptWhenDoubleListWithAllPositiveValuesIsPassed() {
        Double[] array = ArrayRandomUtils.generateRandomDoubleArray(100, -1000, 1000);
        Double[] expected = Arrays.stream(array).sorted().toArray(Double[]::new);
        Double[] sorted = getSortAlgoApi().sort(array);

        assertArrayEquals(expected, sorted);
    }


}
