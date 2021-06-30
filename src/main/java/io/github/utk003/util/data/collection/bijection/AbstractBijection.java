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

import java.util.Iterator;
import java.util.Set;

/**
 * A skeletal implementation of the {@link Bijection} interface, provided
 * to minimize the effort required to implement the interface.
 * <p>
 * Any classes that extend {@code AbstractBijection} will have to implement
 * at least the {@link Bijection#size()}, {@link Bijection#getByFirst(Object)},
 * {@link Bijection#getBySecond(Object)}, {@link Bijection#add(Pairing)},
 * {@link Bijection#remove(Object, Object)}, {@link Bijection#removeByFirst(Object)},
 * {@link Bijection#removeBySecond(Object)}, {@link Bijection#pairingSet()},
 * {@link Bijection#setOfFirsts()}, and {@link Bijection#setOfSeconds()} methods.
 * Additionally, other methods can be re-implemented if the extending class can
 * provide more efficient implementations of those methods.
 *
 * @param <A> The type of objects in the first set of this bijection
 * @param <B> The type of objects in the second set of this bijection
 * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
 * @version June 29, 2021
 * @see Bijection
 * @since 2.0.0
 */
public abstract class AbstractBijection<A, B> implements Bijection<A, B> {
    /**
     * {@inheritDoc}
     */
    @Override
    public abstract int size();

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(@NotNull A obj1, @NotNull B obj2) {
        Pairing<A, B> p1 = getByFirst(obj1), p2 = getBySecond(obj2);
        if (p1 == null && p2 == null)
            return false;

        boolean m1 = p1 != null && obj1.equals(p1.FIRST) && obj2.equals(p1.SECOND),
                m2 = p2 != null && obj1.equals(p2.FIRST) && obj2.equals(p2.SECOND);
        if (m1 != m2)
            throw IllegalBijectiveStateException.lossOfBijectivity();
        return true;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(@NotNull Pairing<A, B> pairing) {
        return contains(pairing.FIRST, pairing.SECOND);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract @Nullable Pairing<A, B> getByFirst(@NotNull A obj1);
    /**
     * {@inheritDoc}
     */
    @Override
    public abstract @Nullable Pairing<A, B> getBySecond(@NotNull B obj2);

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean add(@NotNull A obj1, @NotNull B obj2) {
        return add(new Pairing<>(obj1, obj2));
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public abstract boolean add(@NotNull Pairing<A, B> pairing);

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addOrFail(@NotNull A obj1, @NotNull B obj2) {
        return addOrFail(new Pairing<>(obj1, obj2));
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addOrFail(@NotNull Pairing<A, B> pairing) {
        if (contains(pairing))
            return false;
        if (add(pairing))
            return true;
        throw IllegalBijectiveStateException.failedPairingAddition(pairing);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract boolean remove(@NotNull A obj1, @NotNull B obj2);
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(@NotNull Pairing<A, B> pairing) {
        return remove(pairing.FIRST, pairing.SECOND);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract @Nullable Pairing<A, B> removeByFirst(@NotNull A obj1);
    /**
     * {@inheritDoc}
     */
    @Override
    public abstract @Nullable Pairing<A, B> removeBySecond(@NotNull B obj2);

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract @NotNull Set<Pairing<A, B>> pairingSet();
    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Iterator<Pairing<A, B>> iterator() {
        return pairingSet().iterator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract @NotNull Set<A> setOfFirsts();
    /**
     * {@inheritDoc}
     */
    @Override
    public abstract @NotNull Set<B> setOfSeconds();

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Iterator<A> iteratorOverFirsts() {
        return setOfFirsts().iterator();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Iterator<B> iteratorOverSeconds() {
        return setOfSeconds().iterator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Pairing<A, B>[] toArray() {
        //noinspection unchecked
        Pairing<A, B>[] arr = (Pairing<A, B>[]) new Pairing[size()];
        int ind = 0;
        for (Pairing<A, B> pair : this)
            arr[ind++] = pair;
        return arr;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull ImmutablePair<A[], B[]> toArrayPair() {
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
    public @NotNull Object[][] to2DArray(boolean makeRowsPairwise) {
        if (makeRowsPairwise) {
            Object[][] arrForm = new Object[size()][];
            int ind = 0;
            for (Pairing<A, B> pair : this)
                arrForm[ind++] = new Object[]{pair.FIRST, pair.SECOND};
            return arrForm;
        } else {
            ImmutablePair<A[], B[]> pair = toArrayPair();
            return new Object[][]{pair.FIRST, pair.SECOND};
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        Iterator<Pairing<A, B>> it = iterator();
        if (!it.hasNext())
            return "{}";

        StringBuilder builder = new StringBuilder("{").append(it.next());
        while (it.hasNext())
            builder.append(", ").append(it.next());
        return builder + "}";
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = 0;
        for (Pairing<A, B> pairing : this)
            hash ^= pairing.hashCode();
        return hash;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        return o instanceof Bijection<?, ?> && pairingSet().equals(((Bijection<?, ?>) o).pairingSet());
    }
}
