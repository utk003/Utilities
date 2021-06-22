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

import io.github.utk003.util.misc.Verifier;
import org.jetbrains.annotations.NotNull;

/**
 * An array-like object inspired by {@code slice}s in <a href="https://golang.org/" target="_top">Go</a>.
 * <p>
 * Slices are simply views of an underlying array, and they can be extended, shortened, or
 * even sliced further to reduce the visible portion of the array. Like in Go, a slice has both
 * a size, which refers to the length of the subarray to which the slice can write, and a capacity,
 * which refers to the size of the slice and how much further the underlying array continues beyond
 * the end of the slice's subarray.
 * <p>
 * All slices created on an array and all slices formed by slicing, extending, or shortening
 * an existing slice will be bound to the same underlying array. In other words, changes to
 * the array, either directly or via a mutable slice, will be reflected in all such slices.
 * To check whether a slice is backed by a specific array (or any object), the {@link #sharesBacking(Object)}
 * method can be used.
 * <p>
 * To create a {@code Slice} object, use either {@link #wrap(Object[])} or {@link #wrap(Object[], int, int)}.
 * <p>
 * Unmodifiable slices, created either using the {@link #makeUnmodifiable()} instance method or
 * the {@link #makeUnmodifiable(Object[])} static method(s), can be used to create immutable arrays.
 * <p>
 * Although primarily designed for use with arrays, custom slice implementations can be created
 * for other data structures. In such a case, the {@link #sharesBacking(Object)} method should
 * be used to verify whether an specific object backs the given slice.
 *
 * @param <E> The class type of the elements of this {@code Slice}
 * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
 * @version June 11, 2021
 * @see #sharesBacking(Object)
 * @see #wrap(Object[])
 * @see #wrap(Object[], int, int)
 * @see #makeUnmodifiable()
 * @see #makeUnmodifiable(Object[])
 * @see #makeUnmodifiable(Object[], int, int)
 * @see #makeUnmodifiable(Slice)
 * @see <a href="https://golang.org/" target="_top">Go</a>
 * @since 2.1.0
 */
public interface Slice<E> extends Iterable<E> {
    /**
     * Returns the length of the subarray this slice can directly modify.
     * <p>
     * This method is simply an alias for {@link #length()}.
     *
     * @return The length of this slice
     * @see #length()
     */
    int size();
    /**
     * Returns the length of the subarray this slice can directly modify.
     *
     * @return The length of this slice
     * @see #size()
     */
    int length();

    /**
     * Returns the capacity of the array underlying this slice.
     * <p>
     * The capacity of this slice (or equivalently its underlying array)
     * represents the maximum length of a subarray that any slice extending
     * this slice can modify. This length is calculated as
     * <pre>
     * array.length - slice.start_index
     * </pre>
     * where {@code array.length} is the total length of this slice's underlying
     * array and {@code slice.start_index} is the starting index of this slice
     * relative to that array.
     *
     * @return This slice's capacity (for extension)
     * @see #extend(int)
     */
    int capacity();

    /**
     * Returns the element in this slice at the given index.
     * <p>
     * Indexing is calculated relative to the slice, not the underlying array.
     * For example, if a slice starts at index {@code 2} in an array, then accessing the
     * element at index {@code 0} (with respect to the slice) will return the element
     * at index {@code 2} in the array.
     *
     * @param ind The index of the element to retrieve, relative to the slice
     * @return The retrieved element, if it exists
     * @throws SliceIndexOutOfBoundsException If the index is less than {@code 0} or
     *                                        greater than equal to {@code length()}
     */
    E get(int ind);
    /**
     * Sets the element in this slice at the given index to the specified value.
     * <p>
     * Indexing is calculated relative to the slice, not the underlying array.
     * For example, if a slice starts at index {@code 2} in an array, then accessing the
     * element at index {@code 0} (with respect to the slice) will return the element
     * at index {@code 2} in the array.
     *
     * @param ind The index of the element to set, relative to the slice
     * @param obj The new value of the element
     * @throws SliceIndexOutOfBoundsException If the index is less than {@code 0} or
     *                                        greater than equal to {@code length()}
     */
    void set(int ind, E obj);

