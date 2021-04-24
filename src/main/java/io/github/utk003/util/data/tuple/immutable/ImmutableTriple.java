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

package io.github.utk003.util.data.tuple.immutable;

import java.util.Objects;

/**
 * An immutable triple of objects of types {@code A}, {@code B}, and {@code C}, respectively.
 * <p>
 * The {@code ImmutableTriple<A,B,C>} class is primarily designed for use in
 * parameterized applications such as in {@code List}s, {@code Set}s, and
 * {@code Map}s. This class provides an immutable triplet of the given
 * objects.
 * <p>
 * This class can be paired with {@code Ref<K>} if mutable triples are
 * desired, or {@link io.github.utk003.util.data.tuple.mutable.MutableTriple}
 * can be used instead.
 *
 * @param <A> The type of the first object in the triple
 * @param <B> The type of the second object in the triple
 * @param <C> The type of the third object in the triple
 * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
 * @version February 8, 2021
 * @see io.github.utk003.util.data.reference.Ref
 * @see io.github.utk003.util.data.tuple.mutable.MutableTriple
 * @since 1.0.6
 */
public final class ImmutableTriple<A, B, C> {
    /**
     * Publicly accessible reference to the first object stored in this {@code ImmutableTriple}.
     */
    public final A FIRST;
    /**
     * Publicly accessible reference to the second object stored in this {@code ImmutableTriple}.
     */
    public final B SECOND;
    /**
     * Publicly accessible reference to the third object stored in this {@code ImmutableTriple}.
     */
    public final C THIRD;

    /**
     * Creates a new {@code ImmutableTriple} initialized to the values provided as arguments.
     *
     * @param first  The first object in the triple
     * @param second The second object in the triple
     * @param third  The third object in the triple
     */
    public ImmutableTriple(A first, B second, C third) {
        FIRST = first;
        SECOND = second;
        THIRD = third;
    }

    /**
     * {@inheritDoc}
     */
    @Override // JavaDoc inherited from super method
    public String toString() {
        return "{" + FIRST + "," + SECOND + "," + THIRD + "}";
    }

    /**
     * {@inheritDoc}
     */
    @Override // JavaDoc inherited from super method
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o instanceof ImmutableTriple) {
            ImmutableTriple<?, ?, ?> t = (ImmutableTriple<?, ?, ?>) o;
            return Objects.equals(FIRST, t.FIRST) && Objects.equals(SECOND, t.SECOND) && Objects.equals(THIRD, t.THIRD);
        } else
            return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override // JavaDoc inherited from super method
    public int hashCode() {
        int a = FIRST == null ? 0 : FIRST.hashCode();
        int b = SECOND == null ? 0 : SECOND.hashCode();
        int c = THIRD == null ? 0 : THIRD.hashCode();
        return a + b * 17 + c * 17 * 17;
    }
}
