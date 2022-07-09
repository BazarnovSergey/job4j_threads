package ru.job4j.pools;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;

public class ParallelSearchIndexTest {

    @Test
    public void whenSearchValueUser4ThenReturnIndex3() {
        String[] array = {"User1", "User2", "User3", "User4", "User5", "User6",
                "User7", "User8", "User9", "User10", "User11"};
        int rsl = ParallelSearchIndex.search(array, "User4");
        assertThat(rsl, is(3));
    }

    @Test
    public void whenSearchValueUser11ThenReturnIndex10() {
        String[] array = {"User1", "User2", "User3", "User4", "User5", "User6",
                "User7", "User8", "User9", "User10", "User11"};
        int rsl = ParallelSearchIndex.search(array, "User11");
        assertThat(rsl, is(10));
    }

    @Test
    public void whenValueNotFound() {
        String[] array = {"User1", "User2", "User3", "User4", "User5", "User6",
                "User7", "User8", "User9", "User10", "User11"};
        int rsl = ParallelSearchIndex.search(array, "User12");
        assertThat(rsl, is(-1));
    }

}