    /**
     * Replaces the element at the specified index with the new element value.
     * <p>
     * This method is identical to {@link #set(int, Object)}, with one exception.
     * This method returns this {@code Slice} object, so that other method calls
     * on the same object can be chained one after another.
     *
     * @param ind The index of the element to set, relative to the slice
     * @param obj The new value of the element
     * @return This {@code Slice} object
     * @throws SliceIndexOutOfBoundsException If the index is less than {@code 0} or
     *                                        greater than equal to {@code length()}
     * @see #set(int, Object)
     */
    @NotNull Slice<E> replace(int ind, E obj);
    /**
     * Applies the specified {@link Transformation Transformation} to the element at the specified index.
     * <p>
     * This method returns this {@code Slice} object, so that other method calls
     * on the same object can be chained one after another.
     *
     * @param ind            The index of the element to change, relative to the slice
     * @param transformation The {@code Transformation} to apply to the element
     * @return This {@code Slice} object
     * @throws SliceIndexOutOfBoundsException If the index is less than {@code 0} or
     *                                        greater than equal to {@code length()}
     * @see #replace(int, Object)
     * @see Transformation Transformation
     */
    @NotNull Slice<E> apply(int ind, @NotNull Transformation<E> transformation);
    /**
     * Applies the specified {@link Transformation Transformation} to all elements in the slice.
     * <p>
     * This method returns this {@code Slice} object, so that other method calls
     * on the same object can be chained one after another.
     *
     * @param transformation The {@code Transformation} to apply
     * @return This {@code Slice} object
     * @throws SliceIndexOutOfBoundsException If the index is less than {@code 0} or
     *                                        greater than equal to {@code length()}
     * @see #apply(int, Transformation)
     * @see Transformation Transformation
     */
    @NotNull Slice<E> applyAll(@NotNull Transformation<E> transformation);

    /**
     * Creates a new (shorter) {@code Slice} object backed by the same array as this slice.
     * <p>
     * This method is nearly identical in function to {@link String#substring(int)},
     * except that the resulting {@code Slice} will reflect changes made by this {@code Slice}
     * object or any other {@code Slice} object bound to the same array.
     * <p>
     * This method is identical to calling {@link #trim(int, int)} with the arguments
     * {@code (start, length())}.
     *
     * @param start The starting index of the trimming operation, relative to this {@code Slice} object
     * @return An new {@code Slice} object of the specified dimension
     * @throws SliceIndexOutOfBoundsException If the start index is less than {@code 0}
     *                                        or greater than equal to {@code length()}
     * @see #trim(int, int)
     * @see String#substring(int)
     */
    @NotNull Slice<E> trimStart(int start);
    /**
     * Creates a new (shorter) {@code Slice} object backed by the same array as this slice.
     * <p>
     * This method is similar to {@link #trimStart(int)}, except that the specified index is at
     * the end of this {@code Slice}, not the start. Regardless, the resulting {@code Slice}
     * will reflect changes made by this {@code Slice} object or any other {@code Slice}
     * object bound to the same array.
     * <p>
     * This method is identical to calling {@link #trim(int, int)} with the arguments
     * {@code (0, end)}.
     *
     * @param end The ending index of the trimming operation, relative to this {@code Slice} object
     * @return An new {@code Slice} object of the specified dimension
     * @throws SliceIndexOutOfBoundsException If the end index is less than {@code 0}
     *                                        or greater than equal to {@code length()}
     * @see #trimStart(int)
     * @see #trim(int, int)
     */
    @NotNull Slice<E> trimEnd(int end);
    /**
     * Creates a new (shorter) {@code Slice} object backed by the same array as this slice.
     * <p>
     * This method is nearly identical in function to {@link String#substring(int, int)},
     * except that the resulting {@code Slice} will reflect changes made by this {@code Slice}
     * object or any other {@code Slice} object bound to the same array.
     * <p>
     * Simpler versions of this method exist, where the either the start or end index is taken
     * to be the edge of this slice: {@link #trimStart(int)} and {@link #trimEnd(int)}.
     *
     * @param start The starting index of the trimming operation, relative to this {@code Slice} object
     * @param end   The ending index of the trimming operation, relative to this {@code Slice} object
     * @return An new {@code Slice} object of the specified dimension
     * @throws SliceIndexOutOfBoundsException If either index is less than {@code 0} or
     *                                        greater than equal to {@code length()}, or
     *                                        if the resulting slice length is negative
     * @see #trimStart(int)
     * @see #trimEnd(int)
     */
    @NotNull Slice<E> trim(int start, int end);

