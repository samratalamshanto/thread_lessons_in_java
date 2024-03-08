package com.example.threads_practice;

import com.example.threads_practice.thread_pool.CustomThreadPool;
import com.example.threads_practice.threads.TheadExtendingThreadClass;
import com.example.threads_practice.threads.ThreadImplementingRunnable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

@SpringBootApplication
public class ThreadsPracticeApplication {

	public static void main(String[] args) throws ExecutionException, InterruptedException {
		SpringApplication.run(ThreadsPracticeApplication.class, args);
		practiceThreadPoolExecutor();
		practiceThread();
	}

	public static void practiceThreadPoolExecutor() throws ExecutionException, InterruptedException {
		ThreadPoolExecutor threadPoolExecutor = CustomThreadPool.getThreadPool();
		threadPoolExecutor.allowCoreThreadTimeOut(true);

		for (int i = 1; i < 10; i++) {
			Future<?> future = threadPoolExecutor.submit(() -> {
				//do something
				System.out.println("Inside Thread Submit()->" + Thread.currentThread().getName());

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
				System.out.println("End Execution!!!");
				return "hello";
			});
			System.out.println(future.get());  // get Future object
			future.cancel(true); //interrupt running pool
		}
		threadPoolExecutor.shutdown();
	}

	public static void practiceThread() throws InterruptedException {
		Thread thread1 = new TheadExtendingThreadClass();
		thread1.start();

		Thread thread2 = new Thread(new ThreadImplementingRunnable());
		thread2.start();

		Thread thread3 = new Thread(() -> {
			for (int i = 0; i < 10; i++) {
				System.out.println("On the fly, runnable lambda function call!!! Thread Running-" + i);
			}
		});
		thread3.start();

		thread1.join();
		thread2.join();
		thread3.join();
	}
}
