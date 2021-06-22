package io.github.utk003.util.data.collection.multi.map.unique;

import io.github.utk003.util.data.collection.multi.set.MultiSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

final class UnmodifiableUniqueMappingMultiMap<K, V> extends AbstractUniqueMappingMultiMap<K, V> implements UniqueMappingMultiMap<K, V> {
    private final @NotNull UniqueMappingMultiMap<K, V> MULTI_MAP;
    public UnmodifiableUniqueMappingMultiMap(@NotNull UniqueMappingMultiMap<K, V> wrapping) {
        MULTI_MAP = wrapping;
    }

    @Override
    public int numMappings() {
        return MULTI_MAP.numMappings();
    }

    @Override
    public @Nullable Set<V> get(K key) {
        Set<V> ret = MULTI_MAP.get(key);
        return ret != null ? Collections.unmodifiableSet(ret) : null;
    }

    @Override
    public boolean add(K key, V value) {
        throw new UnsupportedOperationException("Unmodifiable multimaps cannot add elements");
    }

    @Override
    public boolean remove(K key, V value) {
        throw new UnsupportedOperationException("Unmodifiable multimaps cannot remove elements");
    }
    @Override
    public int removeAll(K key, V value) {
        throw new UnsupportedOperationException("Unmodifiable multimaps cannot remove elements");
    }
    @Override
    public @Nullable Set<V> removeAll(K key) {
        throw new UnsupportedOperationException("Unmodifiable multimaps cannot remove elements");
    }

    @Override
    public @NotNull Set<K> uniqueKeys() {
        return Collections.unmodifiableSet(MULTI_MAP.uniqueKeys());
    }
    @Override
    public @NotNull MultiSet<K> keys() {
        return MultiSet.makeUnmodifiable(MULTI_MAP.keys());
    }

    @Override
    public @NotNull Set<Map.Entry<K, V>> entries() {
        return Collections.unmodifiableSet(MULTI_MAP.entries());
    }
    @Override
    public @NotNull Collection<V> values() {
        return Collections.unmodifiableCollection(MULTI_MAP.values());
    }

    @Override
    public @NotNull Iterator<Map.Entry<K, V>> iterator() {
        return new UnmodifiableItr();
    }

    private class UnmodifiableItr implements Iterator<Map.Entry<K, V>> {
        private final @NotNull Iterator<Map.Entry<K, V>> IT = MULTI_MAP.iterator();

        @Override
        public boolean hasNext() {
            return IT.hasNext();
        }
        @Override
        public Map.Entry<K, V> next() {
            return IT.next();
        }
    }
}
