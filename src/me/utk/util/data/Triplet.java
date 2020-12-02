package me.utk.util.data;

import java.util.Objects;

/**
 * A generic wrapper class for a triplet of objects of types
 * {@code A}, {@code B}, and {@code C}, respectively.
 * <p>
 * The {@code Triplet<A,B,C>} class is primarily designed for use in parameterized applications,
 * such as in {@code List}s, {@code Set}s, and {@code Map}s. This class provides an immutable
 * tripling of the given objects. It can be paired with {@code ClassWrapper<K>} if mutable
 * triplets are desired instead.
 *
 * @param <A> The type of the first object in the triplet
 * @param <B> The type of the second object in the triplet
 * @param <C> The type of the third object in the triplet
 * @author Utkarsh Priyam
 * @version 12/1/20
 * @see ClassWrapper
 */
public final class Triplet<A, B, C> {
    /**
     * Publicly accessible references to the objects stored in this {@code Triplet}.
     */
    public final A first;
    public final B second;
    public final C third;

    /**
     * Creates a new {@code Triplet} initialized to the values provided as arguments.
     * @param first The first object in the triplet
     * @param second The second object in the triplet
     * @param third The third object in the triplet
     */
    public Triplet(A first, B second, C third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    @Override // JavaDoc inherited from super method
    public String toString() {
        return "{" + first.toString() + ", " + second.toString() + ", " + third.toString() + "}";
    }

    @Override // JavaDoc inherited from super method
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o instanceof Triplet) {
            Triplet<?, ?, ?> t = (Triplet<?, ?, ?>) o;
            return Objects.equals(first, t.first) && Objects.equals(second, t.second) && Objects.equals(third, t.third);
        } else
            return false;
    }

    @Override // JavaDoc inherited from super method
    public int hashCode() {
        int a = first == null ? 0 : first.hashCode();
        int b = second == null ? 0 : second.hashCode();
        int c = third == null ? 0 : third.hashCode();
        return a * 17 * 17 + b * 17 + c;
    }
}
