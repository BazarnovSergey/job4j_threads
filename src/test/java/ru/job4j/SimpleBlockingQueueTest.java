package ru.job4j;

import static org.hamcrest.Matchers.is;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class SimpleBlockingQueueTest {

    @Test
    public void whenFullList() throws InterruptedException {
        SimpleBlockingQueue<Integer> sbq = new SimpleBlockingQueue<>(5);
        List<Integer> list = new ArrayList<>();
        Thread thread1 = new Thread(
                () -> {
                    for (int i = 1; i <= 5; i++) {
                        sbq.offer(i);
                    }
                }

        );
        Thread thread2 = new Thread(
                () -> {
                    for (int i = 1; i <= 5; i++) {
                        list.addAll(Collections.singleton((Integer) sbq.poll()));
                    }
                }
        );
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        assertThat(list, is(List.of(1, 2, 3, 4, 5)));
    }

}