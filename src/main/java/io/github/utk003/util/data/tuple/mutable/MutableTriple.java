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
 * A mutable triple of objects of types {@code A}, {@code B}, and {@code C}, respectively.
 * <p>
 * The {@code MutableTriple<A,B,C>} class is primarily designed for use in
 * parameterized applications such as in {@code List}s, {@code Set}s, and
 * {@code Map}s. This class provides a mutable triple of the given objects.
 * <p>
 * Use {@link io.github.utk003.util.data.tuple.immutable.ImmutableTriple} for immutable triples.
 *
 * @param <A> The type of the first object in the triple
 * @param <B> The type of the second object in the triple
 * @param <C> The type of the third object in the triple
 * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
 * @version February 8, 2021
 * @see io.github.utk003.util.data.tuple.immutable.ImmutableTriple
 * @since 1.0.6
 */
public final class MutableTriple<A, B, C> {
    /**
     * Publicly accessible reference to the first object stored in this {@code MutableTriple}.
     */
    public A first;
    /**
     * Publicly accessible reference to the second object stored in this {@code MutableTriple}.
     */
    public B second;
    /**
     * Publicly accessible reference to the third object stored in this {@code MutableTriple}.
     */
    public C third;

    /**
     * Creates a new {@code MutableTriple} initialized to {@code (null,null,null)}.
     */
    public MutableTriple() {
        this(null, null, null);
    }
    /**
     * Creates a new {@code MutableTriple} initialized to the values provided as arguments.
     *
     * @param first  The first object in the triple
     * @param second The second object in the triple
     * @param third  The third object in the triple
     */
    public MutableTriple(A first, B second, C third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    /**
     * {@inheritDoc}
     */
    @Override // JavaDoc inherited from super method
    public String toString() {
        return "(" + first + "," + second + "," + third + ")";
    }

    /**
     * {@inheritDoc}
     */
    @Override // JavaDoc inherited from super method
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o instanceof MutableTriple) {
            MutableTriple<?, ?, ?> t = (MutableTriple<?, ?, ?>) o;
            return Objects.equals(first, t.first) && Objects.equals(second, t.second) && Objects.equals(third, t.third);
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
        int c = third == null ? 0 : third.hashCode();
        return a + b * 17 + c * 17 * 17;
    }
}