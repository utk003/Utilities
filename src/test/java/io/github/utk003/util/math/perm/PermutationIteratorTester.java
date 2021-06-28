package io.github.utk003.util.math.perm;

import java.util.Arrays;

public class PermutationIteratorTester {
    public static void main(String[] args) {
        PermutationIterator.runForEach(4, (a) -> System.out.println(Arrays.toString(a)));
    }
}
