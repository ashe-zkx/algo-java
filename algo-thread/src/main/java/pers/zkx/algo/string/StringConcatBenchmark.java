package pers.zkx.algo.string;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

/**
 * 简单微基准：比较多种构造 id 的实现方式
 * <p>
 * 用法:
 * javac bench/*.java
 * java bench.StringConcatBenchmark [listSize] [avgFieldLen] [runs]
 * <p>
 * 例如:
 * java bench.StringConcatBenchmark 200000 8 5
 */
public class StringConcatBenchmark {

    public static void main(String[] args) throws Exception {
        int listSize = args.length > 0 ? Integer.parseInt(args[0]) : 200_000;
        int avgFieldLen = args.length > 1 ? Integer.parseInt(args[1]) : 8;
        int runs = args.length > 2 ? Integer.parseInt(args[2]) : 5;

        System.out.println("List size: " + listSize + ", avgFieldLen: " + avgFieldLen + ", runs: " + runs);

        List<Item> list = generateList(listSize, avgFieldLen);

        // 五种实现
        Map<String, Consumer<List<Item>>> implementations = new LinkedHashMap<>();
        implementations.put("plus", StringConcatBenchmark::testPlus); // f1 + "_" + f2 + "_" + f3
        implementations.put("newStringBuilder", StringConcatBenchmark::testNewStringBuilder); // 每次 new
        implementations.put("reuseStringBuilder", StringConcatBenchmark::testReuseStringBuilder); // 循环外复用
        implementations.put("threadLocalStringBuilder", StringConcatBenchmark::testThreadLocalStringBuilder); // ThreadLocal（单线程与复用）
        implementations.put("cacheMap", StringConcatBenchmark::testCacheMap); // 用缓存减少重复计算

        // warmup
        System.out.println("Warming up...");
        for (String name : implementations.keySet()) {
            Consumer<List<Item>> impl = implementations.get(name);
            impl.accept(cloneList(list)); // 快速运行一次
        }

        System.out.println("Start tests:");
        for (Map.Entry<String, Consumer<List<Item>>> e : implementations.entrySet()) {
            String name = e.getKey();
            Consumer<List<Item>> impl = e.getValue();

            long total = 0;
            for (int r = 0; r < runs; r++) {
                List<Item> lst = cloneList(list);
                System.gc();
                Thread.sleep(50);

                long t0 = System.nanoTime();
                impl.accept(lst);
                long t1 = System.nanoTime();
                long ms = (t1 - t0) / 1_000_000;
                total += ms;
                System.out.printf("  %s run %d: %d ms%n", name, r + 1, ms);

                // 简单校验，防止被 JIT 优化掉，打印第一个元素 id 的哈希
                if (!lst.isEmpty()) {
                    System.out.printf("    sample id hash: %d%n", lst.get(0).getId().hashCode());
                }
            }
            System.out.printf("=> %s avg: %.2f ms%n%n", name, total / (double) runs);
        }
    }

    // Implementation 1: plus
    public static void testPlus(List<Item> list) {
        for (Item it : list) {
            it.setId(it.getF1() + "_" + it.getF2() + "_" + it.getF3());
        }
    }

    // Implementation 2: new StringBuilder each time
    public static void testNewStringBuilder(List<Item> list) {
        for (Item it : list) {
            StringBuilder sb = new StringBuilder();
            sb.append(it.getF1()).append('_').append(it.getF2()).append('_').append(it.getF3());
            it.setId(sb.toString());
        }
    }

    // Implementation 3: reuse one StringBuilder (single-thread)
    public static void testReuseStringBuilder(List<Item> list) {
        // 预估容量：平均长度 * 3 + 2；如果你能估算更精确可设置更大，减少扩容
        int estimated = 32;
        if (!list.isEmpty()) {
            Item it0 = list.get(0);
            estimated = (it0.getF1().length() + it0.getF2().length() + it0.getF3().length() + 2);
            if (estimated < 32) estimated = 32;
        }
        StringBuilder sb = new StringBuilder(estimated);
        for (Item it : list) {
            sb.setLength(0);
            sb.append(it.getF1()).append('_').append(it.getF2()).append('_').append(it.getF3());
            it.setId(sb.toString());
        }
    }

    // Implementation 4: ThreadLocal StringBuilder (useful for multithreaded scenarios)
    private static final ThreadLocal<StringBuilder> TL_SB =
            ThreadLocal.withInitial(() -> new StringBuilder(64));

    public static void testThreadLocalStringBuilder(List<Item> list) {
        StringBuilder sb = TL_SB.get();
        for (Item it : list) {
            sb.setLength(0);
            sb.append(it.getF1()).append('_').append(it.getF2()).append('_').append(it.getF3());
            it.setId(sb.toString());
        }
    }

    // Implementation 5: cache repeated combinations (space for time)
    public static void testCacheMap(List<Item> list) {
        Map<String, String> cache = new HashMap<>();
        for (Item it : list) {
            // 使用不可见分隔符构建 map key（避免 '_' 带来的二义性）
            String key = it.getF1() + '\0' + it.getF2() + '\0' + it.getF3();
            String id = cache.computeIfAbsent(key, k -> it.getF1() + "_" + it.getF2() + "_" + it.getF3());
            it.setId(id);
        }
    }

    // ---- helpers ----
    private static List<Item> generateList(int n, int avgFieldLen) {
        List<Item> list = new ArrayList<>(n);
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        for (int i = 0; i < n; i++) {
            list.add(new Item(randomAlphaNum(rnd, avgFieldLen),
                    randomAlphaNum(rnd, avgFieldLen),
                    randomAlphaNum(rnd, avgFieldLen)));
        }
        return list;
    }

    private static List<Item> cloneList(List<Item> src) {
        List<Item> dst = new ArrayList<>(src.size());
        for (Item it : src) {
            dst.add(new Item(it.getF1(), it.getF2(), it.getF3()));
        }
        return dst;
    }

    private static String randomAlphaNum(Random rnd, int avgLen) {
        int len = Math.max(1, avgLen + rnd.nextInt(Math.max(1, avgLen / 2 + 1)) - avgLen / 4);
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            int t = rnd.nextInt(62);
            char c;
            if (t < 10) c = (char) ('0' + t);
            else if (t < 36) c = (char) ('A' + t - 10);
            else c = (char) ('a' + t - 36);
            sb.append(c);
        }
        return sb.toString();
    }
}
