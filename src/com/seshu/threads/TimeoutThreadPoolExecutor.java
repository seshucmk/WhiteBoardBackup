package com.seshu.threads;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class TimeoutThreadPoolExecutor extends ThreadPoolExecutor {
    private final long timeout;
    private final TimeUnit timeoutUnit;

    private final ScheduledExecutorService timeoutExecutor = Executors.newSingleThreadScheduledExecutor();
    private final ConcurrentMap<Runnable, ScheduledFuture> runningTasks = new ConcurrentHashMap<Runnable, ScheduledFuture>();

    public TimeoutThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, long timeout, TimeUnit timeoutUnit) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        this.timeout = timeout;
        this.timeoutUnit = timeoutUnit;
    }

    public TimeoutThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, long timeout, TimeUnit timeoutUnit) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
        this.timeout = timeout;
        this.timeoutUnit = timeoutUnit;
    }

    public TimeoutThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler, long timeout, TimeUnit timeoutUnit) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
        this.timeout = timeout;
        this.timeoutUnit = timeoutUnit;
    }

    public TimeoutThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler, long timeout, TimeUnit timeoutUnit) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        this.timeout = timeout;
        this.timeoutUnit = timeoutUnit;
    }

    @Override
    public void shutdown() {
        timeoutExecutor.shutdown();
        super.shutdown();
    }

    @Override
    public List<Runnable> shutdownNow() {
        timeoutExecutor.shutdownNow();
        return super.shutdownNow();
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        if(timeout > 0) {
            final ScheduledFuture<?> scheduled = timeoutExecutor.schedule(new TimeoutTask(t), timeout, timeoutUnit);
            runningTasks.put(r, scheduled);
        }
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        ScheduledFuture timeoutTask = runningTasks.remove(r);
        if(timeoutTask != null) {
            timeoutTask.cancel(false);
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = new TimeoutThreadPoolExecutor(1, 1, 10, TimeUnit.SECONDS, 
                new LinkedBlockingQueue<Runnable>(), 10, TimeUnit.MINUTES);
        //ExecutorService service = Executors.newFixedThreadPool(1);
        try {
            final AtomicInteger counter = new AtomicInteger();
            for (long i = 0; i < 10000000; i++) {
                service.submit(new Runnable() {
                    @Override
                    public void run() {
                        counter.incrementAndGet();
                    }
                });
                if (i % 10000 == 0) {
                    System.out.println(i + "/" + counter.get());
                    while (i > counter.get()) {
                        Thread.sleep(10);
                    }
                }
            }
        } finally {
            service.shutdown();
        }
    }

    class TimeoutTask implements Runnable {
        private final Thread thread;

        public TimeoutTask(Thread thread) {
            this.thread = thread;
        }

        @Override
        public void run() {
            thread.interrupt();
        }
    }
}