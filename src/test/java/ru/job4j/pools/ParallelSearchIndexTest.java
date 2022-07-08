package ru.job4j.pools;

import org.junit.Test;

import java.util.concurrent.ForkJoinPool;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;

public class ParallelSearchIndexTest {

    @Test
    public void whenSearchValueUser4ThenReturnIndex3() {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        String[] array = {"User1", "User2", "User3", "User4", "User5",
                "User6", "User7", "User8", "User9", "User10", "User11"};
        ParallelSearchIndex<String> parallelSearchIndex =
                new ParallelSearchIndex<>(array, "User4", 0, array.length - 1);
        int rsl = forkJoinPool.invoke(parallelSearchIndex);
        assertThat(rsl, is(3));
    }

    @Test
    public void whenSearchValueUser11ThenReturnIndex10() {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        String[] array = {"User1", "User2", "User3", "User4", "User5",
                "User6", "User7", "User8", "User9", "User10", "User11"};
        ParallelSearchIndex<String> parallelSearchIndex =
                new ParallelSearchIndex<>(array, "User11", 0, array.length - 1);
        int rsl = forkJoinPool.invoke(parallelSearchIndex);
        assertThat(rsl, is(10));
    }

    @Test
    public void whenValueNotFound() {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        String[] array = {"User1", "User2", "User3", "User4", "User5",
                "User6", "User7", "User8", "User9", "User10", "User11"};
        ParallelSearchIndex<String> parallelSearchIndex =
                new ParallelSearchIndex<>(array, "User12", 0, array.length - 1);
        int rsl = forkJoinPool.invoke(parallelSearchIndex);
        assertThat(rsl, is(-1));
    }

}