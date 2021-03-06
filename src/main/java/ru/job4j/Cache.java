package ru.job4j;

public class Cache {

    private static Cache cache;

    public static Cache instOf() {
        if (cache == null) {
            synchronized (Cache.cache) {
                if (cache == null) {
                    cache = new Cache();
                }
            }
        }
        return cache;
    }

}
