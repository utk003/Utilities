package io.github.utk003.util.data.collection.bijection;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Iterator;

/**
 * A wrapper class for {@link Bijection}s that makes them unmodifiable.
 * <p>
 * This class is used in the {@link Bijection#makeUnmodifiable(Bijection)} method.
 *
 * @param <A> The type of objects in the first set of this bijection
 * @param <B> The type of objects in the second set of this bijection
 * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
 * @version April 23, 2021
 * @see Bijection
 * @since 2.0.0
 */
@Unmodifiable
class UnmodifiableBijection<A, B> extends AbstractBijection<A, B> implements Bijection<A, B> {
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
    private class UnmodifiableIterator implements Iterator<Pairing<A, B>> {
        private final @NotNull Iterator<Pairing<A, B>> BACKING_ITERATOR = BACKING_BIJECTION.iterator();

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
        public Pairing<A, B> next() {
            return BACKING_ITERATOR.next();
        }
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
    public @NotNull Iterator<Pairing<A, B>> iterator() {
        return new UnmodifiableIterator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsPairing(@NotNull Pairing<A, B> pairing) {
        return BACKING_BIJECTION.containsPairing(pairing);
    }
    /**
     * {@inheritDoc}
     * <p>
     * This method always throws a {@link UnsupportedOperationException}.
     *
     * @throws UnsupportedOperationException Always
     */
    @Override
    public boolean addPairing(@NotNull Pairing<A, B> pairing) {
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
    public boolean removePairing(@NotNull Pairing<A, B> pairing) {
        throw new UnsupportedOperationException("Unmodifiable bijections cannot remove pairings");
    }
}
