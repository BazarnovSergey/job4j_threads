package ru.job4j;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {

    private final AtomicInteger count = new AtomicInteger();

    public CASCount(int capacity) {
        count.set(capacity);
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