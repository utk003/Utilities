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

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

/**
 * A wrapper class for {@link Bijection}s that makes them unmodifiable.
 * <p>
 * This class is used in the {@link Bijection#makeUnmodifiable(Bijection)} method.
 *
 * @param <A> The type of objects in the first set of this bijection
 * @param <B> The type of objects in the second set of this bijection
 * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
 * @version June 29, 2021
 * @see Bijection
 * @since 2.0.0
 */
@Unmodifiable
final class UnmodifiableBijection<A, B> extends AbstractBijection<A, B> implements Bijection<A, B> {
    private final @NotNull Bijection<A, B> BACKING_BIJECTION;

    /**
     * Creates a new {@code UnmodifiableBijection} wrapping the specified {@link Bijection}.
     *
     * @param wrapped The {@code Bijection} to make unmodifiable
     */
    public UnmodifiableBijection(@NotNull Bijection<A, B> wrapped) {
        BACKING_BIJECTION = wrapped;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return BACKING_BIJECTION.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @Nullable Pairing<A, B> getByFirst(@NotNull A obj1) {
        return BACKING_BIJECTION.getByFirst(obj1);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public @Nullable Pairing<A, B> getBySecond(@NotNull B obj2) {
        return BACKING_BIJECTION.getBySecond(obj2);
    }

    /**
     * {@inheritDoc}
     * <p>
     * This method always throws a {@link UnsupportedOperationException}.
     *
     * @throws UnsupportedOperationException Always
     */
    @Override
    public boolean add(@NotNull Pairing<A, B> pairing) {
        throw new UnsupportedOperationException("Unmodifiable bijections cannot add new pairings");
    }
    /**
     * {@inheritDoc}
     * <p>
     * This method always throws a {@link UnsupportedOperationException}.
     *
     * @throws UnsupportedOperationException Always
     */
    @Override
    public boolean remove(@NotNull A obj1, @NotNull B obj2) {
        throw new UnsupportedOperationException("Unmodifiable bijections cannot remove pairings");
    }
    /**
     * {@inheritDoc}
     * <p>
     * This method always throws a {@link UnsupportedOperationException}.
     *
     * @throws UnsupportedOperationException Always
     */
    @Override
    public @Nullable Pairing<A, B> removeByFirst(@NotNull A obj1) {
        throw new UnsupportedOperationException("Unmodifiable bijections cannot remove pairings");
    }
    /**
     * {@inheritDoc}
     * <p>
     * This method always throws a {@link UnsupportedOperationException}.
     *
     * @throws UnsupportedOperationException Always
     */
    @Override
    public @Nullable Pairing<A, B> removeBySecond(@NotNull B obj2) {
        throw new UnsupportedOperationException("Unmodifiable bijections cannot remove pairings");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Iterator<Pairing<A, B>> iterator() {
        return new UnmodifiableIterator<>(BACKING_BIJECTION.iterator());
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Iterator<A> iteratorOverFirsts() {
        return new UnmodifiableIterator<>(BACKING_BIJECTION.iteratorOverFirsts());
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Iterator<B> iteratorOverSeconds() {
        return new UnmodifiableIterator<>(BACKING_BIJECTION.iteratorOverSeconds());
    }

    private @Nullable Set<Pairing<A, B>> pairingSet = null;
    private @Nullable Set<A> setOfFirsts = null;
    private @Nullable Set<B> setOfSeconds = null;

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Set<Pairing<A, B>> pairingSet() {
        return pairingSet == null ? pairingSet = Collections.unmodifiableSet(BACKING_BIJECTION.pairingSet()) : pairingSet;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Set<A> setOfFirsts() {
        return setOfFirsts == null ? setOfFirsts = Collections.unmodifiableSet(BACKING_BIJECTION.setOfFirsts()) : setOfFirsts;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Set<B> setOfSeconds() {
        return setOfSeconds == null ? setOfSeconds = Collections.unmodifiableSet(BACKING_BIJECTION.setOfSeconds()) : setOfSeconds;
    }

    /**
     * An iterator for {@link UnmodifiableBijection}s that prevents modifying operations.
     * <p>
     * This class is used by the {@link UnmodifiableBijection#iterator()} method.
     *
     * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
     * @version April 23, 2021
     * @see UnmodifiableBijection
     * @see Iterator
     * @since 2.0.0
     */
    private static class UnmodifiableIterator<E> implements Iterator<E> {
        private final @NotNull Iterator<E> BACKING_ITERATOR;
        public UnmodifiableIterator(@NotNull Iterator<E> backingIterator) {
            BACKING_ITERATOR = backingIterator;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean hasNext() {
            return BACKING_ITERATOR.hasNext();
        }
        /**
         * {@inheritDoc}
         */
        @Override
        public E next() {
            return BACKING_ITERATOR.next();
        }
    }
}
