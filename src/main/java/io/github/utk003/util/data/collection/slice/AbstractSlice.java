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

import java.util.Iterator;

/**
 * A template implementation of an array-backed {@link Slice}.
 *
 * @param <E> The class type of the elements contained in this {@code AbstractSlice}
 * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
 * @version June 11, 2021
 * @see Slice
 * @since 2.1.0
 */
public abstract class AbstractSlice<E> implements Slice<E> {
    /**
     * The array backing this {@code AbstractSlice}.
     */
    protected final E[] ARRAY;
    /**
     * The lower index bound of this {@code AbstractSlice}, relative to its backing array.
     *
     * @see #ARRAY
     */
    protected final int LOWER;
    /**
     * The upper index bound of this {@code AbstractSlice}, relative to its backing array.
     *
     * @see #ARRAY
     */
    protected final int UPPER;
    /**
     * The length of this {@code AbstractSlice}.
     * <p>
     * This value is simply equal to {@link #UPPER} minus ({@code -}) {@link #LOWER}.
     *
     * @see #LOWER
     * @see #UPPER
     */
    protected final int LENGTH;

    /**
     * Constructs a new {@code AbstractSlice} backed by the specified array.
     * <p>
     * This method is identical to calling
     * <pre>
     * AbstractSlice(arr, 0, arr.length)
     * </pre>
     * instead.
     *
     * @param arr The array backing the new {@code AbstractSlice}
     * @see #AbstractSlice(Object[], int, int)
     */
    public AbstractSlice(@NotNull E[] arr) {
        this(arr, 0, arr.length);
    }
    /**
     * Constructs a new {@code AbstractSlice} backed by the specified array.
     * <p>
     * This method limits the region of the backing array accessible by the
     * slice to indices in the interval {@code [lower,upper)}. For an unlimited
     * slice, use {@link #AbstractSlice(Object[])} instead.
     * <p>
     * This method throws a {@link SliceIndexOutOfBoundsException} if either
     * index ({@code lower} or {@code upper}) is less than {@code 0} or
     * greater than or equal to {@code arr.length}, or if the combination of
     * the two indices results in a negative length for the slice (ie if
     * {@code lower > upper}).
     *
     * @param arr   The array backing the new {@code AbstractSlice}
     * @param lower The lower end of the index interval accessible by this slice
     * @param upper The upper end of the index interval accessible by this slice
     * @throws SliceIndexOutOfBoundsException If the given {@code lower} and {@code upper} indices are invalid
     * @see #AbstractSlice(Object[])
     */
    public AbstractSlice(@NotNull E[] arr, int lower, int upper) {
        if (lower < 0)
            throw new SliceIndexOutOfBoundsException(lower);
        if (upper < lower)
            throw new SliceIndexOutOfBoundsException(upper - lower);
        if (arr.length < upper)
            throw new SliceIndexOutOfBoundsException(upper);

        ARRAY = arr;

        LOWER = lower;
        UPPER = upper;
        LENGTH = UPPER - LOWER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return LENGTH;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public int length() {
        return LENGTH;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public int capacity() {
        return ARRAY.length - LOWER;
    }

    /**
     * Adjusts the given index to be relative to the backing array
     * rather than relative to this {@code AbstractSlice}.
     * <p>
     * This method verifies that the given index is valid (ie between
     * {@code 0} and {@code length()}), and then it shifts the index
     * to be relative to the backing array {@link #ARRAY} rather than
     * the slice. The shift amount is simply {@link #LOWER}.
     *
     * @param ind The index to verify and shift
     * @return The adjusted index (ie relative to this {@code AbstractSlice}'s backing array)
     * @throws SliceIndexOutOfBoundsException If the given index is not valid with respect to the {@code AbstractSlice}
     * @see #ARRAY
     */
    protected int adjustIndex(int ind) {
        if (ind < 0 || ind >= LENGTH) throw new SliceIndexOutOfBoundsException(ind);
        return ind + LOWER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E get(int ind) {
        return ARRAY[adjustIndex(ind)];
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void set(int ind, E obj) {
        ARRAY[adjustIndex(ind)] = obj;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Slice<E> replace(int ind, E obj) {
        ARRAY[adjustIndex(ind)] = obj;
        return this;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Slice<E> apply(int ind, @NotNull Transformation<E> transformation) {
        ind = adjustIndex(ind);
        ARRAY[ind] = transformation.apply(ARRAY[ind]);
        return this;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Slice<E> applyAll(@NotNull Transformation<E> transformation) {
        for (int i = LOWER; i < UPPER; i++)
            ARRAY[i] = transformation.apply(ARRAY[i]);
        return this;
    }

    /**
     * Returns a new {@link Slice} bound to the same array as this {@code AbstractSlice}
     * over the index interval {@code [absoluteStartIndex,absoluteEndIndex)}.
     * <p>
     * Though not required, it is suggested that this method return {@code Slice}s
     * of the exact same type as this {@code AbstractSlice}. An easy way to achieve this
     * is to simply return the slice created by the constructor {@link #AbstractSlice(Object[], int, int)}
     * (or its corresponding constructor in the appropriate class) with the arguments
     * {@code (}{@link #ARRAY}{@code , absoluteStartIndex, absoluteEndIndex)}.
     *
     * @param absoluteStartIndex The start index of the new {@code Slice}, relative to the backing array
     * @param absoluteEndIndex   The end index of the new {@code Slice}, relative to the backing array
     * @return The new {@code Slice} instance
     * @see #AbstractSlice(Object[], int, int)
     * @see #ARRAY
     * @see Slice
     */
    protected abstract @NotNull Slice<E> reslice(int absoluteStartIndex, int absoluteEndIndex);

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Slice<E> trimStart(int start) {
        return reslice(adjustIndex(start), UPPER);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Slice<E> trimEnd(int end) {
        return reslice(LOWER, adjustIndex(end));
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Slice<E> trim(int start, int end) {
        return reslice(adjustIndex(start), adjustIndex(end));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Slice<E> shorten(int by) {
        return reslice(LOWER, UPPER - by);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Slice<E> extend(int by) {
        return reslice(LOWER, UPPER + by);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Slice<E> makeUnmodifiable() {
        return new UnmodifiableSlice<>(ARRAY, LOWER, UPPER);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean sharesBacking(@NotNull Object arr) {
        return arr == ARRAY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        Iterator<E> it = iterator();
        if (!it.hasNext())
            return "[]";

        StringBuilder builder = new StringBuilder("[").append(it.next());
        while (it.hasNext())
            builder.append(", ").append(it.next());
        return builder + "]";
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = LOWER ^ UPPER;
        for (E obj : this)
            hash ^= obj.hashCode();
        return hash;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Slice<?>))
            return false;

        Slice<?> slice = (Slice<?>) o;
        return slice.sharesBacking(ARRAY) && capacity() == slice.capacity() && size() == slice.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Iterator<E> iterator() {
        return new Itr();
    }

    /**
     * A simple iterator over the elements of an {@link AbstractSlice}.
     *
     * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
     * @version June 11, 2021
     * @see AbstractSlice
     * @since 2.1.0
     */
    private class Itr implements Iterator<E> {
        private int ind = LOWER;

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean hasNext() {
            return ind < UPPER;
        }
        /**
         * {@inheritDoc}
         */
        @Override
        public E next() {
            if (ind < UPPER) return ARRAY[ind++];
            throw new SliceIndexOutOfBoundsException(ind - LOWER);
        }
    }
}
