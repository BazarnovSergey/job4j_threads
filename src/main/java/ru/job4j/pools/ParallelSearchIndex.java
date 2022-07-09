package ru.job4j.pools;

import java.util.Objects;
import java.util.concurrent.RecursiveTask;

public class ParallelSearchIndex<T> extends RecursiveTask<Integer> {

    private final T[] array;
    private final T value;
    private final int from;
    private final int to;

    public ParallelSearchIndex(T[] array, T value, int from, int to) {
        this.array = array;
        this.value = value;
        this.from = from;
        this.to = to;
    }

    @Override
    protected Integer compute() {
        if ((from - to) < 10) {
            return linearSearch();
        }
        int mid = (from + to) / 2;
        ParallelSearchIndex<T> leftSearch = new ParallelSearchIndex<>(array, value, from, mid);
        ParallelSearchIndex<T> rightSearch = new ParallelSearchIndex<>(array, value, mid + 1, to);
        leftSearch.fork();
        rightSearch.fork();
        Integer left = leftSearch.join();
        Integer right = rightSearch.join();
        return Math.max(left, right);
    }

    private Integer linearSearch() {
        for (int i = from; i <= to; i++) {
            if (Objects.equals(array[i], value)) {
                return i;
            }
        }
        return -1;
    }

    public static Integer search(String[] array, String value, int from, int to) {
        ParallelSearchIndex<String> psi = new ParallelSearchIndex<>(array, value, from, to);
        return psi.invoke();
    }

}
