package ru.job4j.multithreading.pools;

import java.util.Objects;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSearchIndex<T> extends RecursiveTask<Integer> {
    private final T[] array;
    private final int from;
    private final int to;
    private final T key;

    public ParallelSearchIndex(T[] array, int from, int to, T key) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.key = key;
    }

    public int linearSearch() {
        for (int index = from; index < to; index++) {
            if (Objects.equals(array[index], key)) {
                return index;
            }
        }
        return - 1;
    }

    @Override
    protected Integer compute() {
        if (to - from < 10) {
            return linearSearch();
        }
        int mid = (from + to) / 2;
        ParallelSearchIndex<T> left = new ParallelSearchIndex<>(array, from, mid, key);
        ParallelSearchIndex<T> right = new ParallelSearchIndex<>(array, mid + 1, to, key);
        left.fork();
        right.fork();
        return Math.max(left.join(), right.join());
    }

    public int search() {
        ForkJoinPool pool = ForkJoinPool.commonPool();
        return pool.invoke(new ParallelSearchIndex<>(array, from, to, key));
    }
}
