package ru.job4j.multithreading.pools;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ParallelSearchIndexTest {
    private final Integer[] sizeNoMore10 = new Integer[10];
    private final Integer[] sizeMore10 = new Integer[100];

    @Before
    public void setup() {
        for (int i = 0; i < 100; i++) {
            sizeMore10[i] = i;
        }
        for (int i = 0; i < 10; i++) {
            sizeNoMore10[i] = i;
        }
    }

    @Test
    public void whenSearchInArrayWithSizeLess10WasSuccessful() {
        assertThat(
                new ParallelSearchIndex<>(sizeNoMore10, 0, sizeNoMore10.length, 7).search(),
                is(7)
        );
    }

    @Test
    public void whenSearchInArrayWithSizeMore10WasSuccessful() {
        assertThat(
                new ParallelSearchIndex<>(sizeMore10, 0, sizeMore10.length, 77).search(),
                is(77)
        );
    }

    @Test
    public void whenIndexNotSearchInArrayThenMinus1() {
        assertThat(
                new ParallelSearchIndex<>(sizeMore10, 0, sizeMore10.length, 101).search(),
                is(-1)
        );
    }
}
