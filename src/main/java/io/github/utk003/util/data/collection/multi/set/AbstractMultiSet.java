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

import java.util.*;

/**
 * An abstract implementation of some of the methods provided by {@link MultiSet}.
 * <p>
 * This class implements some of the simpler methods provided by {@code MultiSet},
 * primarily by expressing these functionalities in terms of other, more generalized
 * methods. For example, the {@link #add(Object)} is equivalent to {@link #add(Object, int)}
 * with a {@code count} of {@code 1}.
 *
 * @param <E> The class type of the elements contained in this {@code AbstractMultiSet}
 * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
 * @version June 11, 2021
 * @see MultiSet
 * @since 2.1.0
 */
public abstract class AbstractMultiSet<E> extends AbstractCollection<E> implements MultiSet<E> {
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(Object o) {
        //noinspection unchecked
        return count((E) o) != 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int countObj(Object o) {
        //noinspection unchecked
        return count((E) o);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean add(E e) {
        return add(e, 1);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(Object o) {
        //noinspection unchecked
        return remove((E) o, 1) != 0;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public int removeAll(E e) {
        return remove(e, count(e));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull E[] toArray() {
        //noinspection unchecked
        return (E[]) super.toArray();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull String toString() {
        Iterator<E> it = iterator();
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
        for (E obj : uniqueElements())
            hash ^= obj.hashCode() ^ count(obj);
        return hash;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MultiSet<?>))
            return false;

        MultiSet<?> mset = (MultiSet<?>) o;
        if (size() != mset.size())
            return false;
        Set<E> myUnique = uniqueElements();
        if (myUnique.size() != mset.uniqueElements().size())
            return false;

        for (E obj : myUnique)
            if (count(obj) != mset.countObj(obj))
                return false;
        return true;
    }
}
