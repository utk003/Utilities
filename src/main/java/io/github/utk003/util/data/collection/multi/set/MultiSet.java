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

import io.github.utk003.util.misc.annotations.RequiresDocumentation;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@RequiresDocumentation
public interface MultiSet<E> extends Collection<E> {
    int count(E obj);
    int countObj(Object obj);

    boolean add(E e, int count);
    int remove(E e, int count);
    int removeAll(E e);

    // @see Map#keySet()
    @NotNull Set<E> uniqueElements();

    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull E[] toArray();

    /**
     * Returns an unmodifiable {@code MultiSet} backed by the given {@code MultiSet}.
     * <p>
     * Changes in the original multiset will be reflected in the newly returned multiset.
     *
     * @param multiSet The multiset to wrap with an unmodifiability layer
     * @param <E>      The type of the elements in both multisets
     * @return An unmodifiable copy of the given {@code MultiSet} backed by the original multiset
     */
    static <E> @NotNull MultiSet<E> makeUnmodifiable(@NotNull MultiSet<E> multiSet) {
        return multiSet instanceof UnmodifiableMultiSet ? multiSet : new UnmodifiableMultiSet<>(multiSet);
    }
}
