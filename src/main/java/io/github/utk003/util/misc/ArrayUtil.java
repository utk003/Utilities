package io.github.utk003.util.misc;

import org.jetbrains.annotations.NotNull;

public abstract class ArrayUtil {
    private ArrayUtil() {
    }

    public static void swap(@NotNull byte[] arr, int i1, int i2) {
        byte temp = arr[i1];
        arr[i1] = arr[i2];
        arr[i2] = temp;
    }
    public static void swap(@NotNull short[] arr, int i1, int i2) {
        short temp = arr[i1];
        arr[i1] = arr[i2];
        arr[i2] = temp;
    }
    public static void swap(@NotNull int[] arr, int i1, int i2) {
        int temp = arr[i1];
        arr[i1] = arr[i2];
        arr[i2] = temp;
    }
    public static void swap(@NotNull long[] arr, int i1, int i2) {
        long temp = arr[i1];
        arr[i1] = arr[i2];
        arr[i2] = temp;
    }

    public static void swap(@NotNull float[] arr, int i1, int i2) {
        float temp = arr[i1];
        arr[i1] = arr[i2];
        arr[i2] = temp;
    }
    public static void swap(@NotNull double[] arr, int i1, int i2) {
        double temp = arr[i1];
        arr[i1] = arr[i2];
        arr[i2] = temp;
    }

    public static void swap(@NotNull boolean[] arr, int i1, int i2) {
        boolean temp = arr[i1];
        arr[i1] = arr[i2];
        arr[i2] = temp;
    }
    public static void swap(@NotNull char[] arr, int i1, int i2) {
        char temp = arr[i1];
        arr[i1] = arr[i2];
        arr[i2] = temp;
    }

    public static <T> void swap(@NotNull T[] arr, int i1, int i2) {
        T temp = arr[i1];
        arr[i1] = arr[i2];
        arr[i2] = temp;
    }

}
