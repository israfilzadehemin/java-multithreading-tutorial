package com.tutorials.multithreading.core;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DownloadStatus {
    private final Object totalBytesLock = new Object();
    private int totalBytes;

    private AtomicInteger totalBytesAtomic = new AtomicInteger();

    private LongAdder totalBytesAdder = new LongAdder();
    private volatile boolean isDone;

    private Lock lock = new ReentrantLock();

    public int getTotalBytes() {
        return totalBytes;
    }

    public void incrementTotalBytes() {
        totalBytes++;
    }

    public void incrementTotalBytesLocked() {
        lock.lock();

        try {
            incrementTotalBytes();
        } finally {
            lock.unlock();
        }
    }

    public void incrementTotalBytesSynchronized() {
        synchronized (totalBytesLock) {
            incrementTotalBytes();
        }

    }

    public synchronized void incrementTotalBytesSynchronized2() {
            incrementTotalBytes();
    }

    public void incrementTotalBytesAtomic() {
        totalBytesAtomic.incrementAndGet();
    }

    public void incrementTotalBytesAdder() {
        totalBytesAdder.increment();
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone() {
        isDone = true;
    }

    public AtomicInteger getTotalBytesAtomic() {
        return totalBytesAtomic;
    }

    public void setTotalBytesAtomic(AtomicInteger totalBytesAtomic) {
        this.totalBytesAtomic = totalBytesAtomic;
    }

    public LongAdder getTotalBytesAdder() {
        return totalBytesAdder;
    }

    public void setTotalBytesAdder(LongAdder totalBytesAdder) {
        this.totalBytesAdder = totalBytesAdder;
    }
}
