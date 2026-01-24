package pers.zkx.algo.thread.pool;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 高级线程池实现
 * 特点：支持 Future 返回值、多种拒绝策略、自定义线程工厂、完整的生命周期管理
 */
public class AdvancedThreadPool implements ExecutorService {

    // ==================== 状态常量 ====================
    private static final int RUNNING    = 0;
    private static final int SHUTDOWN   = 1;
    private static final int STOP       = 2;
    private static final int TERMINATED = 3;

    // ==================== 核心参数 ====================
    private volatile int state = RUNNING;
    private final int corePoolSize;
    private final int maxPoolSize;
    private final long keepAliveTime;
    private final TimeUnit timeUnit;
    private final BlockingQueue<Runnable> taskQueue;
    private final ThreadFactory threadFactory;
    private final RejectedExecutionHandler rejectedHandler;

    private final Set<Worker> workers = new HashSet<>();
    private final AtomicInteger workerCount = new AtomicInteger(0);
    private final ReentrantLock mainLock = new ReentrantLock();
    private final Condition termination = mainLock.newCondition();
    private long completedTaskCount = 0;

    // ==================== 构造函数 ====================
    public AdvancedThreadPool(int corePoolSize, int maxPoolSize,
                              long keepAliveTime, TimeUnit timeUnit,
                              BlockingQueue<Runnable> taskQueue,
                              ThreadFactory threadFactory,
                              RejectedExecutionHandler rejectedHandler) {
        if (corePoolSize < 0 || maxPoolSize <= 0 ||
            maxPoolSize < corePoolSize || keepAliveTime < 0) {
            throw new IllegalArgumentException();
        }
        this.corePoolSize = corePoolSize;
        this. maxPoolSize = maxPoolSize;
        this.keepAliveTime = keepAliveTime;
        this.timeUnit = timeUnit;
        this.taskQueue = taskQueue;
        this.threadFactory = threadFactory != null ?  threadFactory : new DefaultThreadFactory();
        this.rejectedHandler = rejectedHandler != null ? rejectedHandler : new AbortPolicy();
    }

    // ==================== 提交任务 ====================
    @Override
    public void execute(Runnable command) {
        if (command == null) throw new NullPointerException();

        if (state >= SHUTDOWN) {
            reject(command);
            return;
        }

        int count = workerCount.get();

        // 1. 小于核心线程数，直接创建线程
        if (count < corePoolSize) {
            if (addWorker(command, true)) return;
        }

        // 2. 尝试入队
        if (state == RUNNING && taskQueue.offer(command)) {
            // 双重检查
            if (state >= SHUTDOWN && taskQueue.remove(command)) {
                reject(command);
            } else if (workerCount.get() == 0) {
                addWorker(null, false);
            }
            return;
        }

        // 3. 尝试创建非核心线程
        if (!addWorker(command, false)) {
            reject(command);
        }
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        if (task == null) throw new NullPointerException();
        RunnableFuture<T> futureTask = new FutureTask<>(task);
        execute(futureTask);
        return futureTask;
    }

