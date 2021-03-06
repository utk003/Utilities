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

package io.github.utk003.util.data.tuple.mutable;

import java.util.Objects;

/**
 * A mutable pair of objects of types {@code A} and {@code B}, respectively.
 * <p>
 * The {@code MutablePair<A,B>} class is primarily designed for use in
 * parameterized applications such as in {@code List}s, {@code Set}s, and
 * {@code Map}s. This class provides a mutable pairing of any two objects.
 * <p>
 * Use {@link io.github.utk003.util.data.tuple.immutable.ImmutablePair} for immutable pairs.
 *
 * @param <A> The type of the first object in the pair
 * @param <B> The type of the second object in the pair
 * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
 * @version February 8, 2021
 * @see io.github.utk003.util.data.tuple.immutable.ImmutablePair
 * @since 1.0.6
 */
public final class MutablePair<A, B> {
    /**
     * Publicly accessible reference to the first object stored in this {@code MutablePair}.
     */
    public A first;
    /**
     * Publicly accessible reference to the second object stored in this {@code MutablePair}.
     */
    public B second;

    /**
     * Creates a new {@code MutablePair} initialized to {@code (null,null)}.
     */
    public MutablePair() {
        this(null, null);
    }
    /**
     * Creates a new {@code MutablePair} initialized to the values provided as arguments.
     *
     * @param first  The first object in the pair
     * @param second The second object in the pair
     */
    public MutablePair(A first, B second) {
        this.first = first;
        this.second = second;
    }

    /**
     * {@inheritDoc}
     */
    @Override // JavaDoc inherited from super method
    public String toString() {
        return "(" + first + "," + second + ")";
    }

    /**
     * {@inheritDoc}
     */
    @Override // JavaDoc inherited from super method
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o instanceof MutablePair) {
            MutablePair<?, ?> p = (MutablePair<?, ?>) o;
            return Objects.equals(first, p.first) && Objects.equals(second, p.second);
        } else
            return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override // JavaDoc inherited from super method
    public int hashCode() {
        int a = first == null ? 0 : first.hashCode();
        int b = second == null ? 0 : second.hashCode();
        return a + b * 17;
    }
}
