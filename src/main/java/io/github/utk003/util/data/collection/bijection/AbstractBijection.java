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

import java.util.AbstractCollection;
import java.util.Iterator;

/**
 * A skeletal implementation of the {@link Bijection} interface, provided
 * to minimize the effort required to implement the interface.
 * <p>
 * Any classes that extend {@code AbstractBijection} must implement the
 * {@link Bijection#size()}, {@link Bijection#iterator()}, {@link Bijection#containsPairing(Pairing)},
 * {@link Bijection#addPairing(Pairing)}, and {@link Bijection#removePairing(Pairing)} methods.
 * Additionally, other methods may (and probably should) be re-implemented if the underlying
 * nature of the subclass makes a more efficient implementation possible.
 *
 * @param <A> The type of objects in the first set of this bijection
 * @param <B> The type of objects in the second set of this bijection
 * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
 * @version April 23, 2021
 * @see Bijection
 * @since 2.0.0
 */
public abstract class AbstractBijection<A, B> extends AbstractCollection<Bijection.Pairing<A, B>> implements Bijection<A, B> {
    /**
     * A utility error message for any errors involving loss of bijectivity.
     */
    protected static final @NotNull String LOSS_OF_BIJECTIVITY_ERROR_MESSAGE = "Internal Error in Bijection: Critical loss of bijectivity";

    /*
     * io.github.utk003.util.data.collection.bijection.Bijection methods that will be overridden by subclasses
     * -------------------------------------------------------------------------------------------------------
     * int size();
     * @NotNull Iterator<Pairing<A, B>> iterator();
     *
     * boolean containsPairing(@NotNull Pairing<A, B> pairing);
     * boolean addPairing(@NotNull Pairing<A, B> pairing);
     * boolean removePairing(@NotNull Pairing<A, B> pairing);
     */

    /*
     * java.util.Collection methods overridden by java.util.AbstractCollection
     * -----------------------------------------------------------------------
     * boolean containsAll(Collection<?> c);
     * boolean addAll(Collection<? extends Pairing<A, B>> c);
     * boolean removeAll(Collection<?> c);
     * boolean retainAll(Collection<?> c);
     *
     * void clear();
     */

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract int size();
    /**
     * {@inheritDoc}
     */
    @Override
    public abstract @NotNull Iterator<Pairing<A, B>> iterator();

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(Object o) {
        // force use of Bijection default instead of AbstractCollections definition
        return ((Bijection<A, B>) this).contains(o);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsPairing(@NotNull A obj1, @NotNull B obj2) {
        return containsPairing(new Pairing<>(obj1, obj2));
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public abstract boolean containsPairing(@NotNull Pairing<A, B> pairing);

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Pairing<A, B>[] toPairingArray() {
        //noinspection unchecked
        Pairing<A, B>[] arrForm = (Pairing<A, B>[]) new Pairing[size()];
        int ind = 0;
        for (Pairing<A, B> pair : this)
            arrForm[ind++] = pair;
        return arrForm;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull ImmutablePair<A[], B[]> toPairedArray() {
        //noinspection unchecked
        A[] arrA = (A[]) new Object[size()];
        //noinspection unchecked
        B[] arrB = (B[]) new Object[size()];

        int ind = 0;
        for (Pairing<A, B> pair : this) {
            arrA[ind] = pair.FIRST;
            arrB[ind] = pair.SECOND;
            ind++;
        }
        return new ImmutablePair<>(arrA, arrB);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Object[][] toPairedArray(boolean makeRowsPairwise) {
        if (makeRowsPairwise) {
            Object[][] arrForm = new Object[size()][];
            int ind = 0;
            for (Pairing<A, B> pair : this)
                arrForm[ind++] = new Object[]{pair.FIRST, pair.SECOND};
            return arrForm;
        } else {
            ImmutablePair<A[], B[]> pair = toPairedArray();
            return new Object[][]{pair.FIRST, pair.SECOND};
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean add(Pairing<A, B> pairing) {
        // force use of Bijection default instead of AbstractCollections definition
        return ((Bijection<A, B>) this).add(pairing);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addPairing(@NotNull A obj1, @NotNull B obj2) {
        return addPairing(new Pairing<>(obj1, obj2));
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public abstract boolean addPairing(@NotNull Pairing<A, B> pairing);
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addPairingOrFail(@NotNull Pairing<A, B> pairing) {
        if (containsPairing(pairing))
            return false;
        if (addPairing(pairing))
            return true;
        throw new IllegalStateException("Failure to add pair due to pre-existing pairings");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(Object o) {
        // force use of Bijection default instead of AbstractCollections definition
        return ((Bijection<A, B>) this).remove(o);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removePairing(@NotNull A obj1, @NotNull B obj2) {
        return removePairing(new Pairing<>(obj1, obj2));
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public abstract boolean removePairing(@NotNull Pairing<A, B> pairing);
}
