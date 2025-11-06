package pers.zkx.algo.leetcode.mid;

import java.util.*;

/**
 * 380
 *
 * @author: zhangkuixing
 * @date: 2025/7/17 00:06
 */
public class RandomizedSet {
    private List<Integer> nums;
    private Map<Integer, Integer> valToIndex;
    private Random rand;

    public RandomizedSet() {
        nums = new ArrayList<>();
        valToIndex = new HashMap<>();
        rand = new Random();
    }

    public boolean insert(int val) {
        if (valToIndex.containsKey(val)) {
            return false;
        }
        nums.add(val);
        valToIndex.put(val, nums.size() - 1);
        return true;
    }

    public boolean remove(int val) {
        if (!valToIndex.containsKey(val)) {
            return false;
        }
        int idx = valToIndex.get(val);
        int last = nums.getLast();
        nums.set(idx, last);
        valToIndex.put(last, idx);
        nums.removeLast();
        valToIndex.remove(val);
        return true;
    }

    public int getRandom() {
        return nums.get(rand.nextInt(nums.size()));
    }
}
