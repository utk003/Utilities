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
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * A custom {@code Collection} that represents a bijective mapping.
 * <p>
 * A {@code Bijection} maps two sets of objects in a 1:1 mapping. For more information on
 * bijections, check out <a href="https://en.wikipedia.org/wiki/Bijection" target="_top">https://en.wikipedia.org/wiki/Bijection</a>.
 *
 * @param <A> The type of objects in the first set of this bijection
 * @param <B> The type of objects in the second set of this bijection
 * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
 * @version June 29, 2021
 * @see Collection
 * @since 2.0.0
 */
public interface Bijection<A, B> extends Iterable<Bijection.Pairing<A, B>> {
    /**
     * Returns the number of {@link Pairing}s in this {@code Bijection}.
     *
     * @return The number of pairings in this {@code Bijection}
     * @see Pairing
     */
    int size();

    /**
     * Returns whether or not this {@code Bijection} contains the specified mapping.
     * <p>
     * This method is equivalent to running <pre>contains(new Pairing&lt;&gt;(obj1, obj2))}</pre>
     * As a result, only one of these two methods should be explicitly defined. If both are overridden,
     * then both implementations must have exactly the same effect on the internal state of this {@code Bijection.}
     *
     * @param obj1 The first element of the pairing
     * @param obj2 The second element of the pairing
     * @return Whether or not this pair is mapped together in the bijection
     * @see #contains(Pairing)
     */
    boolean contains(@NotNull A obj1, @NotNull B obj2);
    /**
     * Returns whether or not this {@code Bijection} contains the specified {@link Pairing}.
     * <p>
     * This method is equivalent to running <pre>contains(pairing.getFirst(), pairing.getSecond())</pre>
     * As a result, only one of these two methods should be explicitly defined. If both are overridden,
     * then both implementations must have exactly the same effect on the internal state of this {@code Bijection.}
     *
     * @param pairing The pairing to check
     * @return Whether or not this pairing is present in the bijection
     * @see #contains(Object, Object)
     * @see Pairing
     */
    boolean contains(@NotNull Pairing<A, B> pairing);

    /**
     * Returns the pairing whose first element matches the given object, if one exists.
     * <p>
     * For an identical method acting based on the second element of the pairing,
     * use {@link #getBySecond(Object)} instead.
     *
     * @param obj1 The first element of the pairing
     * @return The pairing with {@code obj1} as its first element, if one exists; otherwise, {@code null}
     * @see #getBySecond(Object)
     */
    @Nullable Pairing<A, B> getByFirst(@NotNull A obj1);
    /**
     * Returns the pairing whose second element matches the given object, if one exists.
     * <p>
     * For an identical method acting based on the first element of the pairing,
     * use {@link #getByFirst(Object)} instead.
     *
     * @param obj2 The second element of the pairing
     * @return The pairing with {@code obj2} as its second element, if one exists; otherwise, {@code null}
     * @see #getByFirst(Object)
     */
    @Nullable Pairing<A, B> getBySecond(@NotNull B obj2);

    /**
     * Adds the specified pairing to this {@code Bijection}, if possible.
     * <p>
     * This method is equivalent to running <pre>add(new Pairing&lt;&gt;(obj1, obj2))</pre>
     * As a result, only one of these two methods should be explicitly defined. If both are overridden,
     * then both implementations must have exactly the same effect on the internal state of this {@code Bijection.}
     *
     * @param obj1 The first element of the pairing
     * @param obj2 The second element of the pairing
     * @return Whether or not this {@code Bijection} was modified by this method
     * @see #add(Pairing)
     */
    boolean add(@NotNull A obj1, @NotNull B obj2);
    /**
     * Adds the specified {@code Pairing} to this {@code Bijection}, if possible.
     * <p>
     * This method is equivalent to running <pre>add(pairing.getFirst(), pairing.getSecond())</pre>
     * As a result, only one of these two methods should be explicitly defined. If both are overridden,
     * then both implementations must have exactly the same effect on the internal state of this {@code Bijection.}
     * <p>
     * This method returns {@code false} regardless of whether the method failed due to the defined pairing
     * existing or due to conflicts with other existing pairings. For a method that throws an exception if
     * the new pairing conflicts, use {@link #addOrFail(Pairing)}, or alternatively use
     * {@link #contains(Pairing)} to see if the addition failed due to the pairing already existing.
     *
     * @param pairing The pairing to add
     * @return Whether or not this {@code Bijection} was modified by this method
     * @see #add(Object, Object)
     * @see #addOrFail(Pairing)
     */
    boolean add(@NotNull Pairing<A, B> pairing);

