package com.tutorials.multithreading.core;

public class DownloadFileTask implements Runnable {

    private DownloadStatus status;

    public DownloadFileTask(DownloadStatus downloadStatus) {
        this.status = downloadStatus;
    }

    public DownloadFileTask() {
        this.status = new DownloadStatus();
    }

    @Override
    public void run() {
        System.out.printf("Downloading a file... [Thread name: %s] %n", Thread.currentThread().getName());

        for (int i = 0; i < 1_000_000; i++) {

            if (Thread.currentThread().isInterrupted()) {
                System.out.println("Thread interrupted");
                return;
            }

            // use case 1:
            // status increments anc causes race condition
            status.incrementTotalBytes();


            // use case 2:
            // status increments using lock to avoid race condition
            // status.incrementTotalBytesLocked();

            // use case 3:
            // status increments using synchronized keyword to avoid race condition
            // status.incrementTotalBytesSynchronized();

            // use case 4:
            // status increments using synchronized keyword with explicit lock object to avoid race condition
            // status.incrementTotalBytesSynchronized2();

            // use case 5:
            // status increments using AtomicInteger to avoid race condition
            // status.incrementTotalBytesAtomic();

            // use case 6:
            // status increments using LongAdder to avoid race condition
            // status.incrementTotalBytesAdder();

        }

        status.setDone();

        synchronized (status) {
            // notify waiting threads
            status.notifyAll();
        }

        System.out.printf("DOWNLOADING COMPLETED IN THREAD: %s %n", Thread.currentThread().getName());
    }

    public DownloadStatus getStatus() {
        return status;
    }
}
