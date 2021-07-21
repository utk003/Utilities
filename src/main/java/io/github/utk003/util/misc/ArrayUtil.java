package io.github.utk003.util.misc;

import io.github.utk003.util.misc.annotations.ScheduledForRelease;
import org.jetbrains.annotations.NotNull;

/**
 * A suite of miscellaneous array-based utility functions not provided by {@link java.util.Arrays}.
 *
 * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
 * @version July 20, 2021
 * @since 3.0.0
 */
@ScheduledForRelease(inVersion = "v3.0.0")
public abstract class ArrayUtil {
    private ArrayUtil() {
    }

    /**
     * Swaps the given positions in an array.
     *
     * @param arr The array whose elements will be swapped
     * @param i1  The index of the first element to swap
     * @param i2  The index of the second element to swap
     * @throws ArrayIndexOutOfBoundsException If either {@code i1} or {@code i2} is out-of-bounds
     *                                        with respect to the given array {@code arr}
     */
    public static void swap(@NotNull byte[] arr, int i1, int i2) {
        byte temp = arr[i1];
        arr[i1] = arr[i2];
        arr[i2] = temp;
    }
    /**
     * Swaps the given positions in an array.
     *
     * @param arr The array whose elements will be swapped
     * @param i1  The index of the first element to swap
     * @param i2  The index of the second element to swap
     * @throws ArrayIndexOutOfBoundsException If either {@code i1} or {@code i2} is out-of-bounds
     *                                        with respect to the given array {@code arr}
     */
    public static void swap(@NotNull short[] arr, int i1, int i2) {
        short temp = arr[i1];
        arr[i1] = arr[i2];
        arr[i2] = temp;
    }
    /**
     * Swaps the given positions in an array.
     *
     * @param arr The array whose elements will be swapped
     * @param i1  The index of the first element to swap
     * @param i2  The index of the second element to swap
     * @throws ArrayIndexOutOfBoundsException If either {@code i1} or {@code i2} is out-of-bounds
     *                                        with respect to the given array {@code arr}
     */
    public static void swap(@NotNull int[] arr, int i1, int i2) {
        int temp = arr[i1];
        arr[i1] = arr[i2];
        arr[i2] = temp;
    }
    /**
     * Swaps the given positions in an array.
     *
     * @param arr The array whose elements will be swapped
     * @param i1  The index of the first element to swap
     * @param i2  The index of the second element to swap
     * @throws ArrayIndexOutOfBoundsException If either {@code i1} or {@code i2} is out-of-bounds
     *                                        with respect to the given array {@code arr}
     */
    public static void swap(@NotNull long[] arr, int i1, int i2) {
        long temp = arr[i1];
        arr[i1] = arr[i2];
        arr[i2] = temp;
    }

    /**
     * Swaps the given positions in an array.
     *
     * @param arr The array whose elements will be swapped
     * @param i1  The index of the first element to swap
     * @param i2  The index of the second element to swap
     * @throws ArrayIndexOutOfBoundsException If either {@code i1} or {@code i2} is out-of-bounds
     *                                        with respect to the given array {@code arr}
     */
    public static void swap(@NotNull float[] arr, int i1, int i2) {
        float temp = arr[i1];
        arr[i1] = arr[i2];
        arr[i2] = temp;
    }
    /**
     * Swaps the given positions in an array.
     *
     * @param arr The array whose elements will be swapped
     * @param i1  The index of the first element to swap
     * @param i2  The index of the second element to swap
     * @throws ArrayIndexOutOfBoundsException If either {@code i1} or {@code i2} is out-of-bounds
     *                                        with respect to the given array {@code arr}
     */
    public static void swap(@NotNull double[] arr, int i1, int i2) {
        double temp = arr[i1];
        arr[i1] = arr[i2];
        arr[i2] = temp;
    }

    /**
     * Swaps the given positions in an array.
     *
     * @param arr The array whose elements will be swapped
     * @param i1  The index of the first element to swap
     * @param i2  The index of the second element to swap
     * @throws ArrayIndexOutOfBoundsException If either {@code i1} or {@code i2} is out-of-bounds
     *                                        with respect to the given array {@code arr}
     */
    public static void swap(@NotNull boolean[] arr, int i1, int i2) {
        boolean temp = arr[i1];
        arr[i1] = arr[i2];
        arr[i2] = temp;
    }
    /**
     * Swaps the given positions in an array.
     *
     * @param arr The array whose elements will be swapped
     * @param i1  The index of the first element to swap
     * @param i2  The index of the second element to swap
     * @throws ArrayIndexOutOfBoundsException If either {@code i1} or {@code i2} is out-of-bounds
     *                                        with respect to the given array {@code arr}
     */
    public static void swap(@NotNull char[] arr, int i1, int i2) {
        char temp = arr[i1];
        arr[i1] = arr[i2];
        arr[i2] = temp;
    }

    /**
     * Swaps the given positions in an array.
     *
     * @param <T> The type of the objects in the given array
     * @param arr The array whose elements will be swapped
     * @param i1  The index of the first element to swap
     * @param i2  The index of the second element to swap
     * @throws ArrayIndexOutOfBoundsException If either {@code i1} or {@code i2} is out-of-bounds
     *                                        with respect to the given array {@code arr}
     */
    public static <T> void swap(@NotNull T[] arr, int i1, int i2) {
        T temp = arr[i1];
        arr[i1] = arr[i2];
        arr[i2] = temp;
    }
}
