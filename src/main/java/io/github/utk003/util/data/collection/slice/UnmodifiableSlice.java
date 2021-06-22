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

package io.github.utk003.util.data.collection.slice;

import org.jetbrains.annotations.NotNull;

/**
 * A default implementation of an unmodifiable {@link Slice}.
 *
 * @param <E> The class type of the elements contained in this {@code UnmodifiableSlice}
 * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
 * @version June 11, 2021
 * @see Slice
 * @since 2.1.0
 */
final class UnmodifiableSlice<E> extends AbstractSlice<E> {
    /**
     * Constructs a new {@code UnmodifiableSlice} backed by the given array.
     *
     * @param arr The array backing the new {@code UnmodifiableSlice}
     * @see AbstractSlice#AbstractSlice(Object[])
     */
    public UnmodifiableSlice(@NotNull E[] arr) {
        super(arr);
    }
    /**
     * Constructs a new {@code UnmodifiableSlice} backed by the given array.
     *
     * @param arr   The array backing the new {@code UnmodifiableSlice}
     * @param lower The lower index bound of the new {@code UnmodifiableSlice}
     * @param upper The upper index bound of the new {@code UnmodifiableSlice}
     * @see AbstractSlice#AbstractSlice(Object[], int, int)
     */
    public UnmodifiableSlice(@NotNull E[] arr, int lower, int upper) {
        super(arr, lower, upper);
    }

    /**
     * {@inheritDoc}
     *
     * @throws UnsupportedOperationException Always
     */
    @Override
    public void set(int ind, E obj) {
        throw new UnsupportedOperationException("Unmodifiable slices cannot set elements");
    }
    /**
     * {@inheritDoc}
     *
     * @throws UnsupportedOperationException Always
     */
    @Override
    public @NotNull Slice<E> replace(int ind, E obj) {
        throw new UnsupportedOperationException("Unmodifiable slices cannot replace elements");
    }

    /**
     * {@inheritDoc}
     *
     * @throws UnsupportedOperationException Always
     */
    @Override
    public @NotNull Slice<E> apply(int ind, @NotNull Transformation<E> transformation) {
        throw new UnsupportedOperationException("Unmodifiable slices cannot apply transformations to elements");
    }
    /**
     * {@inheritDoc}
     *
     * @throws UnsupportedOperationException Always
     */
    @Override
    public @NotNull Slice<E> applyAll(@NotNull Transformation<E> transformation) {
        throw new UnsupportedOperationException("Unmodifiable slices cannot apply transformations to elements");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Slice<E> makeUnmodifiable() {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected @NotNull Slice<E> reslice(int absoluteStartIndex, int absoluteEndIndex) {
        return new UnmodifiableSlice<>(ARRAY, absoluteStartIndex, absoluteEndIndex);
    }
}