    /**
     * Adds the specified pairing to this {@code Bijection}, if possible.
     * <p>
     * This method completes successfully if and only if the pairing does not conflict with any
     * already existing pairings in the bijection. If this is the case, then this method is identical
     * to {@link #add(Object, Object)}. However, if this new pairing <i>does</i> conflict with a
     * pre-existing pairing, then this method throws an {@link IllegalBijectiveStateException} instead.
     *
     * @param obj1 The first element of the pairing
     * @param obj2 The second element of the pairing
     * @return Whether or not this {@code Bijection} was modified by this method
     * @throws IllegalBijectiveStateException If the addition failed due to conflicts with pre-existing pairings
     * @see #add(Pairing)
     * @see IllegalBijectiveStateException#failedPairingAddition
     */
    boolean addOrFail(@NotNull A obj1, @NotNull B obj2);
    /**
     * Adds the specified {@code Pairing} to this {@code Bijection}, if possible.
     * <p>
     * This method completes successfully if and only if the pairing does not conflict with any
     * already existing pairings in the bijection. If this is the case, then this method is identical
     * to {@link #add(Pairing)}. However, if this new pairing <i>does</i> conflict with a
     * pre-existing pairing, then this method throws an {@link IllegalBijectiveStateException} instead.
     *
     * @param pairing The pairing to add
     * @return Whether or not this {@code Bijection} was modified by this method
     * @throws IllegalBijectiveStateException If the addition failed due to conflicts with pre-existing pairings
     * @see #add(Pairing)
     * @see IllegalBijectiveStateException#failedPairingAddition
     */
    boolean addOrFail(@NotNull Pairing<A, B> pairing);

    /**
     * Removes the specified pairing from this {@code Bijection}, if possible.
     * <p>
     * This method is equivalent to running <pre>remove(new Pairing&lt;&gt;(obj1, obj2))</pre>
     * As a result, only one of these two methods should be explicitly defined. If both are overridden,
     * then both implementations must have exactly the same effect on the internal state of this {@code Bijection.}
     *
     * @param obj1 The first element of the pairing
     * @param obj2 The second element of the pairing
     * @return Whether or not this {@code Bijection} was modified by this method
     * @see #remove(Pairing)
     */
    boolean remove(@NotNull A obj1, @NotNull B obj2);
    /**
     * Removes the specified {@code Pairing} from this {@code Bijection}, if possible.
     * <p>
     * This method is equivalent to running <pre>remove(pairing.getFirst(), pairing.getSecond())</pre>
     * As a result, only one of these two methods should be explicitly defined. If both are overridden,
     * then both implementations must have exactly the same effect on the internal state of this {@code Bijection.}
     *
     * @param pairing The pairing to remove
     * @return Whether or not this {@code Bijection} was modified by this method
     * @see #remove(Object, Object)
     */
    boolean remove(@NotNull Pairing<A, B> pairing);

    /**
     * Removes the pairing whose first element matches the given object, if one exists.
     * <p>
     * For an identical method acting based on the second element of the pairing,
     * use {@link #removeBySecond(Object)} instead.
     *
     * @param obj1 The first element of the pairing
     * @return The removed pairing with {@code obj1} as its first element, if one exists; otherwise, {@code null}
     * @see #removeBySecond(Object)
     */
    @Nullable Pairing<A, B> removeByFirst(@NotNull A obj1);
    /**
     * Removes the pairing whose second element matches the given object, if one exists
     * <p>
     * For an identical method acting based on the first element of the pairing,
     * use {@link #removeByFirst(Object)} instead.
     *
     * @param obj2 The second element of the pairing
     * @return The removed pairing with {@code obj2} as its second element, if one exists; otherwise, {@code null}
     * @see #removeByFirst(Object)
     */
    @Nullable Pairing<A, B> removeBySecond(@NotNull B obj2);

    // --------------------------------------------------------------------------------------- //

    /**
     * Returns an {@code Iterator} over all pairings in this {@code Bijection}.
     *
     * @return An {@code Iterator} of objects of type {@code Pairing<A,B>}
     */
    @Override
    @NotNull Iterator<Pairing<A, B>> iterator();

    /**
     * Returns an {@code Iterator} over the first elements of all pairings in this {@code Bijection}.
     *
     * @return An {@code Iterator} of objects of type {@code A}
     */
    @NotNull Iterator<A> iteratorOverFirsts();
    /**
     * Returns an {@code Iterator} over the second elements of all pairings in this {@code Bijection}.
     *
     * @return An {@code Iterator} of objects of type {@code B}
     */
    @NotNull Iterator<B> iteratorOverSeconds();

