/*
MIT License

Copyright (c) 2020-2021 Utkarsh Priyam

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

package io.github.utk003.util.data;

import java.util.Objects;

/**
 * A generic wrapper class for a pair of objects of types {@code A} and {@code B},
 * respectively.
 * <p>
 * The {@code Pair<A,B>} class is primarily designed for use in parameterized applications,
 * such as in {@code List}s, {@code Set}s, and {@code Map}s. This class provides an immutable
 * pairing of the given objects. It can be paired with {@code Ref<K>} if mutable
 * pairs are desired instead.
 *
 * @param <A> The type of the first object in the pair
 * @param <B> The type of the second object in the pair
 * @author Utkarsh Priyam
 * @version January 13, 2021
 * @see Ref
 */
// The Ref link (above) is bugged for certain JavaDoc checker tools (ie IntelliJ's built-in tool)
public final class Pair<A, B> {
    /**
     * Publicly accessible reference to the first object stored in this {@code Pair}.
     */
    public final A first;
    /**
     * Publicly accessible reference to the second object stored in this {@code Pair}.
     */
    public final B second;

    /**
     * Creates a new {@code Pair} initialized to the values provided as arguments.
     *
     * @param first  The first object in the pair
     * @param second The second object in the pair
     */
    public Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }

    @Override // JavaDoc inherited from super method
    public String toString() {
        return "{" + first.toString() + ", " + second.toString() + "}";
    }

    @Override // JavaDoc inherited from super method
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o instanceof Pair) {
            Pair<?, ?> p = (Pair<?, ?>) o;
            return Objects.equals(first, p.first) && Objects.equals(second, p.second);
        } else
            return false;
    }

    @Override // JavaDoc inherited from super method
    public int hashCode() {
        int a = first == null ? 0 : first.hashCode();
        int b = second == null ? 0 : second.hashCode();
        return a * 17 + b;
    }
}