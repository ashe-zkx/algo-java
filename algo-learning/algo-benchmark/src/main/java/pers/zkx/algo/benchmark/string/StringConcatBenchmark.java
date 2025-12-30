package pers.zkx.algo.benchmark.string;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

/**
 * 简单微基准：比较多种构造 id 的实现方式（演示用途，非 JMH）
 */
public class StringConcatBenchmark {
    public static void main(String[] args) {
        final int size = 1_000_000;          // 样本数量，可根据机器调整
        final int warmups = 2;
        final int rounds = 5;

        List<Item> items = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            String f1 = "key" + (i % 1000);
            int f2 = ThreadLocalRandom.current().nextInt(0, 1_000_000);
            items.add(new Item(f1, f2));
        }

        Consumer<Item> formatConsumer = Item::buildIdByFormat;
        Consumer<Item> plusConsumer = Item::buildIdByPlus;
        Consumer<Item> builderConsumer = Item::buildIdByBuilder;

        System.out.println("开始基准：size=" + size);

        runBenchmark("String.format", formatConsumer, items, warmups, rounds);
        runBenchmark("Plus (+)", plusConsumer, items, warmups, rounds);
        runBenchmark("StringBuilder", builderConsumer, items, warmups, rounds);
        runBenchmarkStream("Stream +", items, warmups, rounds);
    }

    private static void runBenchmark(String name, Consumer<Item> setter, List<Item> items, int warmups, int rounds) {
        for (int i = 0; i < warmups; i++) {
            for (Item it : items) {
                setter.accept(it);
            }
        }

        long[] times = new long[rounds];
        Runtime runtime = Runtime.getRuntime();

        for (int r = 0; r < rounds; r++) {
            System.gc();

            long beforeUsed = runtime.totalMemory() - runtime.freeMemory();
            long start = System.nanoTime();
            for (Item it : items) {
                setter.accept(it);
            }
            long durationNs = System.nanoTime() - start;
            times[r] = durationNs;

            long afterUsed = runtime.totalMemory() - runtime.freeMemory();
            double usedMb = (afterUsed - beforeUsed) / (1024.0 * 1024.0);

            System.out.printf(Locale.ENGLISH, "%s - round %d: %.3f ms, mem delta: %.3f MB%n", name, r + 1, durationNs / 1_000_000.0, usedMb);
        }

        double avgMs = Arrays.stream(times).average().orElse(0) / 1_000_000.0;
        System.out.printf(Locale.ENGLISH, "%s - avg: %.3f ms%n%n", name, avgMs);
    }

    private static void runBenchmarkStream(String name, List<Item> items, int warmups, int rounds) {
        for (int i = 0; i < warmups; i++) {
            items.stream().forEach(it -> it.setId(it.getF1() + "-" + it.getF2()));
        }

        long[] times = new long[rounds];
        Runtime runtime = Runtime.getRuntime();

        for (int r = 0; r < rounds; r++) {
            System.gc();

            long beforeUsed = runtime.totalMemory() - runtime.freeMemory();
            long start = System.nanoTime();
            items.stream().forEach(it -> it.setId(it.getF1() + "-" + it.getF2()));
            long durationNs = System.nanoTime() - start;
            times[r] = durationNs;

            long afterUsed = runtime.totalMemory() - runtime.freeMemory();
            double usedMb = (afterUsed - beforeUsed) / (1024.0 * 1024.0);

            System.out.printf(Locale.ENGLISH, "%s - round %d: %.3f ms, mem delta: %.3f MB%n", name, r + 1, durationNs / 1_000_000.0, usedMb);
        }

        double avgMs = Arrays.stream(times).average().orElse(0) / 1_000_000.0;
        System.out.printf(Locale.ENGLISH, "%s - avg: %.3f ms%n%n", name, avgMs);
    }
}

