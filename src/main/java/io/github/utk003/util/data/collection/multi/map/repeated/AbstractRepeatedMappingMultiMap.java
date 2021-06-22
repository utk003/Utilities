package io.github.utk003.util.data.collection.multi.map.repeated;

import io.github.utk003.util.data.collection.multi.map.AbstractMultiMap;
import io.github.utk003.util.data.collection.multi.set.MultiSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Set;

public abstract class AbstractRepeatedMappingMultiMap<K, V> extends AbstractMultiMap<K, V> implements RepeatedMappingMultiMap<K, V> {
    @Override
    public int count(K key, V value) {
        MultiSet<V> values = get(key);
        return values == null ? 0 : values.count(value);
    }
    @Override
    public boolean contains(K key, V value) {
        return count(key, value) >= 1;
    }
    @Override
    public boolean contains(K key, V value, int count) {
        return count(key, value) >= count;
    }

    @Override
    public @Nullable Set<V> getUnique(K key) {
        MultiSet<V> values = get(key);
        return values != null ? values.uniqueElements() : null;
    }

    @Override
    public boolean add(K key, V value) {
        return add(key, value, 1) != 0;
    }

    @Override
    public boolean remove(K key, V value) {
        return remove(key, value, 1) != 0;
    }
    @Override
    public int removeAll(K key, V value) {
        return remove(key, value, count(key, value));
    }

    @Override
    public @NotNull Set<Map.Entry<K, V>> uniqueEntries() {
        return entries().uniqueElements();
    }
}
