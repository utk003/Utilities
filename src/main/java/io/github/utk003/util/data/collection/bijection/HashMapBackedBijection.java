package io.github.utk003.util.data.collection.bijection;

import java.util.HashMap;

/**
 * A specialized subclass of {@link MapBackedBijection} that uses {@link HashMap}s
 * as its backing {@code Map} type.
 *
 * @param <A> The type of objects in the first set of this bijection
 * @param <B> The type of objects in the second set of this bijection
 * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
 * @version April 23, 2021
 * @see MapBackedBijection
 * @since 2.0.0
 */
public class HashMapBackedBijection<A, B> extends MapBackedBijection<A, B, HashMap<A, Bijection.Pairing<A, B>>, HashMap<B, Bijection.Pairing<A, B>>> {
    /**
     * Creates a new default {@code HashMapBackedBijection} using {@link HashMap#HashMap()}.
     */
    public HashMapBackedBijection() {
        super(
                new HashMap<>(),
                new HashMap<>()
        );
    }
    /**
     * Creates a new {@code HashMapBackedBijection} using {@link HashMap#HashMap(int)}.
     *
     * @param expectedCapacity The expected number of mappings in the bijection once populated
     */
    public HashMapBackedBijection(int expectedCapacity) {
        super(
                new HashMap<>(expectedCapacity),
                new HashMap<>(expectedCapacity)
        );
    }
    /**
     * Creates a new {@code HashMapBackedBijection} using {@link HashMap#HashMap(int, float)}.
     *
     * @param expectedCapacity The expected number of mappings in the bijection once populated
     * @param loadFactor       The load factor of the underlying {@code HashMap}s
     */
    public HashMapBackedBijection(int expectedCapacity, float loadFactor) {
        super(
                new HashMap<>(expectedCapacity, loadFactor),
                new HashMap<>(expectedCapacity, loadFactor)
        );
    }
}
