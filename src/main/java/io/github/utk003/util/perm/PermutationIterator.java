package io.github.utk003.util.perm;

import io.github.utk003.util.function.void_lambda.VoidLambda1;
import io.github.utk003.util.misc.Verifier;

/**
 * An abstract utility class for performing actions on all permutations of
 * an array of any objects or elements.
 * <p>
 * This class provides a variety of different permutation iteration implementations.
 * These options are provided by the {@link PermutationAlgorithms} enum.
 *
 * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
 * @version February 25, 2021
 * @since 1.2.0
 */
public abstract class PermutationIterator {
    private PermutationIterator() {
    }

    /**
     * An enum for all valid permutation algorithms supported
     * by the {@link PermutationIterator} class.
     * <p>
     * The currently supported algorithms are a recursive and
     * a non-recursive implementation of
     * <a href="https://en.wikipedia.org/wiki/Heap%27s_algorithm">Heap's Algorithm</a>.
     * <p>
     * These algorithm types correspond to the following utility methods:
     * <ul>
     * <li>{@link #RECURSIVE_HEAPS} corresponds to {@link #runOnRecursiveHeapsPermutations(int, VoidLambda1)}
     * <li>{@link #NON_RECURSIVE_HEAPS} corresponds to {@link #runOnNonRecursiveHeapsPermutations(int, VoidLambda1)}
     * </ul>
     *
     * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
     * @version February 25, 2021
     */
    public enum PermutationAlgorithms {
        RECURSIVE_HEAPS, NON_RECURSIVE_HEAPS
    }

    /**
     * Runs the specified action on all permutations of a collection of elements.
     * <p>
     * This method is a general wrapper for the different permutation implementations
     * provided by this class. The specific implementation can be chosen either by directly
     * calling the permutation method or by specifying the desired algorithm using the
     * {@link PermutationAlgorithms} argument for this method.
     * <p>
     * The lambda {@code action} argument is the action that will be performed on each
     * permutation of the elements. This lambda function takes a single array of indices
     * as an argument. This array has a length equal to the {@code numElements} argument,
     * and it contains each number in the interval {@code [0,numElements)} in some
     * permutation. These numbers are the indices corresponding to a unique permutation,
     * and they should be used by the {@code action} lambda function to calculate the
     * proper ordering of elements corresponding to that array of indices.
     * <p>
     * Note that the lambda function should not in any way modify the {@code int[]} indices
     * array provided as a argument, or the behavior of this permutations method will be
     * undefined from that point onwards.
     *
     * @param numElements The number of elements to permute
     * @param action      The action to perform on each permutation
     * @param algorithm   The specific permutations algorithm to run
     * @see #runOnRecursiveHeapsPermutations(int, VoidLambda1)
     * @see #runOnNonRecursiveHeapsPermutations(int, VoidLambda1)
     */
    public static void runOnPermutations(int numElements, VoidLambda1<int[]> action, PermutationAlgorithms algorithm) {
        switch (algorithm) {
            case RECURSIVE_HEAPS:
                runOnRecursiveHeapsPermutations(numElements, action);
                break;

            case NON_RECURSIVE_HEAPS:
                runOnNonRecursiveHeapsPermutations(numElements, action);
                break;

            default:
                Verifier.fail("Unrecognized Permutation Algorithm");
        }
    }

    /**
     * Runs the specified action on all permutations of a collection of elements.
     * The permutations are generated using recursive Heap's Algorithm.
     *
     * @param numElements The number of elements to permute
     * @param action      The action to perform on each permutation
     * @see #runOnPermutations(int, VoidLambda1, PermutationAlgorithms)
     * @see PermutationAlgorithms
     */
    public static void runOnRecursiveHeapsPermutations(int numElements, VoidLambda1<int[]> action) {
        Verifier.requireTrue(numElements >= 0, "Cannot permute a negative number of elements");
        if (numElements == 0) {
            action.run(new int[0]);
            return;
        }

        int[] indices = new int[numElements];
        for (int i = 0; i < numElements; i++)
            indices[i] = i;

        runOnRecursiveHeapsPermutationsHelper(action, indices, numElements);
    }
    /**
     * A recursive helper method for {@link #runOnRecursiveHeapsPermutations(int, VoidLambda1)}.
     *
     * @param action  The action to perform on each permutation
     * @param indices The current indices for the permutation
     * @param len     A length tracker for the permutation state
     */
    private static void runOnRecursiveHeapsPermutationsHelper(VoidLambda1<int[]> action, int[] indices, int len) {
        if (len-- == 1)
            action.run(indices);
        else {
            runOnRecursiveHeapsPermutationsHelper(action, indices, len);
            for (int i = 0; i < len; i++) {
                /* swap */
                {
                    int i1 = len % 2 == 0 ? i : 0, temp = indices[i1];
                    indices[i1] = indices[len];
                    indices[len] = temp;
                }
                runOnRecursiveHeapsPermutationsHelper(action, indices, len);
            }
        }
    }

    /**
     * Runs the specified action on all permutations of a collection of elements.
     * The permutations are generated using non-recursive Heap's Algorithm.
     *
     * @param numElements The number of elements to permute
     * @param action      The action to perform on each permutation
     * @see #runOnPermutations(int, VoidLambda1, PermutationAlgorithms)
     * @see PermutationAlgorithms
     */
    public static void runOnNonRecursiveHeapsPermutations(int numElements, VoidLambda1<int[]> action) {
        Verifier.requireTrue(numElements >= 0, "Cannot permute a negative number of elements");

        int[] state = new int[numElements], indices = new int[numElements];
        for (int i = 0; i < numElements; i++)
            indices[i] = i;
        int i = 0;

        action.run(indices);
        while (i < numElements) {
            if (state[i] < i) {
                /* swap */
                {
                    int i1 = i % 2 == 0 ? 0 : state[i], temp = indices[i1];
                    indices[i1] = indices[i];
                    indices[i] = temp;
                }

                action.run(indices);

                state[i]++;
                i = 1;
            } else
                state[i++] = 0;
        }
    }
}