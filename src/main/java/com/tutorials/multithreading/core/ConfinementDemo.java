package com.tutorials.multithreading.core;

import java.util.ArrayList;
import java.util.List;

public class ConfinementDemo {

    public static void main(String[] args) throws InterruptedException {
        System.out.printf("Main thread name: %s %n", Thread.currentThread().getName());

        List<Thread> threads = new ArrayList<>();
        List<DownloadFileTask> tasks = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            var task = new DownloadFileTask();
            tasks.add(task);

            var thread = new Thread(task);
            thread.start();
            threads.add(thread);
        }

        for (var thread : threads) {
            thread.join();
        }

        Integer totalBytes = tasks
                .stream()
                .map(t -> t.getStatus().getTotalBytes())
                .reduce(Integer::sum)
                .get();

        System.out.printf("Total bytes downloaded: %d%n", totalBytes);
    }
}
