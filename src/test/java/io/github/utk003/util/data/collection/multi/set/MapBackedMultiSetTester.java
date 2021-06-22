package io.github.utk003.util.data.collection.multi.set;

import java.util.Arrays;
import java.util.HashMap;

public class MapBackedMultiSetTester {
    public static void main(String[] args) throws Exception {
        MapBackedMultiSet<Integer> mset = new MapBackedMultiSet<>(HashMap.class);
        for (int i = 0; i <= 10; i++)
            mset.add(i, i / 2 + 1);

        System.out.println(mset);
        System.out.println(Arrays.toString(mset.toArray()));

        for (int i : mset)
            System.out.print(" " + i + ",");
        System.out.println();
    }
}
