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

package io.github.utk003.util.math.perm;

import io.github.utk003.util.misc.ArrayUtil;
import io.github.utk003.util.misc.annotations.ScheduledForRelease;

/**
 * An abstract utility class for randomizing an ordered collection
 * of objects or primitives.
 * <p>
 * The specified collection is randomly permuted using the modernized variation of the
 * <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">Fisher–Yates shuffle</a>,
 * first developed by Richard Durstenfeld. The algorithm permutes the elements of the array,
 * picking one permutation uniformly at random from all possible permutations. The algorithm has
 * a runtime complexity of {@code O(n)} and a memory complexity of {@code O(1)}.
 *
 * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
 * @version June 26, 2021
 * @since 3.0.0
 */
@ScheduledForRelease(inVersion = "v3.0.0")
public abstract class Randomizer {
    /**
     * Creates a new {@code Randomizer}.
     */
    private Randomizer() {
    }

    /**
     * Randomizes an array of {@code byte}s.
     *
     * @param arr The array to randomize
     */
    public static void randomize(byte[] arr) {
        int i = arr.length;
        while (i > 0) ArrayUtil.swap(arr, (int) (i * Math.random()), --i);
    }
    /**
     * Randomizes an array of {@code short}s.
     *
     * @param arr The array to randomize
     */
    public static void randomize(short[] arr) {
        int i = arr.length;
        while (i > 0) ArrayUtil.swap(arr, (int) (i * Math.random()), --i);
    }
    /**
     * Randomizes an array of {@code int}s.
     *
     * @param arr The array to randomize
     */
    public static void randomize(int[] arr) {
        int i = arr.length;
        while (i > 0) ArrayUtil.swap(arr, (int) (i * Math.random()), --i);
    }
    /**
     * Randomizes an array of {@code long}s.
     *
     * @param arr The array to randomize
     */
    public static void randomize(long[] arr) {
        int i = arr.length;
        while (i > 0) ArrayUtil.swap(arr, (int) (i * Math.random()), --i);
    }

    /**
     * Randomizes an array of {@code float}s.
     *
     * @param arr The array to randomize
     */
    public static void randomize(float[] arr) {
        int i = arr.length;
        while (i > 0) ArrayUtil.swap(arr, (int) (i * Math.random()), --i);
    }
    /**
     * Randomizes an array of {@code double}s.
     *
     * @param arr The array to randomize
     */
    public static void randomize(double[] arr) {
        int i = arr.length;
        while (i > 0) ArrayUtil.swap(arr, (int) (i * Math.random()), --i);
    }

    /**
     * Randomizes an array of {@code boolean}s.
     *
     * @param arr The array to randomize
     */
    public static void randomize(boolean[] arr) {
        int i = arr.length;
        while (i > 0) ArrayUtil.swap(arr, (int) (i * Math.random()), --i);
    }
    /**
     * Randomizes an array of {@code char}s.
     *
     * @param arr The array to randomize
     */
    public static void randomize(char[] arr) {
        int i = arr.length;
        while (i > 0) ArrayUtil.swap(arr, (int) (i * Math.random()), --i);
    }

    /**
     * Randomizes an array of objects.
     *
     * @param arr The array to randomize
     * @param <T> The class type of the objects in the array
     */
    public static <T> void randomize(T[] arr) {
        int i = arr.length;
        while (i > 0) ArrayUtil.swap(arr, (int) (i * Math.random()), --i);
    }
}
