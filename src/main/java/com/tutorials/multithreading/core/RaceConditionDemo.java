package com.tutorials.multithreading.core;

import java.util.ArrayList;
import java.util.List;

public class RaceConditionDemo {

    public static void main(String[] args) throws InterruptedException {
        System.out.printf("Main thread name: %s %n", Thread.currentThread().getName());

        var status = new DownloadStatus();
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new DownloadFileTask(status));
            thread.start();
            threads.add(thread);
        }

        for (var thread: threads) {
            thread.join();
        }

        System.out.printf("Total bytes downloaded: %d%n", status.getTotalBytes());
        System.out.printf("Total Atomic bytes downloaded: %d%n", status.getTotalBytesAtomic().get());
        System.out.printf("Total Adder bytes downloaded: %d%n", status.getTotalBytesAdder().intValue());
    }
}
