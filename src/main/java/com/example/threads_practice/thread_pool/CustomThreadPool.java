package com.example.threads_practice.thread_pool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.DefaultManagedAwareThreadFactory;

import java.util.concurrent.*;


public class CustomThreadPool {
    private static ThreadFactory threadFactory = new DefaultManagedAwareThreadFactory();
    private static ThreadFactory customThreadFactory = new CustomThreadFactory();

    private static int corePoolSize = 3;
    private static int maximumPoolSize = 5;
    private static long keepAliveTime = 5;
    private static TimeUnit timeUnit = TimeUnit.MINUTES;

    private static int poolSize = 3;
    private static BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(poolSize);

    private static RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();
    private static RejectedExecutionHandler customHandler = new CustomRejectionHandler();

    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            corePoolSize,
            maximumPoolSize,
            keepAliveTime,
            timeUnit,
            workQueue,
            threadFactory,
            customHandler
    );


    public static ThreadPoolExecutor getThreadPool() {
        return threadPoolExecutor;
    }

}

class CustomThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(Runnable r) {
        Thread th = newThread(r);
        th.setName("Custom Thread");
        th.setPriority(Thread.NORM_PRIORITY);
        return th;
    }
}

@Slf4j
class CustomRejectionHandler implements RejectedExecutionHandler {

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        log.error("Rejected Task!!! Details: {}", r.toString());
    }
}
