package ru.job4j.cache;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class CacheTest {

    @Test
    public void whenVersionsEqual() {
        Cache cache = new Cache();
        Base base1 = new Base(1, 1);
        cache.add(base1);
        cache.update(base1);
        int rsl = cache.findId(base1.getId()).getVersion();
        assertThat(rsl, is(2));
    }

    @Test(expected = OptimisticException.class)
    public void whenVersionsNotEqual() {
        Cache cache = new Cache();
        Base base1 = new Base(1, 1);
        cache.add(base1);
        cache.update(base1);
        cache.update(base1);
    }

    @Test
    public void whenAdd3Delete2Than1() {
        Cache cache = new Cache();
        Base base1 = new Base(1, 1);
        Base base2 = new Base(2, 1);
        Base base3 = new Base(3, 1);
        cache.add(base1);
        cache.add(base2);
        cache.add(base3);
        cache.delete(base1);
        cache.delete(base2);
        assertThat(cache.getSize(), is(1));
    }

}