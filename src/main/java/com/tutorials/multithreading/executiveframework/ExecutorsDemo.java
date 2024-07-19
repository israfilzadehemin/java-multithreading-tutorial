package com.tutorials.multithreading.executiveframework;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorsDemo {

    public static void main(String[] args) {

        var threadPoolExecutor = Executors.newFixedThreadPool(2);

        try {
            Future<Integer> future = threadPoolExecutor.submit(() -> {
                TimeConsumingTask.executeTask();
                return 1;
            });

            System.out.println("Ongoing operation...");

            try {
                Integer result = future.get();
                System.out.printf("Result: %s%n", result);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }


        } finally {
            threadPoolExecutor.shutdown();
        }

    }
}