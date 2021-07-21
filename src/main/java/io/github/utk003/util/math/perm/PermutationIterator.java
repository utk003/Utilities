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

import io.github.utk003.util.func.VoidLambda1;
import io.github.utk003.util.misc.ArrayUtil;
import io.github.utk003.util.misc.annotations.ScheduledForRelease;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

/**
 * An iterator over all possible permutations of an array. The permutations
 * are generated using non-recursive Heap's Algorithm.
 * <p>
 * This iterator works by returning a permutation of {@code n} indices, where
 * {@code n} is the number of objects, positions, etc. that are being permuted.
 * This array of indices can then be used to access elements from another array.
 * <p>
 * To improve performance, the array is shared across calls to {@link #next()}.
 * Additionally, this class assumes the state of the array does not change.
 * Changing the contents of the returned array between calls to {@code next()}
 * <em>may</em> break the permutation iteration.
 *
 * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
 * @version June 26, 2021
 * @since 3.0.0
 */
@ScheduledForRelease(inVersion = "v3.0.0")
public final class PermutationIterator implements Iterator<int[]> {
    private final @NotNull int[] state, indices;
    private final int numElements;
    private int i = 0;
    private boolean justInit = true;

    /**
     * Creates a new {@code PermutationIterator} over {@code n} indices.
     *
     * @param n The number of indices to permute
     */
    public PermutationIterator(int n) {
        numElements = n;
        state = new int[n];
        indices = new int[n];
        for (int i = 0; i < numElements; i++)
            indices[i] = i;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasNext() {
        if (justInit) return true;
        while (i < numElements && i <= state[i]) state[i++] = 0;
        return i < numElements;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull int[] next() {
        if (justInit) {
            justInit = false;
            return indices;
        }

        while (true)
            if (state[i] < i) {
                ArrayUtil.swap(indices, i, i % 2 == 0 ? 0 : state[i]);

                state[i]++;
                i = 1;

                return indices;
            } else state[i++] = 0;
    }

    /**
     * Runs a specific action over all permutations of {@code numElements} indices
     *
     * @param numElements The number of indices to permute
     * @param action      The action to perform over all permutations of indices
     */
    public static void runForEach(int numElements, VoidLambda1<int[]> action) {
        PermutationIterator it = new PermutationIterator(numElements);
        while (it.hasNext()) action.run(it.next());
    }
}