    @Override
    public Future<?> submit(Runnable task) {
        if (task == null) throw new NullPointerException();
        RunnableFuture<Void> futureTask = new FutureTask<>(task, null);
        execute(futureTask);
        return futureTask;
    }

    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        if (task == null) throw new NullPointerException();
        RunnableFuture<T> futureTask = new FutureTask<>(task, result);
        execute(futureTask);
        return futureTask;
    }

    // ==================== 添加工作线程 ====================
    private boolean addWorker(Runnable firstTask, boolean core) {
        retry:
        while (true) {
            if (state >= SHUTDOWN && !(state == SHUTDOWN && firstTask == null && ! taskQueue.isEmpty())) {
                return false;
            }

            while (true) {
                int count = workerCount.get();
                int max = core ? corePoolSize : maxPoolSize;
                if (count >= max) return false;
                if (workerCount. compareAndSet(count, count + 1)) break retry;
            }
        }

        boolean workerStarted = false;
        Worker worker = null;

        try {
            worker = new Worker(firstTask);
            Thread t = worker.thread;
            if (t != null) {
                mainLock.lock();
                try {
                    if (state == RUNNING || (state == SHUTDOWN && firstTask == null)) {
                        if (t.isAlive()) throw new IllegalThreadStateException();
                        workers.add(worker);
                        t.start();
                        workerStarted = true;
                    }
                } finally {
                    mainLock.unlock();
                }
            }
        } finally {
            if (! workerStarted) {
                workerCount.decrementAndGet();
            }
        }
        return workerStarted;
    }

    // ==================== 获取任务 ====================
    private Runnable getTask() {
        boolean timedOut = false;

        while (true) {
            if (state >= STOP || (state >= SHUTDOWN && taskQueue.isEmpty())) {
                workerCount.decrementAndGet();
                return null;
            }

            int count = workerCount.get();
            boolean timed = count > corePoolSize;

            if ((count > maxPoolSize || (timed && timedOut)) &&
                (count > 1 || taskQueue.isEmpty())) {
                if (workerCount. compareAndSet(count, count - 1)) {
                    return null;
                }
                continue;
            }

            try {
                Runnable task = timed
                    ? taskQueue.poll(keepAliveTime, timeUnit)
                    :  taskQueue.take();
                if (task != null) return task;
                timedOut = true;
            } catch (InterruptedException e) {
                timedOut = false;
            }
        }
    }

    // ==================== 拒绝策略 ====================
    private void reject(Runnable command) {
        rejectedHandler.rejectedExecution(command, null);
    }

    // ==================== 生命周期管理 ====================
    @Override
    public void shutdown() {
        mainLock.lock();
        try {
            state = SHUTDOWN;
            interruptIdleWorkers();
        } finally {
            mainLock.unlock();
        }
        tryTerminate();
    }

    @Override
    public List<Runnable> shutdownNow() {
        List<Runnable> tasks;
        mainLock.lock();
        try {
            state = STOP;
            interruptWorkers();
            tasks = drainQueue();
        } finally {
            mainLock.unlock();
        }
        tryTerminate();
        return tasks;
    }

    private void interruptIdleWorkers() {
        for (Worker w : workers) {
            if (! w.thread.isInterrupted() && w.tryLock()) {
                try {
                    w.thread.interrupt();
                } finally {
                    w.unlock();
                }
            }
        }
    }

    private void interruptWorkers() {
        for (Worker w :  workers) {
            w.thread.interrupt();
        }
    }

    private List<Runnable> drainQueue() {
        List<Runnable> taskList = new ArrayList<>();
        taskQueue.drainTo(taskList);
        return taskList;
    }

    private void tryTerminate() {
        mainLock.lock();
        try {
            if (state >= SHUTDOWN && workers.isEmpty() && taskQueue.isEmpty()) {
                state = TERMINATED;
                termination.signalAll();
            }
        } finally {
            mainLock.unlock();
        }
    }

    @Override
    public boolean isShutdown() {
        return state >= SHUTDOWN;
    }

    @Override
    public boolean isTerminated() {
        return state == TERMINATED;
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        long nanos = unit.toNanos(timeout);
        mainLock.lock();
        try {
            while (state != TERMINATED) {
                if (nanos <= 0) return false;
                nanos = termination. awaitNanos(nanos);
            }
            return true;
        } finally {
            mainLock.unlock();
        }
    }

    // ==================== 统计信息 ====================
    public int getPoolSize() {
        return workerCount. get();
    }

    public int getQueueSize() {
        return taskQueue. size();
    }

    public long getCompletedTaskCount() {
        mainLock.lock();
        try {
            long count = completedTaskCount;
            for (Worker w : workers) {
                count += w.completedTasks;
            }
            return count;
        } finally {
            mainLock.unlock();
        }
    }

    // ==================== Worker 内部类 ====================
    private class Worker extends ReentrantLock implements Runnable {
        final Thread thread;
        Runnable firstTask;
        long completedTasks = 0;

        Worker(Runnable firstTask) {
            this.firstTask = firstTask;
            this.thread = threadFactory.newThread(this);
        }

        @Override
        public void run() {
            Runnable task = firstTask;
            firstTask = null;

            try {
                while (task != null || (task = getTask()) != null) {
                    lock();
                    try {
                        // 可在此添加 beforeExecute 钩子
                        try {
                            task.run();
                            // 可在此添加 afterExecute 钩子
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } finally {
                        task = null;
                        completedTasks++;
                        unlock();
                    }
                }
            } finally {
                mainLock.lock();
                try {
                    completedTaskCount += completedTasks;
                    workers.remove(this);
                } finally {
                    mainLock. unlock();
                }
                tryTerminate();
            }
        }
    }

    // ==================== 默认线程工厂 ====================
    static class DefaultThreadFactory implements ThreadFactory {
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix = "pool-thread-";

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r, namePrefix + threadNumber.getAndIncrement());
            t.setDaemon(false);
            return t;
        }
    }

    // ==================== 拒绝策略实现 ====================
    /** 直接抛出异常 */
    public static class AbortPolicy implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            throw new RejectedExecutionException("Task " + r + " rejected");
        }
    }

    /** 由调用线程执行 */
    public static class CallerRunsPolicy implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            r.run();
        }
    }

    /** 静默丢弃 */
    public static class DiscardPolicy implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            // 什么都不做
        }
    }

    /** 丢弃队列最老的任务 */
    public static class DiscardOldestPolicy implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            if (executor != null && ! executor.isShutdown()) {
                executor.getQueue().poll();
                executor.execute(r);
            }
        }
    }

    // ==================== 未实现的方法 ====================
    @Override
    public <T> List<Future<T>> invokeAll(Collection<?  extends Callable<T>> tasks) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T invokeAny(Collection<?  extends Callable<T>> tasks, long timeout, TimeUnit unit) {
        throw new UnsupportedOperationException();
    }

    // ==================== 测试 ====================
    public static void main(String[] args) throws Exception {
        AdvancedThreadPool pool = new AdvancedThreadPool(
            2, 4,
            30, TimeUnit. SECONDS,
            new LinkedBlockingQueue<>(10),
            null,
            new CallerRunsPolicy()  // 使用调用者运行策略
        );

        // 提交带返回值的任务
        List<Future<Integer>> futures = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            final int taskId = i;
            Future<Integer> future = pool.submit(() -> {
                System. out.println("任务 " + taskId + " 执行中, 线程: " + Thread.currentThread().getName());
                Thread.sleep(1000);
                return taskId * taskId;
            });
            futures.add(future);
        }

        // 获取结果
        for (int i = 0; i < futures.size(); i++) {
            System. out.println("任务 " + i + " 结果: " + futures. get(i).get());
        }

        System.out.println("完成任务数: " + pool. getCompletedTaskCount());

        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.MINUTES);
        System.out.println("线程池已终止");
    }
}
