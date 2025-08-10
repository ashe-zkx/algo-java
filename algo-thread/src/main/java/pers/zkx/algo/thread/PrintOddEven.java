package pers.zkx.algo.thread;

/**
 * @author: zhangkuixing
 * @date: 2025/7/27 22:46
 */
public class PrintOddEven {
    private static final Object lock = new Object();
    private static int count = 1;
    private static int maxCount = 10;


    public static void main(String[] args) {
        Runnable printOdd = () -> {
            while (count <= maxCount) {
                synchronized (lock) {
                    if (count % 2 != 0) {
                        System.out.println("Odd: " + count);
                        count++;
                        lock.notifyAll(); // 通知其他线程
                    } else {
                        try {
                            lock.wait(); // 等待偶数线程
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
            }
        };
        Runnable printEven = () -> {
            while (count <= maxCount) {
                synchronized (lock) {
                    if (count % 2 == 0) {
                        System.out.println("Even: " + count);
                        count++;
                        lock.notifyAll(); // 通知其他线程
                    } else {
                        try {
                            lock.wait(); // 等待奇数线程
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
            }
        };
        Thread oddThread = new Thread(printOdd);
        Thread evenThread = new Thread(printEven);
        oddThread.start();
        evenThread.start();
    }
}
