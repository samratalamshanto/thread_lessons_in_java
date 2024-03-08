package com.example.threads_practice.synchronized_lock;

import java.util.concurrent.ExecutionException;

public class SynchronizedPractice {

    static Object lockObject = new Object();
    private static int count = 0, count1 = 0;

    private static void firstThreadOperation() throws InterruptedException {
        // lockObject.wait();
        synchronized (lockObject) {
            increment();
            lockObject.notifyAll();
        }


        incrementSyncMethod();
    }

    private static void secondThreadOperation() throws InterruptedException {
        synchronized (lockObject) {
            increment();
            lockObject.notifyAll();
        }

        incrementSyncMethod();
    }

    private static void increment() {
        count++;
    }

    private static synchronized void incrementSyncMethod() {
        count1++;
    }

    private static void printResult() {
        System.out.println(count); //2
        System.out.println(count1); //2
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
