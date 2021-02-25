package io.github.utk003.util.perm;

import java.util.Arrays;

public class PermutationIteratorTest {
    public static void main(String[] args) {
        System.out.println("Recursive Output:");
        for (int i = 0; i <= 4; i++) {
            PermutationIterator.runOnRecursiveHeapsPermutations(i, (arr) -> System.out.println(Arrays.toString(arr)));
            System.out.println();
        }

        System.out.println("Non-Recursive Output:");
        for (int i = 0; i <= 4; i++) {
            PermutationIterator.runOnNonRecursiveHeapsPermutations(i, (arr) -> System.out.println(Arrays.toString(arr)));
            System.out.println();
        }
    }
}
