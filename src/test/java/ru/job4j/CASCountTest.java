package ru.job4j;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;

public class CASCountTest {

    @Test
    public void when2ThreadsUseIncrementOperation5TimesEachThanCountGet10()
            throws InterruptedException {
        CASCount casCount = new CASCount(0);
        Thread thread1 = new Thread(
                () -> {
                    for (int i = 0; i < 5; i++) {
                        casCount.increment();
                    }
                }
        );
        Thread thread2 = new Thread(
                () -> {
                    for (int i = 0; i < 5; i++) {
                        casCount.increment();
                    }
                }
        );
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        assertThat(casCount.get(), is(10));
    }

}