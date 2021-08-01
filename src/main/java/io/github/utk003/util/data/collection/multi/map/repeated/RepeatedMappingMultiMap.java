package io.github.utk003.util.data.collection.multi.map.repeated;

import io.github.utk003.util.data.collection.multi.map.MultiMap;
import io.github.utk003.util.data.collection.multi.set.MultiSet;
import io.github.utk003.util.misc.annotations.ScheduledForRelease;
import io.github.utk003.util.misc.annotations.RequiresDocumentation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Set;

@ScheduledForRelease(inVersion = "v2.2.0")
@RequiresDocumentation
public interface RepeatedMappingMultiMap<K, V> extends MultiMap<K, V> {
    int count(K key, V value);

    boolean contains(K key, V value, int count);
    int add(K key, V value, int count);
    int remove(K key, V value, int count);

    @Nullable Set<V> getUnique(K key);
    @Override
    @Nullable MultiSet<V> get(K key);

    @Override
    @Nullable MultiSet<V> removeAll(K key);

    @NotNull Set<Map.Entry<K, V>> uniqueEntries();
    @Override
    @NotNull MultiSet<Map.Entry<K, V>> entries();

    /**
     * Returns an unmodifiable {@code RepeatedMappingMultiMap} backed by the given {@code RepeatedMappingMultiMap}.
     * <p>
     * Changes in the original multimap will be reflected in the newly returned multimap.
     *
     * @param multiMap The multimap to wrap with an unmodifiability layer
     * @param <K>      The type of the keys in both multimaps
     * @param <V>      The type of the values in both multimap
     * @return An unmodifiable copy of the given {@code RepeatedMappingMultiMap} backed by the original multimap
     */
    static <K, V> @NotNull RepeatedMappingMultiMap<K, V> makeUnmodifiable(@NotNull RepeatedMappingMultiMap<K, V> multiMap) {
        return multiMap instanceof UnmodifiableRepeatedMappingMultiMap ? multiMap : new UnmodifiableRepeatedMappingMultiMap<>(multiMap);
    }
}
