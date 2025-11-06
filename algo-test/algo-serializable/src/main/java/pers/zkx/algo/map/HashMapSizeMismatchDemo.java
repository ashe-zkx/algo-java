package pers.zkx.algo.map;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * HashMapSizeMismatchDemo
 * 演示 HashMap 在高并发写入时，size() 与实际元素个数不一致的问题。
 * 可选地，使用 ConcurrentHashMap 以对比其在相同场景下的表现。
 */
public class HashMapSizeMismatchDemo {

    public static void main(String[] args) throws Exception {
        // 参数: "concurrent" 使用 ConcurrentHashMap，否则使用 HashMap（更容易复现问题）
        boolean useConcurrent = args != null && args.length > 0 && "concurrent".equalsIgnoreCase(args[0]);

        final int writerThreads = Runtime.getRuntime().availableProcessors(); // 写线程数
        final int keySpace = 1 << 16; // 键空间大小
        final int durationSeconds = 20; // 运行时长
        final int payloadMax = 512; // 值字节数组最大长度

        final Map<Integer, byte[]> map = useConcurrent
                ? new ConcurrentHashMap<>()
                : new HashMap<>(); // 不做额外同步，演示问题

        // 新增：用于在“检查时暂停写入”的读写锁
        final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

        System.out.println("Using map type: " + map.getClass().getName());
        System.out.println("Writers: " + writerThreads + ", keySpace: " + keySpace + ", duration: " + durationSeconds + "s");

        final ExecutorService writers = Executors.newFixedThreadPool(writerThreads);
        final AtomicBoolean stop = new AtomicBoolean(false);
        final AtomicInteger anomalies = new AtomicInteger(0);
        final Random seedRandom = new Random();

        // 写线程：大量 put / occasional remove
        for (int i = 0; i < writerThreads; i++) {
            final int id = i;
            writers.submit(() -> {
                Random r = new Random(seedRandom.nextInt() ^ id);
                try {
                    while (!stop.get()) {
                        int key = r.nextInt(keySpace);
                        // 随机长度的 payload，增加内存与 rehash 概率
                        byte[] payload = new byte[r.nextInt(payloadMax) + 1];
                        r.nextBytes(payload);
                        try {
                            // 在写操作周围获取读锁，读线程在检查时会获取写锁从而阻止写入
                            rwLock.readLock().lock();
                            try {
                                map.put(key, payload);
                                if ((r.nextInt(100) == 0)) {
                                    // 少量 remove 操作制造更多变化
                                    map.remove(r.nextInt(keySpace));
                                }
                            } finally {
                                rwLock.readLock().unlock();
                            }
                        } catch (Throwable t) {
                            // 写入过程中可能抛出异常
                            System.err.println("Writer caught throwable: " + t);
                            anomalies.incrementAndGet();
                        }
                        // 少许让步，避免完全占满 CPU
                        if ((key & 0xFF) == 0) Thread.yield();
                    }
                } catch (Throwable t) {
                    System.err.println("Writer thread fatal: " + t);
                    anomalies.incrementAndGet();
                }
            });
        }

        // 读线程：定期比较 declared size 与遍历计数
        Thread reader = new Thread(() -> {
            long checks = 0;
            while (!stop.get()) {
                checks++;
                int declaredSize = -1;
                int counted = -1;
                try {
                    // 在检查前获取写锁，确保没有并发写入导致 ConcurrentModificationException
                    rwLock.writeLock().lock();
                    try {
                        declaredSize = map.size();
                        int c = 0;
                        // 通过 entrySet 遍历并计数
                        for (Map.Entry<Integer, byte[]> e : map.entrySet()) {
                            // 读取 key/value 以防被编译器优化
                            if (e.getKey() == null) {
                                // shouldn't happen but touch value
                                Object v = e.getValue();
                                if (v == null) {
                                }
                            }
                            c++;
                        }
                        counted = c;
                    } finally {
                        rwLock.writeLock().unlock();
                    }

                    if (declaredSize != counted) {
                        System.err.println("ANOMALY detected! declaredSize=" + declaredSize + " but iteratedCount=" + counted + " (check #" + checks + ")");
                        anomalies.incrementAndGet();
                        // 额外打印部分内容用于调试
                        System.err.println("Map class: " + map.getClass().getName());
                        // 打印少量样例键
                        int sample = 0;
                        StringBuilder sb = new StringBuilder("Sample keys:");
                        for (Integer k : map.keySet()) {
                            sb.append(' ').append(k);
                            if (++sample >= 10) break;
                        }
                        System.err.println(sb.toString());
                        // 立即停止演示
                        stop.set(true);
                        break;
                    }
                } catch (ConcurrentModificationException cme) {
                    System.err.println("ConcurrentModificationException during iteration (check #" + checks + "): " + cme);
                    anomalies.incrementAndGet();
                    stop.set(true);
                    break;
                } catch (Throwable t) {
                    System.err.println("Reader caught throwable: declaredSize=" + declaredSize + ", counted=" + counted + " -> " + t);
                    anomalies.incrementAndGet();
                    stop.set(true);
                    break;
                }
                // 每隔一段时间检查一次
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ignored) {
                }
            }
        }, "reader-thread");
        reader.setDaemon(true);
        reader.start();

        // 运行一段时间或直到发现异常
        long start = System.nanoTime();
        long endTime = start + TimeUnit.SECONDS.toNanos(durationSeconds);
        while (System.nanoTime() < endTime && !stop.get()) {
            Thread.sleep(200);
        }
        stop.set(true);
        writers.shutdownNow();
        writers.awaitTermination(2, TimeUnit.SECONDS);
        reader.join(500);

        System.out.println("Finished. anomalies=" + anomalies.get() + ", final declared size=" + map.size() + ", final iterated count:");
        int finalCount = 0;
        try {
            for (Map.Entry<Integer, byte[]> e : map.entrySet()) finalCount++;
        } catch (Throwable t) {
            System.err.println("Final iteration error: " + t);
        }
        System.out.println("  iterated count = " + finalCount);
        System.out.println("Done.");

    }

}
