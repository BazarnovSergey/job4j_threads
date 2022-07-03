package ru.job4j;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {

    private final AtomicReference<Integer> count = new AtomicReference<>();
    final AtomicInteger current = new AtomicInteger(0);

    public CASCount(int value) {
        count.set(value);
    }

    public void increment() {
        if (count.get() == -1) {
            throw new UnsupportedOperationException("Count is not impl.");
        }
        do {
            current.set(count.get());
        } while (!count.compareAndSet(current.get(), current.incrementAndGet()));
    }

    public int get() {
        if (count.get() == -1) {
            throw new UnsupportedOperationException("Count is not impl.");
        }
        return count.get();
    }

}