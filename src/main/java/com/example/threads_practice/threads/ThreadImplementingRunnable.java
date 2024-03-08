package com.example.threads_practice.threads;

public class ThreadImplementingRunnable implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("Implementing Runnable Interface!!! Thread Running-" + i);
        }
    }
}
