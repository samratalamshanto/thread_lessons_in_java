package com.example.threads_practice.deadlock;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Deadlock {
    public static Lock lock1 = new ReentrantLock();
    public static Lock lock2 = new ReentrantLock();

    public static void firstThread() throws InterruptedException {
        System.out.println("In first Thread start....");
        lock1.lock();
        //do something
        Thread.sleep(1000);
        lock2.lock();

        lock2.unlock();
        lock1.unlock();

        System.out.println("In first Thread end....");
    }

    public static void secondThread() throws InterruptedException {
        System.out.println("In second Thread start....");
        lock2.lock();
        //do something
        Thread.sleep(1000);
        lock1.lock();

        lock1.unlock();
        lock2.unlock();

        System.out.println("In second Thread end....");
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        System.out.println("main start...");
        Thread t1 = new Thread(() -> {
            try {
                firstThread();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                secondThread();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        //if locks are same order in methods, then deadlock situation will not arise!!!

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }
}
