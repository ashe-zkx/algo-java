package pers.zkx.algo.thread;

/**
 * @author: zhangkuixing
 * @date: 2025/7/27 22:53
 */
public class SynchronizedAddition {
    private static final Object lock = new Object();
    private static int number = 0;

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 5; j++) {
                    synchronized (lock) {
                        number++; // 使用synchronized关键字确保线程安全
                    }
                }
            }).start();
        }
        // 等待所有线程完成
        try {
            Thread.sleep(2000); // 等待足够的时间让所有线程执行完毕
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Final value: " + number); // 输出最终值
    }
}
