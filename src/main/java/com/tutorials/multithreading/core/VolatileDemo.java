package com.tutorials.multithreading.core;

public class VolatileDemo {

    public static void main(String[] args) throws InterruptedException {
        System.out.printf("Main thread name: %s %n", Thread.currentThread().getName());

        var status = new DownloadStatus();

        Thread thread1 = new Thread(new DownloadFileTask(status));
        Thread thread2 = new Thread(() -> {
            while (!status.isDone()) {

                synchronized (status) {
                    try {

                        // makes the thread wait until notified
                        status.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
            System.out.printf("Thread 2 completed with bytes: %s %n", status.getTotalBytes());
        });

        thread1.start();
        thread2.start();
    }
}
