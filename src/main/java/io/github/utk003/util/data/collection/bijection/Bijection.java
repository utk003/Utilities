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

package io.github.utk003.util.data.collection.bijection;

import io.github.utk003.util.data.tuple.immutable.ImmutablePair;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collection;

/**
 * A custom {@code Collection} that represents a bijective mapping.
 * <p>
 * A {@code Bijection} maps two sets of objects in a 1:1 mapping. For more information on
 * bijections, check out <a href="https://en.wikipedia.org/wiki/Bijection" target="_top">https://en.wikipedia.org/wiki/Bijection</a>.
 *
 * @param <A> The type of objects in the first set of this bijection
 * @param <B> The type of objects in the second set of this bijection
 * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
 * @version April 23, 2021
 * @see Collection
 * @since 2.0.0
 */
public interface Bijection<A, B> extends Collection<Bijection.Pairing<A, B>> {
    /**
     * A utility class representing a unique 1:1 mapping between two elements in a bijection.
     * <p>
     * This class plays a very similar role to what {@link java.util.Map.Entry} does for {@link java.util.Map}.
     *
     * @param <A> The type of the first element in this mapping
     * @param <B> The type of the second element in this mapping
     * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
     * @version April 23, 2021
     * @see java.util.Map.Entry
     * @since 2.0.0
     */
    final class Pairing<A, B> {
        /**
         * A public reference to the first object in this mapping.
         */
        final @NotNull A FIRST;
        /**
         * A public reference to the second object in this mapping.
         */
        final @NotNull B SECOND;

        /**
         * Creates a new bijection pairing between the specified elements
         *
         * @param first  The first element of the pairing
         * @param second The second element of the pairing
         */
        public Pairing(@NotNull A first, @NotNull B second) {
            FIRST = first;
            SECOND = second;
        }

        /**
         * Returns the first element of this pairing
         *
         * @return The first element of this pairing
         * @see #FIRST
         */
        public @NotNull A getFirst() {
            return FIRST;
        }
        /**
         * Returns the second element of this pairing
         *
         * @return The second element of this pairing
         * @see #SECOND
         */
        public @NotNull B getSecond() {
            return SECOND;
        }