    /**
     * Creates a new shorter {@code Slice} object backed by the same array as this slice.
     * <p>
     * This method is identical to calling {@link #trim(int, int)} with the arguments
     * {@code (0, size() - by)}.
     * <p>
     * This same operation, applied to the front of this slice instead of the end, can be
     * achieved by calling {@link #trimStart(int)} with the argument {@code (by)}.
     * <p>
     * For a similar operation, extending the slice instead of shortening it, use {@link #extend(int)}.
     *
     * @param by The number of elements to remove off the end of this {@code Slice}
     * @return An new {@code Slice} object of the specified dimension
     * @throws SliceIndexOutOfBoundsException If the resulting slice length is negative
     * @see #trimStart(int)
     * @see #trim(int, int)
     * @see #extend(int)
     */
    @NotNull Slice<E> shorten(int by);
    /**
     * Creates a new longer {@code Slice} object backed by the same array as this slice.
     * <p>
     * This method extends the region of the underlying array that the slice can modify.
     * In other words,
     * <pre>
     *     new_slice.length() == old_slice.length() + by
     * </pre>
     * must be true. If this is not possible (due to constraints from {@link #capacity()}),
     * then this method must throw a {@link SliceIndexOutOfBoundsException}
     * <p>
     * The functionality provided by this method (extending a slice) cannot be achieved by
     * any of the other slice-creation methods, as they all throw {@code SliceIndexOutOfBoundsException}s
     * in such scenarios.
     * <p>
     * For a similar operation (shortening the slice instead of extending it) use {@link #shorten(int)}.
     *
     * @param by The number of additional elements to append to the end of this {@code Slice}
     * @return An new {@code Slice} object of the specified dimension
     * @throws SliceIndexOutOfBoundsException If the underlying array does not have sufficient
     *                                        space to accommodate the extension
     * @see #shorten(int)
     * @see #capacity()
     */
    @NotNull Slice<E> extend(int by);

    /**
     * Checks whether the given {@code Object} is the object backing this {@code Slice}.
     * <p>
     * This method should simply use {@code ==} to check whether the given object equals
     * the object used to back this {@code Slice}.
     * <p>
     * In most cases, this backing object will be an array, but the argument type
     * is {@code Object} to allow for custom implementations of {@code Slice} backed
     * by other data structures.
     *
     * @param arr The object (typically an array) to check as a potential backer
     * @return {@code true} if the given object backs this {@code Slice}; otherwise, {@code false}
     */
    boolean sharesBacking(@NotNull Object arr);

    /**
     * Creates a new {@code Slice} object backed by the given array.
     * <p>
     * This method is identical to calling
     * <pre>
     * wrap(arr, 0, arr.length).
     * </pre>
     *
     * @param arr The array to wrap in a {@code Slice} object
     * @param <E> The class type of the elements in the slice
     * @return The new {@code Slice} object
     * @see #wrap(Object[], int, int)
     */
    static <E> @NotNull Slice<E> wrap(@NotNull E[] arr) {
        return new DefaultSlice<>(arr);
    }
    /**
     * Creates a new {@code Slice} object backed by the given array.
     * <p>
     * This method limits the part of the array the returned slice
     * can modify correspond to the indices in the interval
     * {@code [from,to)}. For a slice-creation method that wraps
     * the entire array, use {@link #wrap(Object[])} instead.
     *
     * @param arr  The array to wrap in a {@code Slice} object
     * @param from The starting index of the slice, relative to the array
     * @param to   The ending index of the slice, relative to the array
     * @param <E>  The class type of the elements in the slice
     * @return The new {@code Slice} object
     * @see #wrap(Object[])
     */
    static <E> @NotNull Slice<E> wrap(@NotNull E[] arr, int from, int to) {
        return new DefaultSlice<>(arr, from, to);
    }
    static <E> @NotNull Slice<Slice<E>> wrap(@NotNull E[][] arr) {
        //noinspection unchecked
        return wrap(arr, (Slice<E>[]) new Slice[arr.length]);
    }
    static <E> @NotNull Slice<Slice<E>> wrap(@NotNull E[][] arr, @NotNull Slice<E>[] backing) {
        int len = Verifier.requireEqual(arr.length, backing.length);
        for (int i = 0; i < len; i++)
            backing[i] = new DefaultSlice<>(arr[i]);
        return new DefaultSlice<>(backing);
    }
    @SuppressWarnings("unchecked")
    static <E> @NotNull Slice<Slice<Slice<E>>> wrap(@NotNull E[][][] arr) {
        int len = arr.length;
        Slice<Slice<E>>[] backing = (Slice<Slice<E>>[]) new Slice[len];
        Slice<E>[][] backing2D = (Slice<E>[][]) new Slice[len][];
        for (int i = 0; i < len; i++)
            backing2D[i] = (Slice<E>[]) new Slice[arr[i].length];
        return wrap(arr, backing, backing2D);
    }
    static <E> @NotNull Slice<Slice<Slice<E>>> wrap(@NotNull E[][][] arr, @NotNull Slice<Slice<E>>[] backing, @NotNull Slice<E>[][] backing2D) {
        int len = Verifier.requireEqual(
                Verifier.requireEqual(arr.length, backing.length),
                backing2D.length
        );
        for (int i = 0; i < len; i++) {
            int len2 = Verifier.requireEqual(arr[i].length, backing2D[i].length);
            for (int j = 0; j < len2; j++)
                backing2D[i][j] = new DefaultSlice<>(arr[i][j]);
            backing[i] = new DefaultSlice<>(backing2D[i]);
        }
        return new DefaultSlice<>(backing);
    }

