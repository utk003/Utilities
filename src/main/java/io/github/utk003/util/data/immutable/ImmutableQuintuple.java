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

package io.github.utk003.util.data.immutable;

import java.util.Objects;

/**
 * An immutable quintuple of objects of types {@code A}, {@code B},
 * {@code C}, {@code D}, and {@code E}, respectively.
 * <p>
 * The {@code ImmutableQuintuple<A,B,C,D,E>} class is primarily designed for
 * use in parameterized applications such as in {@code List}s, {@code Set}s,
 * and {@code Map}s. This class provides an immutable quintuple of the given
 * objects.
 * <p>
 * This class can be paired with {@code Ref<K>} if mutable quintuples are
 * desired, or {@link io.github.utk003.util.data.mutable.MutableQuintuple}
 * can be used instead.
 *
 * @param <A> The type of the first object in the quintuple
 * @param <B> The type of the second object in the quintuple
 * @param <C> The type of the third object in the quintuple
 * @param <D> The type of the fourth object in the quintuple
 * @param <E> The type of the fifth object in the quintuple
 * @author Utkarsh Priyam
 * @version February 8, 2021
 * @see io.github.utk003.util.data.Ref
 * @see io.github.utk003.util.data.mutable.MutableQuintuple
 * @since 1.0.6
 */
public final class ImmutableQuintuple<A, B, C, D, E> {
    /**
     * Publicly accessible reference to the first object stored in this {@code ImmutableQuintuple}.
     */
    public final A FIRST;
    /**
     * Publicly accessible reference to the second object stored in this {@code ImmutableQuintuple}.
     */
    public final B SECOND;
    /**
     * Publicly accessible reference to the third object stored in this {@code ImmutableQuintuple}.
     */
    public final C THIRD;
    /**
     * Publicly accessible reference to the fourth object stored in this {@code ImmutableQuintuple}.
     */
    public final D FOURTH;
    /**
     * Publicly accessible reference to the fifth object stored in this {@code ImmutableQuintuple}.
     */
    public final E FIFTH;

    /**
     * Creates a new {@code ImmutableQuintuple} initialized to the values provided as arguments.
     *
     * @param first  The first object in the quintuple
     * @param second The second object in the quintuple
     * @param third  The third object in the quintuple
     * @param fourth The fourth object in the quintuple
     * @param fifth  The fifth object in the quintuple
     */
    public ImmutableQuintuple(A first, B second, C third, D fourth, E fifth) {
        FIRST = first;
        SECOND = second;
        THIRD = third;
        FOURTH = fourth;
        FIFTH = fifth;
    }

    @Override // JavaDoc inherited from super method
    public String toString() {
        return "{" + FIRST + "," + SECOND + "," + THIRD + "," + FOURTH + "," + FIFTH + "}";
    }

    @Override // JavaDoc inherited from super method
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o instanceof ImmutableQuintuple) {
            ImmutableQuintuple<?, ?, ?, ?, ?> q = (ImmutableQuintuple<?, ?, ?, ?, ?>) o;
            return Objects.equals(FIRST, q.FIRST) && Objects.equals(SECOND, q.SECOND) && Objects.equals(THIRD, q.THIRD) &&
                    Objects.equals(FOURTH, q.FOURTH) && Objects.equals(FIFTH, q.FIFTH);
        } else
            return false;
    }

    @Override // JavaDoc inherited from super method
    public int hashCode() {
        int a = FIRST == null ? 0 : FIRST.hashCode();
        int b = SECOND == null ? 0 : SECOND.hashCode();
        int c = THIRD == null ? 0 : THIRD.hashCode();
        int d = FOURTH == null ? 0 : FOURTH.hashCode();
        int e = FIFTH == null ? 0 : FIFTH.hashCode();
        return a + b * 17 + c * 17 * 17 + d * 17 * 17 * 17 + e * 17 * 17 * 17 * 17;
    }
}
