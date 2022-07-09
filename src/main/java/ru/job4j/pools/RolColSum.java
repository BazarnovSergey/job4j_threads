package ru.job4j.pools;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {

    public static class Sums {

        private int rowSum;
        private int colSum;

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }

    }

    public static Sums[] sum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            int totalRowSum = 0;
            int totalColSum = 0;
            for (int j = 0; j < matrix[i].length; j++) {
                totalRowSum += matrix[i][j];
                totalColSum += matrix[j][i];
            }
            Sums sum = new Sums();
            sum.setRowSum(totalRowSum);
            sum.setColSum(totalColSum);
            sums[i] = sum;
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] rsl = new Sums[matrix.length];
        Map<Integer, CompletableFuture<Sums>> map = new HashMap<>();
        for (int i = 0; i < matrix.length; i++) {
            map.put(i, getTask(matrix, i));
        }
        for (Integer key : map.keySet()) {
            rsl[key] = map.get(key).get();
        }
        return rsl;
    }

    private static CompletableFuture<Sums> getTask(int[][] matrix, int index) {
        return CompletableFuture.supplyAsync(() -> {
            Sums sums = new Sums();
            for (int i = 0; i < matrix.length; i++) {
                sums.rowSum += matrix[index][i];
                sums.colSum += matrix[i][index];
            }
            return sums;
        });
    }
}

