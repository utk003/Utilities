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

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

/**
 * A default implementation of an unmodifiable {@link MultiSet}.
 *
 * @param <E> The class type of the elements contained in this {@code UnmodifiableMultiSet}
 * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
 * @version June 11, 2021
 * @see MultiSet
 * @since 2.1.0
 */
final class UnmodifiableMultiSet<E> extends AbstractMultiSet<E> implements MultiSet<E> {
    /**
     * The wrapped {@link MultiSet} by this {@code UnmodifiableMultiSet}.
     */
    private final @NotNull MultiSet<E> MULTI_SET;

    /**
     * Creates a new {@code UnmodifiableMultiSet} wrapping the given {@link MultiSet}.
     *
     * @param wrapping The {@code MultiSet} to wrap
     * @see MultiSet
     */
    public UnmodifiableMultiSet(@NotNull MultiSet<E> wrapping) {
        MULTI_SET = wrapping;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return MULTI_SET.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int count(E obj) {
        return MULTI_SET.count(obj);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean add(E e, int count) {
        throw new UnsupportedOperationException("Unmodifiable multisets cannot add elements");
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public int remove(E e, int count) {
        throw new UnsupportedOperationException("Unmodifiable multisets cannot remove elements");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Set<E> uniqueElements() {
        return Collections.unmodifiableSet(MULTI_SET.uniqueElements());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<E> iterator() {
        return new UnmodifiableItr();
    }

    /**
     * An iterator for {@link UnmodifiableMultiSet}s that prevents modifying operations.
     * <p>
     * This class is used by the {@link UnmodifiableMultiSet#iterator()} method.
     *
     * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
     * @version June 11, 2021
     * @see UnmodifiableMultiSet
     * @see Iterator
     * @since 2.1.0
     */
    private class UnmodifiableItr implements Iterator<E> {
        private final @NotNull Iterator<E> IT = MULTI_SET.iterator();

        @Override
        public boolean hasNext() {
            return IT.hasNext();
        }
        @Override
        public E next() {
            return IT.next();
        }
    }
}
