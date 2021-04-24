package io.github.utk003.util.data.collection.bijection;

import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.HashMap;

/**
 * A specialized subclass of {@link MapBackedBijection} that uses {@link HashMap}s
 * as its backing {@code Map} type and maps {@code enum}s to {@code Object}s.
 * <p>
 * There is no {@code HashEnumMapBackedBijection} class, because it would be identical
 * to this class but with its parameter's flipped. As a result, simply flip the parameters
 * as necessary to make the mapping compatible with this class's parameter list.
 *
 * @param <A> The type of enums in the first set of this bijection
 * @param <B> The type of objects in the second set of this bijection
 * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
 * @version April 23, 2021
 * @see MapBackedBijection
 * @since 2.0.0
 */
public class EnumHashMapBackedBijection<A extends Enum<A>, B> extends MapBackedBijection<A, B, EnumMap<A, Bijection.Pairing<A, B>>, HashMap<B, Bijection.Pairing<A, B>>> {
    /**
     * Creates a new default {@code EnumHashMapBackedBijection} using {@link EnumMap#EnumMap(Class)} and {@link HashMap#HashMap()}.
     *
     * @param enumType The class type for the enum set in this bijection
     */
    public EnumHashMapBackedBijection(@NotNull Class<A> enumType) {
        super(
                new EnumMap<>(enumType),
                new HashMap<>()
        );
    }
    /**
     * Creates a new {@code EnumHashMapBackedBijection} using {@link EnumMap#EnumMap(Class)} and {@link HashMap#HashMap(int)}.
     *
     * @param enumType         The class type for the enum set in this bijection
     * @param expectedCapacity The expected number of mappings in the bijection once populated
     */
    public EnumHashMapBackedBijection(@NotNull Class<A> enumType, int expectedCapacity) {
        super(
                new EnumMap<>(enumType),
                new HashMap<>(expectedCapacity)
        );
    }
    /**
     * Creates a new {@code EnumHashMapBackedBijection} using {@link EnumMap#EnumMap(Class)} and {@link HashMap#HashMap(int, float)}.
     *
     * @param enumType         The class type for the enum set in this bijection
     * @param expectedCapacity The expected number of mappings in the bijection once populated
     * @param loadFactor       The load factor of the underlying {@code HashMap}s
     */
    public EnumHashMapBackedBijection(@NotNull Class<A> enumType, int expectedCapacity, float loadFactor) {
        super(
                new EnumMap<>(enumType),
                new HashMap<>(expectedCapacity, loadFactor)
        );
    }
}