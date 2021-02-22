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

import java.util.Objects;

/**
 * A mutable quintuple of objects of types {@code A}, {@code B},
 * {@code C}, {@code D}, and {@code E}, respectively.
 * <p>
 * The {@code MutableQuintuple<A,B,C,D,E>} class is primarily designed for
 * use in parameterized applications such as in {@code List}s, {@code Set}s,
 * and {@code Map}s. This class provides a mutable quintuple of the given
 * objects.
 * <p>
 * Use {@link io.github.utk003.util.data.immutable.ImmutableQuintuple}
 * for immutable quintuples.
 *
 * @param <A> The type of the first object in the quintuple
 * @param <B> The type of the second object in the quintuple
 * @param <C> The type of the third object in the quintuple
 * @param <D> The type of the fourth object in the quintuple
 * @param <E> The type of the fifth object in the quintuple
 * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
 * @version February 8, 2021
 * @see io.github.utk003.util.data.immutable.ImmutableQuintuple
 * @since 1.0.6
 */
public final class MutableQuintuple<A, B, C, D, E> {
    /**
     * Publicly accessible reference to the first object stored in this {@code MutableQuintuple}.
     */
    public A first;
    /**
     * Publicly accessible reference to the second object stored in this {@code MutableQuintuple}.
     */
    public B second;
    /**
     * Publicly accessible reference to the third object stored in this {@code MutableQuintuple}.
     */
    public C third;
    /**
     * Publicly accessible reference to the fourth object stored in this {@code MutableQuintuple}.
     */
    public D fourth;
    /**
     * Publicly accessible reference to the fifth object stored in this {@code MutableQuintuple}.
     */
    public E fifth;

    /**
     * Creates a new {@code MutableQuintuple} initialized to {@code (null,null,null,null,null)}.
     */
    public MutableQuintuple() {
        this(null, null, null, null, null);
    }
    /**
     * Creates a new {@code MutableQuintuple} initialized to the values provided as arguments.
     *
     * @param first  The first object in the quintuple
     * @param second The second object in the quintuple
     * @param third  The third object in the quintuple
     * @param fourth The fourth object in the quintuple
     * @param fifth  The fifth object in the quintuple
     */
    public MutableQuintuple(A first, B second, C third, D fourth, E fifth) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
        this.fifth = fifth;
    }

    /**
     * {@inheritDoc}
     */
    @Override // JavaDoc inherited from super method
    public String toString() {
        return "(" + first + "," + second + "," + third + "," + fourth + "," + fifth + ")";
    }

    /**
     * {@inheritDoc}
     */
    @Override // JavaDoc inherited from super method
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o instanceof MutableQuintuple) {
            MutableQuintuple<?, ?, ?, ?, ?> q = (MutableQuintuple<?, ?, ?, ?, ?>) o;
            return Objects.equals(first, q.first) && Objects.equals(second, q.second) && Objects.equals(third, q.third) &&
                    Objects.equals(fourth, q.fourth) && Objects.equals(fifth, q.fifth);
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
        int d = fourth == null ? 0 : fourth.hashCode();
        int e = fifth == null ? 0 : fifth.hashCode();
        return a + b * 17 + c * 17 * 17 + d * 17 * 17 * 17 + e * 17 * 17 * 17 * 17;
    }
}

