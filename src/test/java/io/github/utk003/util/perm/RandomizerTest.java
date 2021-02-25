package io.github.utk003.util.perm;

import java.util.Arrays;

public class RandomizerTest {
    public static void main(String[] args) {
    }

    private static void test_randomizeIntArray() {
        final int SIZE = 10, ITERATIONS = 100000;
        int[] arr = new int[SIZE];
        for (int i = 0; i < SIZE; i++)
            arr[i] = i;

        int[][] results = new int[SIZE][SIZE];
        for (int it = 0; it < ITERATIONS; it++) {
            Randomizer.randomize(arr);
            for (int i = 0; i < SIZE; i++)
                results[i][arr[i]]++;
        }

        for (int[] res : results)
            System.out.println(Arrays.toString(res));
    }
}
