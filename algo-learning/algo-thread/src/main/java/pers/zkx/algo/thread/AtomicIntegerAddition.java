package pers.zkx.algo.thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: zhangkuixing
 * @date: 2025/7/27 22:52
 */
public class AtomicIntegerAddition {
    private static AtomicInteger atomicInteger = new AtomicInteger(0);

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 5; j++) {
                    atomicInteger.getAndIncrement(); // 原子性地增加
                }
            }).start();
        }
        // 等待所有线程完成
        try {
            Thread.sleep(2000); // 等待足够的时间让所有线程执行完毕
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Final value: " + atomicInteger.get()); // 输出最终值
    }
}
