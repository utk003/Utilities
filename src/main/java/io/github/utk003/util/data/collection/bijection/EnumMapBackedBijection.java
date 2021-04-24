package io.github.utk003.util.data.collection.bijection;

import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;

/**
 * A specialized subclass of {@link MapBackedBijection} that uses {@link EnumMap}s
 * as its backing {@code Map} type and maps {@code enum}s to {@code enum}s.
 *
 * @param <A> The type of enums in the first set of this bijection
 * @param <B> The type of enums in the second set of this bijection
 * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
 * @version April 23, 2021
 * @see MapBackedBijection
 * @see Enum
 * @see EnumMap
 * @since 2.0.0
 */
public class EnumMapBackedBijection<A extends Enum<A>, B extends Enum<B>> extends MapBackedBijection<A, B, EnumMap<A, Bijection.Pairing<A, B>>, EnumMap<B, Bijection.Pairing<A, B>>> {
    /**
     * Creates a new {@code EnumMapBackedBijection} using {@link EnumMap#EnumMap(Class)}.
     *
     * @param enumTypeA The class type for the first enum set in this bijection
     * @param enumTypeB The class type for the second enum set in this bijection
     */
    public EnumMapBackedBijection(@NotNull Class<A> enumTypeA, @NotNull Class<B> enumTypeB) {
        super(
                new EnumMap<>(enumTypeA),
                new EnumMap<>(enumTypeB)
        );
    }
}