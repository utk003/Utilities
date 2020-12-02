package me.utk.util.data;

import java.util.Objects;

/**
 * A generic wrapper class for a pair of objects of types {@code A} and {@code B},
 * respectively.
 * <p>
 * The {@code Pair<A,B>} class is primarily designed for use in parameterized applications,
 * such as in {@code List}s, {@code Set}s, and {@code Map}s. This class provides an immutable
 * pairing of the given objects. It can be paired with {@code ClassWrapper<K>} if mutable
 * pairs are desired instead.
 *
 * @param <A> The type of the first object in the pair
 * @param <B> The type of the second object in the pair
 * @author Utkarsh Priyam
 * @version 12/1/20
 * @see ClassWrapper
 */
public final class Pair<A, B> {
    /**
     * Publicly accessible references to the objects stored in this {@code Pair}.
     */
    public final A first;
    public final B second;

    /**
     * Creates a new {@code Pair} initialized to the values provided as arguments.
     * @param first The first object in the pair
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