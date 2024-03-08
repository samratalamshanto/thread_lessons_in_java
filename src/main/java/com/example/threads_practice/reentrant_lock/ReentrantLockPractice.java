package com.example.threads_practice.reentrant_lock;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockPractice {
    private static Lock lock = new ReentrantLock();
    private static int count = 0;
    private static Condition condition = lock.newCondition();

    private static void firstThreadOperation() throws InterruptedException {
        lock.lock();

        System.out.println("In wait state...");
        condition.await();  //in waiting state, so other threads get the lock object to use
        System.out.println("Wake up... and do operations...");
        try {
            increment();
        } finally {
            lock.unlock();
        }
    }

    private static void secondThreadOperation() throws InterruptedException {
        Thread.sleep(1000);
        lock.lock();
        System.out.println("Before Notify....");
        condition.signal();  //notify lock is free
        System.out.println("After Notify the other threads got lock object and can do operation....");
        try {
            increment();
        } finally {
            lock.unlock();
        }
    }

    private static void increment() {
        count++;
    }

    private static void printResult() {
        System.out.println(count);
    }


    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Thread t1 = new Thread(() -> {
            try {
                firstThreadOperation();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                secondThreadOperation();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        printResult();
    }
}
