package pers.zkx.algo.base.structures.bloomfilter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author: zhangkuixing
 * @date: 2025/6/29 22:49
 */
public class BloomFilterTest {

    private BloomFilter<String> bloomFilter;

    @BeforeEach
    void setUp() {
        bloomFilter = new BloomFilter<>(3, 100);
    }

    @Test
    public void testIntegerContains() {
        BloomFilter<Integer> bloomFilter = new BloomFilter<>(3, 10);
        bloomFilter.insert(3);
        bloomFilter.insert(17);

        assert bloomFilter.mightContain(3);
        assert bloomFilter.mightContain(17);
    }

    @Test
    public void testStringContains() {
        bloomFilter.insert("omar");
        bloomFilter.insert("mahamid");

        Assertions.assertTrue(bloomFilter.mightContain("omar"));
        Assertions.assertTrue(bloomFilter.mightContain("mahamid"));
    }

    @Test
    void testInsertAndContains() {
        bloomFilter.insert("hello");
        bloomFilter.insert("world");

        Assertions.assertTrue(bloomFilter.mightContain("hello"));
        Assertions.assertTrue(bloomFilter.mightContain("world"));
        Assertions.assertFalse(bloomFilter.mightContain("java"));
    }

    @Test
    void testDifferentTypes() {
        BloomFilter<Object> filter = new BloomFilter<>(3, 100);
        filter.insert("string");
        filter.insert(123);
        filter.insert(45.67);

        Assertions.assertTrue(filter.mightContain("string"), "Filter should contain the string 'string'");
        Assertions.assertTrue(filter.mightContain(123), "Filter should contain the integer 123");
        Assertions.assertTrue(filter.mightContain(45.67), "Filter should contain the double 45.67");
        Assertions.assertFalse(filter.mightContain("missing"), "Filter should not contain elements that were not inserted");
    }


}
