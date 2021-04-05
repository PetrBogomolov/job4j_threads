package ru.job4j.multithreading.pools;

import org.junit.Test;
import java.util.concurrent.ExecutionException;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class RolColSumTest {

    private final int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9, 10}};

    @Test
    public void whenSeriallySumSuccessful() {
        RolColSum.Sums[] expected = new RolColSum.Sums[] {
                new RolColSum.Sums(6, 12),
                new RolColSum.Sums(15, 15),
                new RolColSum.Sums(34, 18)
        };
        assertThat(RolColSum.seriallySum(matrix), is(expected));
    }

    @Test
    public void whenAsyncSumSuccessful() throws ExecutionException, InterruptedException {
        RolColSum.Sums[] expected = new RolColSum.Sums[] {
                new RolColSum.Sums(6, 12),
                new RolColSum.Sums(15, 15),
                new RolColSum.Sums(34, 18)
        };
        assertThat(RolColSum.asyncSum(matrix), is(expected));
    }
}
