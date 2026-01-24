package pers.zkx.algo.thread.pool;

import java. util.HashSet;
import java.util.Set;
import java. util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java. util.concurrent.TimeUnit;
import java.util. concurrent.atomic.AtomicInteger;
import java.util. concurrent.locks.ReentrantLock;

/**
 * 可扩展线程池
 * 特点：支持核心线程数、最大线程数、空闲超时回收
 */
public class ScalableThreadPool {
    private final int corePoolSize;      // 核心线程数
    private final int maxPoolSize;        // 最大线程数
    private final long keepAliveTime;     // 空闲线程存活时间
    private final TimeUnit timeUnit;
    private final BlockingQueue<Runnable> taskQueue;

    private final Set<Worker> workers = new HashSet<>();
    private final AtomicInteger workerCount = new AtomicInteger(0);
    private final ReentrantLock mainLock = new ReentrantLock();
    private volatile boolean isShutdown = false;

    public ScalableThreadPool(int corePoolSize, int maxPoolSize,
                              long keepAliveTime, TimeUnit timeUnit,
                              int queueCapacity) {
        this.corePoolSize = corePoolSize;
        this.maxPoolSize = maxPoolSize;
        this. keepAliveTime = keepAliveTime;
        this.timeUnit = timeUnit;
        this.taskQueue = new LinkedBlockingQueue<>(queueCapacity);
    }

    /**
     * 提交任务
     */
    public void execute(Runnable task) {
        if (task == null) throw new NullPointerException();
        if (isShutdown) throw new IllegalStateException("线程池已关闭");

        int currentCount = workerCount.get();

        // 1. 如果当前线程数小于核心线程数，创建新线程
        if (currentCount < corePoolSize) {
            if (addWorker(task, true)) {
                return;
            }
        }

        // 2. 尝试将任务加入队列
        if (taskQueue.offer(task)) {
            // 确保至少有一个工作线程
            if (workerCount.get() == 0) {
                addWorker(null, false);
            }
            return;
        }

        // 3. 队列满了，尝试创建非核心线程
        if (! addWorker(task, false)) {
            // 4. 无法创建，执行拒绝策略
            reject(task);
        }
    }

    /**
     * 添加工作线程
     */
    private boolean addWorker(Runnable firstTask, boolean core) {
        int max = core ? corePoolSize : maxPoolSize;

        while (true) {
            int count = workerCount. get();
            if (count >= max) return false;
            if (workerCount. compareAndSet(count, count + 1)) break;
        }

        Worker worker = new Worker(firstTask);
        mainLock.lock();
        try {
            workers.add(worker);
            worker.thread.start();
            return true;
        } finally {
            mainLock.unlock();
        }
    }

    /**
     * 获取任务
     */
    private Runnable getTask() {
        boolean timedOut = false;

        while (true) {
            if (isShutdown && taskQueue.isEmpty()) {
                workerCount.decrementAndGet();
                return null;
            }

            int count = workerCount.get();
            boolean timed = count > corePoolSize; // 非核心线程需要超时

            if ((count > maxPoolSize || (timed && timedOut)) && count > 1) {
                if (workerCount.compareAndSet(count, count - 1)) {
                    return null;
                }
                continue;
            }

            try {
                Runnable task = timed
                    ? taskQueue.poll(keepAliveTime, timeUnit)
                    : taskQueue.take();

                if (task != null) return task;
                timedOut = true;
            } catch (InterruptedException e) {
                timedOut = false;
            }
        }
    }

    /**
     * 拒绝策略
     */
    private void reject(Runnable task) {
        throw new RuntimeException("任务被拒绝:  " + task. toString());
    }

    /**
     * 关闭线程池
     */
    public void shutdown() {
        mainLock.lock();
        try {
            isShutdown = true;
            for (Worker worker : workers) {
                worker.thread.interrupt();
            }
        } finally {
            mainLock.unlock();
        }
    }

    /**
     * 获取当前工作线程数
     */
    public int getWorkerCount() {
        return workerCount.get();
    }

    /**
     * 工作线程类
     */
    private class Worker implements Runnable {
        final Thread thread;
        Runnable firstTask;

        Worker(Runnable firstTask) {
            this.firstTask = firstTask;
            this.thread = new Thread(this);
        }

        @Override
        public void run() {
            Runnable task = firstTask;
            firstTask = null;

            try {
                while (task != null || (task = getTask()) != null) {
                    try {
                        task.run();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        task = null;
                    }
                }
            } finally {
                mainLock.lock();
                try {
                    workers.remove(this);
                } finally {
                    mainLock. unlock();
                }
            }
        }
    }

    // 测试
    public static void main(String[] args) throws InterruptedException {
        ScalableThreadPool pool = new ScalableThreadPool(
            2,                    // 核心线程数
            5,                    // 最大线程数
            10, TimeUnit.SECONDS, // 空闲超时
            5                     // 队列容量
        );

        for (int i = 0; i < 15; i++) {
            final int taskId = i;
            try {
                pool. execute(() -> {
                    System.out.println("任务 " + taskId + " 开始, 线程:  " +
                        Thread.currentThread().getName() + ", 当前线程数: " + pool.getWorkerCount());
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    System.out.println("任务 " + taskId + " 完成");
                });
            } catch (Exception e) {
                System.out.println("任务 " + taskId + " 被拒绝");
            }
        }

        Thread. sleep(15000);
        System.out.println("等待后线程数:  " + pool.getWorkerCount());
        pool.shutdown();
    }
}
