package com.example.threads_practice.threads;

public class TheadExtendingThreadClass extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("Extending Thread Class!!! Thread Running-" + i);
        }
    }
}
