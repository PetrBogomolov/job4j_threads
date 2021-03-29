package ru.job4j.multithreading.pools;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
    public static class Sums {
        private final int rowSum;
        private final int colSum;

        public Sums(int rowSum, int colSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            Sums sums = (Sums) o;
            return rowSum == sums.rowSum
                    && colSum == sums.colSum;
        }

        @Override
        public int hashCode() {
            return Objects.hash(rowSum, colSum);
        }
    }

    public static Sums[] seriallySum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        for (int index = 0; index < matrix.length; index++) {
            sums[index] = new Sums(sumRow(matrix, index), sumColumn(matrix, index));
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
       Sums[] sums = new Sums[matrix.length];
       for (int i = 0; i < matrix.length; i++) {
           int index = i;
           int sumRow = CompletableFuture.supplyAsync(
                  () -> sumRow(matrix, index)).get();
           int sumColumn = CompletableFuture.supplyAsync(
                   () -> sumColumn(matrix, index)
           ).get();
           sums[index] = new Sums(sumRow, sumColumn);
       }
       return sums;
    }

    private static int sumRow(int[][] matrix, int row) {
        int sumRow = 0;
        for (int column = 0; column < matrix[row].length; column++) {
            sumRow += matrix[row][column];
        }
        return sumRow;
    }

    private static int sumColumn(int[][] matrix, int column) {
        int sumColumn = 0;
        for (int row = 0; row < matrix.length; row++) {
            sumColumn += matrix[row][column];
        }
        return sumColumn;
    }
}
