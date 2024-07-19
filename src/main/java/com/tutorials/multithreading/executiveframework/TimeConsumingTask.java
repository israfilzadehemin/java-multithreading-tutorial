package com.tutorials.multithreading.executiveframework;

public class TimeConsumingTask {

    public static void executeTask() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }





}
