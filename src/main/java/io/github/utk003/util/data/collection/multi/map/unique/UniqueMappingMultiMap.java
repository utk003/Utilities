package io.github.utk003.util.data.collection.multi.map.unique;

import io.github.utk003.util.data.collection.multi.map.MultiMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Set;

public interface UniqueMappingMultiMap<K, V> extends MultiMap<K, V> {
    @Override
    @Nullable Set<V> get(K key);

    @Override
    @Nullable Set<V> removeAll(K key);

    @Override
    @NotNull Set<Map.Entry<K, V>> entries();

    /**
     * Returns an unmodifiable {@code UniqueMappingMultiMap} backed by the given {@code UniqueMappingMultiMap}.
     * <p>
     * Changes in the original multimap will be reflected in the newly returned multimap.
     *
     * @param multiMap The multimap to wrap with an unmodifiability layer
     * @param <K>      The type of the keys in both multimaps
     * @param <V>      The type of the values in both multimap
     * @return An unmodifiable copy of the given {@code UniqueMappingMultiMap} backed by the original multimap
     */
    static <K, V> @NotNull UniqueMappingMultiMap<K, V> makeUnmodifiable(@NotNull UniqueMappingMultiMap<K, V> multiMap) {
        return multiMap instanceof UnmodifiableUniqueMappingMultiMap ? multiMap : new UnmodifiableUniqueMappingMultiMap<>(multiMap);
    }
}
