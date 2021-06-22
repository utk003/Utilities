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

package io.github.utk003.util.data.collection.multi.set;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * A {@link Map}-backed implementation of the {@link MultiSet}.
 * <p>
 * This class uses a {@code E}-to-{@code Integer} map to keep track
 * of multiple copies of an object being present in this multiset.
 * The map itself is generic, so any class that implements {@code Map}
 * can be provided into either of the two constructors.
 *
 * @param <E> The class type of the elements contained in this {@code MapBackedMultiSet}
 * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
 * @version June 11, 2021
 * @see MultiSet
 * @since 2.1.0
 */
public class MapBackedMultiSet<E> extends AbstractMultiSet<E> implements MultiSet<E> {
    /**
     * The {@link Map} backing this {@code MapBackedMultiSet}.
     */
    private final Map<E, Integer> MAP;
    /**
     * The size and modification counter for this {@code MapBackedMultiSet}.
     */
    private int size = 0, modCount = 0;

    /**
     * Creates a new {@code MapBackedMultiSet} backed by a map of the specified class.
     * <p>
     * This method takes a {@link Class} object to instantiate
     * the map that will hold this multiset's elements.
     *
     * @param underlyingMapType The {@code Class} object of type {@code Map<E, Integer>}
     * @throws IllegalAccessException If one is thrown by the {@link Class#newInstance()} method
     * @throws InstantiationException If one is thrown by the {@link Class#newInstance()} method
     */
    @SuppressWarnings("rawtypes")
    public MapBackedMultiSet(@NotNull Class<? extends Map> underlyingMapType)
            throws IllegalAccessException, InstantiationException {
        //noinspection unchecked
        this(underlyingMapType.newInstance());
    }
    /**
     * Creates a new {@code MapBackedMultiSet} backed by the given map.
     *
     * @param underlyingMap The {@code Map} object mapping objects of type {@code E} to {@code Integer}s
     */
    public MapBackedMultiSet(@NotNull Map<E, Integer> underlyingMap) {
        MAP = underlyingMap;
        for (int s : underlyingMap.values())
            size += s;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int count(E obj) {
        return MAP.getOrDefault(obj, 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean add(E obj, int count) {
        MAP.put(obj, count(obj) + count);

        size += count;
        modCount++;

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int remove(E obj, int count) {
        int rem = removeHelper(obj, count);

        size -= rem;
        modCount++;

        return rem;
    }
    /**
     * Removes the specified object the specified number of times.
     * <p>
     * This method has the exact same documentation as {@link #remove(Object, int)}
     *
     * @param obj   The object to remove
     * @param count The target number of objects to remove
     * @return The actual number of objects removed
     * @see #remove(Object, int)
     */
    private int removeHelper(E obj, int count) {
        int curr = count(obj);
        if (curr <= count) {
            MAP.remove(obj);
            return curr;
        } else {
            MAP.put(obj, curr - count);
            return count;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Set<E> uniqueElements() {
        return MAP.keySet();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Iterator<E> iterator() {
        return new Itr();
    }

    /**
     * A simple iterator over the elements of a {@link MapBackedMultiSet}.
     *
     * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
     * @version June 11, 2021
     * @see MapBackedMultiSet
     * @since 2.1.0
     */
    private class Itr implements Iterator<E> {
        private final @NotNull java.util.Iterator<Map.Entry<E, Integer>> IT = MAP.entrySet().iterator();
        private int expectedModCount = modCount;

        private @Nullable Map.Entry<E, Integer> currEntry = null;
        private boolean hasCalledNext = false;
        private int currentIndex = 0;

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean hasNext() {
            return currentIndex != 0 || IT.hasNext();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public E next() {
            if (!hasNext())
                throw new IllegalStateException();

            if (currEntry == null) {
                if (currentIndex != 0)
                    throw new IllegalStateException();

                currEntry = IT.next();
                currentIndex = currEntry.getValue();
            } else if (currentIndex == 0) {
                currEntry = IT.next();
                currentIndex = currEntry.getValue();
            }
            if (currentIndex == 0)
                throw new IllegalStateException();

            hasCalledNext = true;
            currentIndex--;

            return currEntry.getKey();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void remove() {
            if (!hasCalledNext)
                throw new IllegalStateException();
            hasCalledNext = false;

            if (expectedModCount != modCount)
                throw new ConcurrentModificationException();

            if (currEntry == null)
                throw new IllegalStateException();

            int currCount = currEntry.getValue();
            if (currCount == 0)
                throw new IllegalStateException();

            if (--currCount == 0) {
                IT.remove();
                currEntry = null;
            } else
                currEntry.setValue(currCount);

            size--;
            modCount++;
            expectedModCount++;
        }
    }
}
