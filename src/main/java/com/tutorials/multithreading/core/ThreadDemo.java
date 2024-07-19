package com.tutorials.multithreading.core;

public class ThreadDemo {

    public static void main(String[] args) throws InterruptedException {
        System.out.printf("Main thread name: %s %n", Thread.currentThread().getName());

        // Start a new thread
        Thread secondaryThread = new Thread(new DownloadFileTask(new DownloadStatus()));
        secondaryThread.start();

        Thread.sleep(3000);

        // Interrupt the secondary thread
        // It does not ensure that the secondary thread will stop.
        // It just sends interruption request to it. Thread itself decides whether to stop
        secondaryThread.interrupt();


        // Join the thread (Make the current thread wait for the newly created thread to finish)
        secondaryThread.join();
    }
}
