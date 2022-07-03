package ru.job4j;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public class CASCount {

    private final AtomicInteger count;

    public CASCount() {
        count = new AtomicInteger();
    }

    public void increment() {
        int expectedValue;
        do {
            expectedValue = count.get();
        } while (!count.compareAndSet(expectedValue, expectedValue + 1));
    }

    public int get() {
        return count.get();
    }

}