        /**
         * Returns the specified element of this pairing
         *
         * @param <T>   The type of the object to be returned
         * @param first Whether to return the first element of this pairing or the second
         * @return The first element of this pairing if the argument is {@code true};
         * otherwise, the second element of this pairing
         * @throws ClassCastException If the parametrized type {@code T} does not correspond to type {@code A}
         *                            or {@code B}, depending on whether the method argument is {@code true} or {@code false}, respectively.
         * @see #FIRST
         * @see #SECOND
         */
        public <T> @NotNull T get(boolean first) {
            //noinspection unchecked
            return (T) (first ? FIRST : SECOND);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return FIRST + "=" + SECOND;
        }
        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            return FIRST.hashCode() ^ SECOND.hashCode();
        }
        /**
         * {@inheritDoc}
         * <p>
         * This method returns whether or not the two elements of this pair
         * exactly match the elements of the other pair (using {@code ==}
         * to check for equality).
         */
        @Override
        public boolean equals(Object obj) {
            return obj instanceof Pairing<?, ?> && FIRST == ((Pairing<?, ?>) obj).FIRST && SECOND == ((Pairing<?, ?>) obj).SECOND;
        }
    }

    /*
     * java.util.Collection methods that will be overridden by subclasses
     * ------------------------------------------------------------------
     * int size();
     * Iterator<Pairing<A, B>> iterator();
     *
     * boolean containsAll(Collection<?> c);
     * boolean addAll(Collection<? extends Pairing<A, B>> c);
     * boolean removeAll(Collection<?> c);
     * boolean retainAll(Collection<?> c);
     *
     * void clear();
     */

    /**
     * {@inheritDoc}
     * <p>
     * The default implementation simply uses the {@link #containsPairing(Pairing)}
     * if the argument to this method can be cast and just returns {@code false} otherwise.
     *
     * @throws ClassCastException If the given object is of type {@code Pairing} but with mismatched parameters
     */
    @Override
    default boolean contains(Object o) {
        //noinspection unchecked
        return o instanceof Pairing<?, ?> && containsPairing((Pairing<A, B>) o);
    }
    /**
     * Returns whether or not this {@code Bijection} contains the specified mapping.
     * <p>
     * This method is equivalent to running <pre>containsPairing(new Pairing&lt;&gt;(obj1, obj2))}</pre>
     * As a result, only one of these two methods should be explicitly defined. If both are overridden,
     * then both implementations must have exactly the same effect on the internal state of this {@code Bijection.}
     *
     * @param obj1 The first element of the pairing
     * @param obj2 The second element of the pairing
     * @return Whether or not this pair is mapped together in the bijection
     * @see #containsPairing(Pairing)
     */
    boolean containsPairing(@NotNull A obj1, @NotNull B obj2);
    /**
     * Returns whether or not this {@code Bijection} contains the specified mapping.
     * <p>
     * This method is equivalent to running <pre>containsPairing(pairing.getFirst(), pairing.getSecond())</pre>
     * As a result, only one of these two methods should be explicitly defined. If both are overridden,
     * then both implementations must have exactly the same effect on the internal state of this {@code Bijection.}
     *
     * @param pairing The pairing to check
     * @return Whether or not this pairing is present in the bijection
     * @see #containsPairing(Object, Object)
     */
    boolean containsPairing(@NotNull Pairing<A, B> pairing);

    /**
     * {@inheritDoc}
     * This method is identical to {@link #toPairingArray()}, except that
     * this method returns {@code Object[]} instead of {@code Pairing<A, B>[]}.
     */
    @Override
    default @NotNull Object[] toArray() {
        return toPairingArray();
    }
    /**
     * Returns this bijection as an array of {@code Pairing}s.
     * <p>
     * This method must meet all of the requirements and restrictions placed on
     * the returned array by the method {@link Collection#toArray()}.
     *
     * @return This {@code Bijection} as an array of {@code Pairing}s
     */
    @NotNull Pairing<A, B>[] toPairingArray();
    /**
     * Returns this bijection as a pair of arrays.
     * <p>
     * The paired arrays must have equal lengths, with corresponding
     * elements representing a mapping of this {@code Bijection}.
     *
     * @return This {@code Bijection} as an array of {@code Pairing}s
     * @see ImmutablePair
     */
    @NotNull ImmutablePair<A[], B[]> toPairedArray();
    /**
     * Returns this bijection as a 2D array.
     * <p>
     * If the argument {@code makeRowsPairwise} is {@code true},
     * then the returned 2D array will have dimensions {@code Nx2},
     * where {@code N} is the number of mappings in this bijection.
     * Each of the {@code N} rows of the array will correspond to a unique
     * mapping of this {@code Bijection}.
     * <p>
     * If the argument {@code makeRowsPairwise} is {@code false},
     * then the returned 2D array will have dimensions {@code 2xN},
     * where {@code N} is the number of mappings in this bijection.
     * This will essentially be an array of the two arrays returned
     * by {@link #toPairedArray()}.
     *
     * @param makeRowsPairwise Whether to return an {@code Nx2} or {@code 2xN} array
     * @return This {@code Bijection} as a 2D array
     * @see #toPairedArray()
     */
    @NotNull Object[][] toPairedArray(boolean makeRowsPairwise);
    /**
     * {@inheritDoc}
     * <p>
     * Additionally, note that {@code toArray((Pairing<X,Y>[]) new Pairing[0])}
     * is nearly identical in function to {@link #toPairingArray()}.
     *
     * @throws ClassCastException If the parameter type {@code T} is not of type {@code Pairing}
     *                            or has mismatched parameters with this {@code Bijection}
     */
    @SuppressWarnings("unchecked")
    @Override
    default <T> @NotNull T[] toArray(@NotNull T[] a) {
        T[] arr = a.length >= size() ? a : (T[]) Arrays.copyOf(a, size(), a.getClass());
        int ind = 0;
        for (Pairing<A, B> pair : this)
            arr[ind++] = (T) pair;
        while (ind < arr.length)
            arr[ind++] = null;
        return a;
    }

    /**
     * {@inheritDoc}
     * <p>
     * The default implementation simply uses the {@link #addPairing(Pairing)}
     * if the argument to this method can be cast and just returns {@code false} otherwise.
     */
    @Override
    default boolean add(Pairing<A, B> pairing) {
        return pairing != null && addPairing(pairing);
    }
    /**
     * Adds the specified pairing to this {@code Bijection}, if possible.
     * <p>
     * This method is equivalent to running <pre>addPairing(new Pairing&lt;&gt;(obj1, obj2))</pre>
     * As a result, only one of these two methods should be explicitly defined. If both are overridden,
     * then both implementations must have exactly the same effect on the internal state of this {@code Bijection.}
     *
     * @param obj1 The first element of the pairing
     * @param obj2 The second element of the pairing
     * @return Whether or not this {@code Bijection} was modified by this method
     * @see #addPairing(Pairing)
     */
    boolean addPairing(@NotNull A obj1, @NotNull B obj2);
    /**
     * Adds the specified {@code Pairing} to this {@code Bijection}, if possible.
     * <p>
     * This method is equivalent to running <pre>addPairing(pairing.getFirst(), pairing.getSecond())</pre>
     * As a result, only one of these two methods should be explicitly defined. If both are overridden,
     * then both implementations must have exactly the same effect on the internal state of this {@code Bijection.}
     * <p>
     * This method returns {@code false} regardless of whether the method failed due to the defined pairing
     * existing or due to conflicts with other existing pairings. For a method that throws an exception if
     * the new pairing conflicts, use {@link #addPairingOrFail(Pairing)}, or alternatively use
     * {@link #containsPairing(Pairing)} to see if the addition failed due to the pairing already existing.
     *
     * @param pairing The pairing to add
     * @return Whether or not this {@code Bijection} was modified by this method
     * @see #addPairing(Object, Object)
     * @see #addPairingOrFail(Pairing)
     */
    boolean addPairing(@NotNull Pairing<A, B> pairing);
    /**
     * Adds the specified {@code Pairing} to this {@code Bijection}, if possible.
     * <p>
     * This method completes successfully if and only if the pairing does not conflict with any
     * already existing pairings in the bijection. If this is the case, then this method is identical
     * to {@link #addPairing(Pairing)}. However, if this new pairing <i>does</i> conflict with a
     * pre-existing pairing, then this method throws an {@link IllegalStateException} instead.
     *
     * @param pairing The pairing to add
     * @return Whether or not this {@code Bijection} was modified by this method
     * @throws IllegalStateException If the addition failed due to conflicts with pre-existing pairings
     * @see #addPairing(Pairing)
     */
    boolean addPairingOrFail(@NotNull Pairing<A, B> pairing);

    /**
     * {@inheritDoc}
     * <p>
     * The default implementation simply uses the {@link #removePairing(Pairing)}
     * if the argument to this method can be cast and just returns {@code false} otherwise.
     *
     * @throws ClassCastException If the given object is of type {@code Pairing} but with mismatched parameters
     */
    @Override
    default boolean remove(Object o) {
        //noinspection unchecked
        return o instanceof Pairing<?, ?> && removePairing((Pairing<A, B>) o);
    }
    /**
     * Removes the specified pairing from this {@code Bijection}, if possible.
     * <p>
     * This method is equivalent to running <pre>removePairing(new Pairing&lt;&gt;(obj1, obj2))</pre>
     * As a result, only one of these two methods should be explicitly defined. If both are overridden,
     * then both implementations must have exactly the same effect on the internal state of this {@code Bijection.}
     *
     * @param obj1 The first element of the pairing
     * @param obj2 The second element of the pairing
     * @return Whether or not this {@code Bijection} was modified by this method
     * @see #removePairing(Pairing)
     */
    boolean removePairing(@NotNull A obj1, @NotNull B obj2);
    /**
     * Removes the specified {@code Pairing} from this {@code Bijection}, if possible.
     * <p>
     * This method is equivalent to running <pre>removePairing(pairing.getFirst(), pairing.getSecond())</pre>
     * As a result, only one of these two methods should be explicitly defined. If both are overridden,
     * then both implementations must have exactly the same effect on the internal state of this {@code Bijection.}
     *
     * @param pairing The pairing to remove
     * @return Whether or not this {@code Bijection} was modified by this method
     * @see #removePairing(Object, Object)
     */
    boolean removePairing(@NotNull Pairing<A, B> pairing);

    /**
     * Returns an unmodifiable {@code Bijection} backed by the given {@code Bijection}.
     * <p>
     * Changes in the original bijection will be reflected in the newly returned copy.
     *
     * @param bijection The bijection to wrap with an unmodifiability layer
     * @param <X>       The type of elements in the first set of the bijections
     *                  (see the {@code A} parameter for {@code Bijection})
     * @param <Y>       The type of elements in the second set of the bijections
     *                  (see the {@code B} parameter for {@code Bijection})
     * @return An unmodifiable copy of the given {@code Bijection} backed by the original bijection
     */
    static <X, Y> @NotNull Bijection<X, Y> makeUnmodifiable(@NotNull Bijection<X, Y> bijection) {
        return new UnmodifiableBijection<>(bijection);
    }
}
