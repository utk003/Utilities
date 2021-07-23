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

import io.github.utk003.util.misc.annotations.ScheduledForRelease;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * An {@link Iterator} that returns its elements in a random order
 * with respect to a default "sorted" order defined by the objects'
 * prior ordering or their prior iterator's returned ordering.
 * <p>
 * This class scrambles the iteration order of other iterators,
 * and, by extension, the iterators of {@link Iterable}s (and
 * therefore {@link Collections}, too). It does so by extracting
 * all the elements of an iterator into an array, scrambling the
 * arrays contents via {@link Randomizer}, and iterating over the
 * scrambled elements.
 * <p>
 * As per the {@code Iterator} guarantee, each object will only be
 * returned once for each appearance in the collection of iterated
 * objects. An object will be returned multiple times if and only if
 * it appeared multiple times in the original collection of objects.
 *
 * @param <E> The type of the objects this {@code Iterator} will return
 * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
 * @version July 22, 2021
 * @since 3.0.0
 */
@ScheduledForRelease(inVersion = "v3.0.0")
public final class RandomizedIterator<E> implements Iterator<E> {
    /**
     * A cached empty {@code Object[]} array.
     */
    private static final @NotNull Object[] EMPTY_ARRAY = new Object[0];
    /**
     * The array of all elements that have been or will be returned by this {@code RandomizedIterator}.
     */
    private final @NotNull E[] ARR;

    /**
     * Creates a new {@code RandomizedIterator} over the given objects.
     *
     * @param objs The objects to randomly iterate over
     */
    @SafeVarargs
    public RandomizedIterator(@NotNull E... objs) {
        this(Arrays.asList(objs).iterator());
    }
    /**
     * Creates a new {@code RandomizedIterator} over the given {@link Collection}.
     *
     * @param collection The collection of objects to randomly iterate over
     */
    public RandomizedIterator(@NotNull Collection<E> collection) {
        this(collection.iterator());
    }
    /**
     * Creates a new {@code RandomizedIterator} over the given {@link Iterable}.
     *
     * @param iterable The {@code Iterable} of objects to randomly iterate over
     */
    public RandomizedIterator(@NotNull Iterable<E> iterable) {
        this(iterable.iterator());
    }
    /**
     * Creates a new {@code RandomizedIterator} wrapping the given {@link Iterator}.
     *
     * @param it The iterator of objects to wrap
     */
    public RandomizedIterator(@NotNull Iterator<E> it) {
        List<E> list = new LinkedList<>();
        while (it.hasNext())
            list.add(it.next());
        //noinspection unchecked
        ARR = list.toArray((E[]) EMPTY_ARRAY);
        Randomizer.randomize(ARR);
    }

    /**
     * The current index of this {@code RandomizedIterator} with respect to {@link #ARR}.
     */
    private int ind = 0;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasNext() {
        return ind < ARR.length;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public E next() {
        return ARR[ind++];
    }
}
