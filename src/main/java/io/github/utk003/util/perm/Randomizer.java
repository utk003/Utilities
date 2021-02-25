/*
MIT License

Copyright (c) 2021 Utkarsh Priyam

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */

package io.github.utk003.util.perm;

import java.util.Arrays;
import java.util.List;

/**
 * An abstract utility class for randomizing an ordered collection
 * of objects or primitives.
 * <p>
 * This class can randomize arrays or {@code List}s of either primitives
 * or objects. It does so via the overloaded {@code randomize} methods.
 * <p>
 * The specified collection is randomly permuted using the modernized variation of the
 * <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">Fisher–Yates shuffle</a>,
 * first developed by Richard Durstenfeld. The algorithm permutes the elements of the array,
 * picking one permutation uniformly at random from all possible permutations. The algorithm has
 * a runtime complexity of {@code O(n)} and memory usage of {@code O(n)} as well.
 *
 * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
 * @version February 25, 2021
 * @since 1.2.0
 */
public abstract class Randomizer {
    private Randomizer() {
    }

    /**
     * Randomizes an array of {@code byte}s.
     *
     * @param arr The array to randomize
     */
    public static void randomize(byte[] arr) {
        int len = arr.length;

        byte[] second = new byte[len];
        int[] indices = new int[len];

        for (int i = 0; i < len; i++)
            indices[i] = i;

        for (int i = len - 1; i >= 0; i--) {
            int indexIndex = (int) ((i + 1) * Math.random());
            second[i] = arr[indices[indexIndex]];

            int temp = indices[i];
            indices[i] = indices[indexIndex];
            indices[indexIndex] = temp;
        }

        System.arraycopy(second, 0, arr, 0, len);
    }
    /**
     * Randomizes an array of {@code short}s.
     *
     * @param arr The array to randomize
     */
    public static void randomize(short[] arr) {
        int len = arr.length;

        short[] second = new short[len];
        int[] indices = new int[len];

        for (int i = 0; i < len; i++)
            indices[i] = i;

        for (int i = len - 1; i >= 0; i--) {
            int indexIndex = (int) ((i + 1) * Math.random());
            second[i] = arr[indices[indexIndex]];

            int temp = indices[i];
            indices[i] = indices[indexIndex];
            indices[indexIndex] = temp;
        }

        System.arraycopy(second, 0, arr, 0, len);
    }
    /**
     * Randomizes an array of {@code int}s.
     *
     * @param arr The array to randomize
     */
    public static void randomize(int[] arr) {
        int len = arr.length;

        int[] second = new int[len];
        int[] indices = new int[len];

        for (int i = 0; i < len; i++)
            indices[i] = i;

        for (int i = len - 1; i >= 0; i--) {
            int indexIndex = (int) ((i + 1) * Math.random());
            second[i] = arr[indices[indexIndex]];

            int temp = indices[i];
            indices[i] = indices[indexIndex];
            indices[indexIndex] = temp;
        }

        System.arraycopy(second, 0, arr, 0, len);
    }
    /**
     * Randomizes an array of {@code long}s.
     *
     * @param arr The array to randomize
     */
    public static void randomize(long[] arr) {
        int len = arr.length;

        long[] second = new long[len];
        int[] indices = new int[len];

        for (int i = 0; i < len; i++)
            indices[i] = i;

        for (int i = len - 1; i >= 0; i--) {
            int indexIndex = (int) ((i + 1) * Math.random());
            second[i] = arr[indices[indexIndex]];

            int temp = indices[i];
            indices[i] = indices[indexIndex];
            indices[indexIndex] = temp;
        }

        System.arraycopy(second, 0, arr, 0, len);
    }

    /**
     * Randomizes an array of {@code float}s.
     *
     * @param arr The array to randomize
     */
    public static void randomize(float[] arr) {
        int len = arr.length;

        float[] second = new float[len];
        int[] indices = new int[len];

        for (int i = 0; i < len; i++)
            indices[i] = i;

        for (int i = len - 1; i >= 0; i--) {
            int indexIndex = (int) ((i + 1) * Math.random());
            second[i] = arr[indices[indexIndex]];

            int temp = indices[i];
            indices[i] = indices[indexIndex];
            indices[indexIndex] = temp;
        }

        System.arraycopy(second, 0, arr, 0, len);
    }
    /**
     * Randomizes an array of {@code double}s.
     *
     * @param arr The array to randomize
     */
    public static void randomize(double[] arr) {
        int len = arr.length;

        double[] second = new double[len];
        int[] indices = new int[len];

        for (int i = 0; i < len; i++)
            indices[i] = i;

        for (int i = len - 1; i >= 0; i--) {
            int indexIndex = (int) ((i + 1) * Math.random());
            second[i] = arr[indices[indexIndex]];

            int temp = indices[i];
            indices[i] = indices[indexIndex];
            indices[indexIndex] = temp;
        }

        System.arraycopy(second, 0, arr, 0, len);
    }

    /**
     * Randomizes an array of {@code char}s.
     *
     * @param arr The array to randomize
     */
    public static void randomize(char[] arr) {
        int len = arr.length;

        char[] second = new char[len];
        int[] indices = new int[len];

        for (int i = 0; i < len; i++)
            indices[i] = i;

        for (int i = len - 1; i >= 0; i--) {
            int indexIndex = (int) ((i + 1) * Math.random());
            second[i] = arr[indices[indexIndex]];

            int temp = indices[i];
            indices[i] = indices[indexIndex];
            indices[indexIndex] = temp;
        }

        System.arraycopy(second, 0, arr, 0, len);
    }
    /**
     * Randomizes an array of {@code boolean}s.
     *
     * @param arr The array to randomize
     */
    public static void randomize(boolean[] arr) {
        int len = arr.length;

        boolean[] second = new boolean[len];
        int[] indices = new int[len];

        for (int i = 0; i < len; i++)
            indices[i] = i;

        for (int i = len - 1; i >= 0; i--) {
            int indexIndex = (int) ((i + 1) * Math.random());
            second[i] = arr[indices[indexIndex]];

            int temp = indices[i];
            indices[i] = indices[indexIndex];
            indices[indexIndex] = temp;
        }

        System.arraycopy(second, 0, arr, 0, len);
    }

    /**
     * Randomizes an array of objects.
     *
     * @param arr The array to randomize
     * @param <T> The object type of the array to randomize
     */
    public static <T> void randomize(T[] arr) {
        int len = arr.length;

        //noinspection unchecked
        T[] second = (T[]) new Object[len];
        int[] indices = new int[len];

        for (int i = 0; i < len; i++)
            indices[i] = i;

        for (int i = len - 1; i >= 0; i--) {
            int indexIndex = (int) ((i + 1) * Math.random());
            second[i] = arr[indices[indexIndex]];

            int temp = indices[i];
            indices[i] = indices[indexIndex];
            indices[indexIndex] = temp;
        }

        System.arraycopy(second, 0, arr, 0, len);
    }

    /**
     * Randomizes a {@code List} of objects.
     *
     * @param list The list to randomize
     * @param <T>  The object type of the elements of the {@code List} to randomize
     */
    @SuppressWarnings("unchecked")
    public static <T> void randomize(List<T> list) {
        T[] arr = list.toArray((T[]) new Object[0]);
        randomize(arr);
        list.clear();
        list.addAll(Arrays.asList(arr));
    }
}