    /**
     * Returns an unmodifiable version of this {@code Slice} object.
     * <p>
     * This method is identical to calling {@link #makeUnmodifiable(Slice)}
     * with the argument {@code (this)}.
     * <p>
     * Note that method will not make this slice's elements unmodifiable.
     * This is especially an issue for multi-dimensional slices. Once the
     * outermost slice is unmodifiable, the inner slices can no longer become
     * unmodifiable (except via external modification to the underlying array),
     * as making slices (or other collections) unmodifiable creates a new object
     * instance that must then replace the original element in the slice.
     * Therefore, to properly make such nested slices unmodifiable, the slices
     * must be converted in reverse: starting with the innermost slices and
     * working outwards. One simple way to achieve this is using the
     * {@link #applyAll(Transformation)} method: for a 3D slice
     * (ie {@code Slice<Slice<Slice<E>>> slice}), either
     * <pre>
     * slice.applyAll((slice2D) -&gt; {
     *     return slice2D.applyAll((slice1D) -&gt; {
     *         return slice1D.makeUnmodifiable();
     *     }).makeUnmodifiable();
     * }).makeUnmodifiable();
     * </pre>
     * or (equivalently)
     * <pre>
     * slice.applyAll((slice2D) -&gt; slice2D.applyAll((slice1D) -&gt; slice1D.makeUnmodifiable()).makeUnmodifiable()).makeUnmodifiable();
     * </pre>
     * would return a perfectly unmodifiable 3D slice.
     *
     * @return An unmodifiable version of this slice
     * @see #makeUnmodifiable(Slice)
     * @see #applyAll(Transformation)
     */
    @NotNull Slice<E> makeUnmodifiable();
    /**
     * Returns an unmodifiable {@code Slice} object backed by the given array.
     * <p>
     * This method is an optimized way of performing {@link #wrap(Object[])}
     * followed by either {@link #makeUnmodifiable()} or {@link #makeUnmodifiable(Slice)}.
     * <p>
     * This method is identical to calling
     * <pre>
     * makeUnmodifiable(arr, 0, arr.length).
     * </pre>
     *
     * @param arr The array to wrap in an unmodifiable {@code Slice} object
     * @param <E> The class type of the elements in the slice
     * @return The new unmodifiable {@code Slice} object
     * @see #wrap(Object[])
     * @see #makeUnmodifiable(Slice)
     * @see #makeUnmodifiable(Object[], int, int)
     */
    static <E> @NotNull Slice<E> makeUnmodifiable(@NotNull E[] arr) {
        return new UnmodifiableSlice<>(arr);
    }
    /**
     * Returns an unmodifiable {@code Slice} object backed by the given array.
     * <p>
     * This method is an optimized way of performing {@link #wrap(Object[], int, int)}
     * followed by either {@link #makeUnmodifiable()} or {@link #makeUnmodifiable(Slice)}.
     * <p>
     * Like with the {@link #wrap(Object[], int, int)} method, this method quite like
     * {@link #makeUnmodifiable(Object[])}, except that it provides an option to limit
     * the region of the array corresponding to the returned {@code Slice} object. The
     * part of the array the returned slice can modify correspond to the indices in
     * the interval {@code [from,to)}.
     *
     * @param arr  The array to wrap in an unmodifiable {@code Slice} object
     * @param from The starting index of the slice, relative to the array
     * @param to   The ending index of the slice, relative to the array
     * @param <E>  The class type of the elements in the slice
     * @return The new unmodifiable {@code Slice} object
     * @see #wrap(Object[])
     * @see #makeUnmodifiable(Slice)
     * @see #makeUnmodifiable(Object[], int, int)
     */
    static <E> @NotNull Slice<E> makeUnmodifiable(@NotNull E[] arr, int from, int to) {
        return new UnmodifiableSlice<>(arr, from, to);
    }
    static <E> @NotNull Slice<Slice<E>> makeUnmodifiable(@NotNull E[][] arr) {
        //noinspection unchecked
        return makeUnmodifiable(arr, (Slice<E>[]) new Slice[arr.length]);
    }
    static <E> @NotNull Slice<Slice<E>> makeUnmodifiable(@NotNull E[][] arr, @NotNull Slice<E>[] backing) {
        int len = Verifier.requireEqual(arr.length, backing.length);
        for (int i = 0; i < len; i++)
            backing[i] = new UnmodifiableSlice<>(arr[i]);
        return new UnmodifiableSlice<>(backing);
    }
    @SuppressWarnings("unchecked")
    static <E> @NotNull Slice<Slice<Slice<E>>> makeUnmodifiable(@NotNull E[][][] arr) {
        int len = arr.length;
        Slice<Slice<E>>[] backing = (Slice<Slice<E>>[]) new Slice[len];
        Slice<E>[][] backing2D = (Slice<E>[][]) new Slice[len][];
        for (int i = 0; i < len; i++)
            backing2D[i] = (Slice<E>[]) new Slice[arr[i].length];
        return makeUnmodifiable(arr, backing, backing2D);
    }
    static <E> @NotNull Slice<Slice<Slice<E>>> makeUnmodifiable(@NotNull E[][][] arr, @NotNull Slice<Slice<E>>[] backing, @NotNull Slice<E>[][] backing2D) {
        int len = Verifier.requireEqual(
                Verifier.requireEqual(arr.length, backing.length),
                backing2D.length
        );
        for (int i = 0; i < len; i++) {
            int len2 = Verifier.requireEqual(arr[i].length, backing2D[i].length);
            for (int j = 0; j < len2; j++)
                backing2D[i][j] = new UnmodifiableSlice<>(arr[i][j]);
            backing[i] = new UnmodifiableSlice<>(backing2D[i]);
        }
        return new UnmodifiableSlice<>(backing);
    }
    /**
     * Returns an unmodifiable version of the given {@code Slice}.
     * <p>
     * This is simply a static version of {@link #makeUnmodifiable()}.
     * <p>
     * Note that method will not make this slice's elements unmodifiable.
     * This is especially an issue for multi-dimensional slices. Once the
     * outermost slice is unmodifiable, the inner slices can no longer become
     * unmodifiable (except via external modification to the underlying array),
     * as making slices (or other collections) unmodifiable creates a new object
     * instance that must then replace the original element in the slice.
     * Therefore, to properly make such nested slices unmodifiable, the slices
     * must be converted in reverse: starting with the innermost slices and
     * working outwards. One simple way to achieve this is using the
     * {@link #applyAll(Transformation)} method: for a 3D slice
     * (ie {@code Slice<Slice<Slice<E>>> slice}), either
     * <pre>
     * slice.applyAll((slice2D) -&gt; {
     *     return slice2D.applyAll((slice1D) -&gt; {
     *         return slice1D.makeUnmodifiable();
     *     }).makeUnmodifiable();
     * }).makeUnmodifiable();
     * </pre>
     * or (equivalently)
     * <pre>
     * slice.applyAll((slice2D) -&gt; slice2D.applyAll((slice1D) -&gt; slice1D.makeUnmodifiable()).makeUnmodifiable()).makeUnmodifiable();
     * </pre>
     * would return a perfectly unmodifiable 3D slice.
     *
     * @param slice The slice to make unmodifiable
     * @param <E>   The class type of the elements in the slice
     * @return The new unmodifiable {@code Slice} object
     * @see #makeUnmodifiable()
     * @see #applyAll(Transformation)
     */
    static <E> @NotNull Slice<E> makeUnmodifiable(@NotNull Slice<E> slice) {
        return slice.makeUnmodifiable();
    }

    /**
     * A {@link FunctionalInterface} that represents any
     * element-by-element transformation of a {@link Slice}.
     * <p>
     * This interface is used by the {@link #apply(int, Transformation)}
     * and {@link #applyAll(Transformation)} methods.
     * <p>
     * This interface is quite similar in function to {@link java.util.function.UnaryOperator}.
     *
     * @param <E> The type of the object to be transformed
     * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
     * @version June 11, 2021
     * @see #apply(int, Transformation)
     * @see #applyAll(Transformation)
     * @see Slice
     * @see java.util.function.UnaryOperator
     * @see FunctionalInterface
     * @since 2.1.0
     */
    @FunctionalInterface
    interface Transformation<E> {
        /**
         * A unary operation that acts on objects of type {@code E}.
         *
         * @param in The original element
         * @return The transformed element
         * @see java.util.function.UnaryOperator#apply(Object)
         */
        E apply(E in);
    }
}
