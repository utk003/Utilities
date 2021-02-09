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
 * An immutable pair of objects of types {@code A} and {@code B}, respectively.
 * <p>
 * The {@code ImmutablePair<A,B>} class is primarily designed for use in
 * parameterized applications such as in {@code List}s, {@code Set}s, and
 * {@code Map}s. This class provides an immutable pairing of the given
 * objects.
 * <p>
 * This class can be paired with {@code Ref<K>} if mutable pairs are
 * desired, or {@link io.github.utk003.util.data.mutable.MutablePair}
 * can be used instead.
 *
 * @param <A> The type of the first object in the pair
 * @param <B> The type of the second object in the pair
 * @author Utkarsh Priyam
 * @version February 8, 2021
 * @see io.github.utk003.util.data.Ref
 * @see io.github.utk003.util.data.mutable.MutablePair
 * @since 1.0.6
 */
public final class ImmutablePair<A, B> {
    /**
     * Publicly accessible reference to the first object stored in this {@code ImmutablePair}.
     */
    public final A FIRST;
    /**
     * Publicly accessible reference to the second object stored in this {@code ImmutablePair}.
     */
    public final B SECOND;

    /**
     * Creates a new {@code ImmutablePair} initialized to the values provided as arguments.
     *
     * @param first  The first object in the pair
     * @param second The second object in the pair
     */
    public ImmutablePair(A first, B second) {
        FIRST = first;
        SECOND = second;
    }

    @Override // JavaDoc inherited from super method
    public String toString() {
        return "{" + FIRST + "," + SECOND + "}";
    }

    @Override // JavaDoc inherited from super method
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o instanceof ImmutablePair) {
            ImmutablePair<?, ?> p = (ImmutablePair<?, ?>) o;
            return Objects.equals(FIRST, p.FIRST) && Objects.equals(SECOND, p.SECOND);
        } else
            return false;
    }

    @Override // JavaDoc inherited from super method
    public int hashCode() {
        int a = FIRST == null ? 0 : FIRST.hashCode();
        int b = SECOND == null ? 0 : SECOND.hashCode();
        return a + b * 17;
    }
}
