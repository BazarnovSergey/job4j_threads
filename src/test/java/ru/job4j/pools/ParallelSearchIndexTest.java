package ru.job4j.pools;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;

public class ParallelSearchIndexTest {

    String[] array = {"User1", "User2", "User3", "User4", "User5", "User6",
            "User7", "User8", "User9", "User10", "User11"};

    int from = 0;
    int to = array.length - 1;


    @Test
    public void whenSearchValueUser4ThenReturnIndex3() {
        int rsl = ParallelSearchIndex.search(array, "User4", from, to);
        assertThat(rsl, is(3));
    }

    @Test
    public void whenSearchValueUser11ThenReturnIndex10() {
        int rsl = ParallelSearchIndex.search(array, "User11", from, to);
        assertThat(rsl, is(10));
    }

    @Test
    public void whenValueNotFound() {
        int rsl = ParallelSearchIndex.search(array, "User12", from, to);
        assertThat(rsl, is(-1));
    }

}