    /**
     * Returns a {@code Set} of all the pairings in this {@code Bijection}.
     * <p>
     * This {@code Set} is backed by this {@code Bijection}. In other words, the set will reflect
     * changes made to the bijection, and the bijection will reflect changes made to the set.
     *
     * @return A set of all the pairings in this {@code Bijection}
     */
    @NotNull Set<Pairing<A, B>> pairingSet();

    /**
     * Returns a {@code Set} of the first elements of all pairings in this {@code Bijection}.
     * <p>
     * This {@code Set} is backed by this {@code Bijection}. In other words, the set will reflect
     * changes made to the bijection, and the bijection will reflect changes made to the set.
     *
     * @return A set of the first elements of all pairings in this {@code Bijection}
     */
    @NotNull Set<A> setOfFirsts();
    /**
     * Returns a {@code Set} of the second elements of all pairings in this {@code Bijection}.
     * <p>
     * This {@code Set} is backed by this {@code Bijection}. In other words, the set will reflect
     * changes made to the bijection, and the bijection will reflect changes made to the set.
     *
     * @return A set of the second elements of all pairings in this {@code Bijection}
     */
    @NotNull Set<B> setOfSeconds();

    // --------------------------------------------------------------------------------------- //

    /**
     * Returns this bijection as an array of {@code Pairing}s.
     *
     * @return This {@code Bijection} as an array of {@code Pairing}s
     */
    @NotNull Pairing<A, B>[] toArray();
    /**
     * Returns this bijection as an {@link ImmutablePair} of arrays.
     * <p>
     * The paired arrays must have equal lengths, with corresponding
     * elements representing a mapping of this {@code Bijection}.
     *
     * @return This {@code Bijection} as an array of {@code Pairing}s
     * @see ImmutablePair
     */
    @NotNull ImmutablePair<A[], B[]> toArrayPair();
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
     * by {@link #toArrayPair()}.
     *
     * @param makeRowsPairwise Whether to return an {@code Nx2} or {@code 2xN} array
     * @return This {@code Bijection} as a 2D array
     * @see #toArrayPair()
     */
    @NotNull Object[][] to2DArray(boolean makeRowsPairwise);

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

    /**
     * A utility class representing a unique 1:1 mapping between two elements in a bijection.
     * <p>
     * This class plays a very similar role to what {@link java.util.Map.Entry} does for {@link java.util.Map}.
     *
     * @param <A> The type of the first element in this bijective mapping
     * @param <B> The type of the second element in this bijective mapping
     * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
     * @version April 23, 2021
     * @see java.util.Map.Entry
     * @since 2.0.0
     */
    final class Pairing<A, B> {
        /**
         * A public reference to the first object in this {@code Pairing}.
         */
        final @NotNull A FIRST;
        /**
         * A public reference to the second object in this {@code Pairing}.
         */
        final @NotNull B SECOND;

        /**
         * Creates a new bijection mapping between the specified elements
         *
         * @param first  The first element of the {@code Pairing}
         * @param second The second element of the {@code Pairing}
         */
        public Pairing(@NotNull A first, @NotNull B second) {
            FIRST = first;
            SECOND = second;
        }

        /**
         * Returns the first element of this {@code Pairing}.
         *
         * @return The first element of this pairing
         * @see #FIRST
         */
        public @NotNull A getFirst() {
            return FIRST;
        }
        /**
         * Returns the second element of this {@code Pairing}.
         *
         * @return The second element of this pairing
         * @see #SECOND
         */
        public @NotNull B getSecond() {
            return SECOND;
        }

        /**
         * Returns the first element of the given bijective mapping.
         *
         * @param <T>     The type of the first element in the {@code Pairing}
         * @param pairing The {@code Pairing} whose first element should be retrieved
         * @return The first element of the pairing, if the pairing exists; otherwise, {@code null}
         */
        public static <T> @Nullable T getFirst(@Nullable Pairing<T, ?> pairing) {
            return pairing != null ? pairing.FIRST : null;
        }
        /**
         * Returns the second element of the given bijective mapping.
         *
         * @param <T>     The type of the second element in the {@code Pairing}
         * @param pairing The {@code Pairing} whose second element should be retrieved
         * @return The second element of the pairing, if the pairing exists; otherwise, {@code null}
         */
        public static <T> @Nullable T getSecond(@Nullable Pairing<?, T> pairing) {
            return pairing != null ? pairing.SECOND : null;
        }

        /**
         * Returns the specified element of this pairing
         *
         * @param <T>   The type of the object to be returned
         * @param first Whether to return the first element of this pairing or the second
         * @return The first element of this pairing if the argument is {@code true};
         * otherwise, the second element of this pairing
         * @throws ClassCastException If the parametrized type {@code T} does not correspond to type {@code A}
         *                            or {@code B}, depending on whether the method argument is {@code true}
         *                            or {@code false}, respectively.
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
}
