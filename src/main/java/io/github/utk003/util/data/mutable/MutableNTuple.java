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

package io.github.utk003.util.data.mutable;

import java.util.Arrays;

/**
 * A mutable <code>N</code>-tuple of objects of type {@code T}.
 * <p>
 * The {@code MutableNTuple<T>} class is primarily designed for use in
 * parameterized applications such as in {@code List}s, {@code Set}s, and
 * {@code Map}s. This class provides a mutable <code>N</code>-tuple of
 * any two objects.
 * <p>
 * Use {@link io.github.utk003.util.data.immutable.ImmutableNTuple}
 * for immutable <code>N</code>-tuples.
 *
 * @param <T> The general type of the objects in the <code>N</code>-tuple
 * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
 * @version February 8, 2021
 * @see io.github.utk003.util.data.immutable.ImmutableNTuple
 * @since 1.0.6
 */
public class MutableNTuple<T> {
    /**
     * Publicly accessible array of the elements contained in this {@code MutableNTuple}
     */
    public final T[] ELEMENTS;

    /**
     * Publicly accessible value of the size of this {@code MutableNTuple}.
     * <p>
     * In other words, the value of <code>N</code> for this {@code MutableNTuple}.
     */
    public final int SIZE;

    /**
     * Creates an empty {@code MutableNTuple} of the specified length.
     * <p>
     * This is equivalent to creating the <code>N</code>-tuple {@code (null,null,...,null)}
     *
     * @param size The size of the <code>N</code>-tuple (the value of <code>N</code>)
     */
    @SuppressWarnings("unchecked")
    public MutableNTuple(int size) {
        SIZE = size;
        ELEMENTS = (T[]) new Object[SIZE];
    }
    /**
     * Creates a new {@code MutableNTuple} initialized to the values provided as arguments.
     *
     * @param elements The elements of the <code>N</code>-tuple
     */
    @SuppressWarnings("unchecked")
    public MutableNTuple(T... elements) {
        this(elements.length);
        System.arraycopy(elements, 0, ELEMENTS, 0, SIZE);
    }

    /**
     * Returns the element of this <code>N</code>-tuple at the specified index
     * <p>
     * Indexing is <code>0</code>-indexed.
     *
     * @param index The index of the element to get
     * @return The element at that index, if it exists
     * @throws ArrayIndexOutOfBoundsException If the index is less than <code>0</code> or
     *                                        greater than equal to <code>N</code>
     */
    public T get(int index) {
        return ELEMENTS[index];
    }

    /**
     * Replaces the element of this <code>N</code>-tuple at the specified index
     * <p>
     * Indexing is <code>0</code>-indexed.
     *
     * @param index   The index of the element to replace
     * @param element The new value of the element
     * @throws ArrayIndexOutOfBoundsException If the index is less than <code>0</code> or
     *                                        greater than equal to <code>N</code>
     */
    public void set(int index, T element) {
        ELEMENTS[index] = element;
    }

    @Override // JavaDoc inherited from super method
    public String toString() {
        if (SIZE == 0)
            return "{}";

        StringBuilder builder = new StringBuilder();
        for (T element : ELEMENTS)
            builder.append(",").append(element);
        return "(" + builder.substring(1) + ")";
    }

    @Override // JavaDoc inherited from super method
    public boolean equals(Object o) {
        return this == o || o instanceof MutableNTuple && Arrays.equals(ELEMENTS, ((MutableNTuple<?>) o).ELEMENTS);
    }

    @Override // JavaDoc inherited from super method
    public int hashCode() {
        int hash = 0, pow17 = 1;
        for (T element : ELEMENTS) {
            hash += element == null ? 0 : element.hashCode() * pow17;
            pow17 *= 17;
        }
        return hash;
    }
}
