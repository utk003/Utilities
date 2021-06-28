package io.github.utk003.util.math.perm;

import io.github.utk003.util.func.VoidLambda1;
import io.github.utk003.util.misc.ArrayUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

/**
 * An iterator over all possible permutations of an array. The permutations
 * are generated using non-recursive Heap's Algorithm.
 *
 * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
 * @version June 26, 2021
 * @since 3.0.0
 */
public final class PermutationIterator implements Iterator<int[]> {
    private final @NotNull int[] state, indices;
    private final int numElements;
    private int i = 0;
    private boolean justInit = true;

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

    public static void runForEach(int numElements, VoidLambda1<int[]> action) {
        PermutationIterator it = new PermutationIterator(numElements);
        while (it.hasNext()) action.run(it.next());
    }
}
