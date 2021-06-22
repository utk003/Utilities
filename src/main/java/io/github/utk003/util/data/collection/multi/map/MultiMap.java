package io.github.utk003.util.data.collection.multi.map;

import io.github.utk003.util.data.collection.multi.set.MultiSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

public interface MultiMap<K, V> extends Iterable<Map.Entry<K, V>> {
    int numMappings();
    boolean isEmpty();

    boolean contains(K key);
    boolean contains(K key, V value);

    @Nullable Collection<V> get(K key);

    boolean add(K key, V value);

    boolean remove(K key, V value);
    int removeAll(K key, V value);
    @Nullable Collection<V> removeAll(K key);

    @NotNull Set<K> uniqueKeys();
    @NotNull MultiSet<K> keys();

    @NotNull Collection<Map.Entry<K, V>> entries();

    @NotNull Collection<V> values();

    @NotNull Map.Entry<K, V>[] toArray();

    /**
     * Returns an unmodifiable {@code MultiMap} backed by the given {@code MultiMap}.
     * <p>
     * Changes in the original multimap will be reflected in the newly returned multimap.
     *
     * @param multiMap The multimap to wrap with an unmodifiability layer
     * @param <K>      The type of the keys in both multimaps
     * @param <V>      The type of the values in both multimap
     * @return An unmodifiable copy of the given {@code MultiMap} backed by the original multimap
     */
    static <K, V> @NotNull MultiMap<K, V> makeUnmodifiable(@NotNull MultiMap<K, V> multiMap) {
        return multiMap instanceof UnmodifiableMultiMap ? multiMap : new UnmodifiableMultiMap<>(multiMap);
    }

    @FunctionalInterface
    interface Constructor<K, T> extends Supplier<T>, Function<K, T> {
        T construct();

        @Override
        default T get() {
            return construct();
        }
        @Override
        default T apply(K key) {
            return construct();
        }
    }
}
