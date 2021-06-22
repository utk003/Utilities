package io.github.utk003.util.data.collection.multi.map;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public abstract class AbstractMultiMap<K, V> implements MultiMap<K, V> {
    @Override
    public boolean isEmpty() {
        return numMappings() == 0;
    }
    @Override
    public boolean contains(K key) {
        Collection<V> values = get(key);
        return values != null && !values.isEmpty();
    }
    @Override
    public boolean contains(K key, V value) {
        Collection<V> values = get(key);
        return values != null && values.contains(value);
    }

    @Override
    public int removeAll(K key, V value) {
        int count = 0;
        while (remove(key, value))
            count++;
        return count;
    }

    @Override
    public @NotNull Set<K> uniqueKeys() {
        return keys().uniqueElements();
    }

    @Override
    public @NotNull Map.Entry<K, V>[] toArray() {
        //noinspection unchecked
        Map.Entry<K, V>[] arr = (Map.Entry<K, V>[]) new Map.Entry[numMappings()];

        int i = 0;
        for (Map.Entry<K, V> mapping : this)
            arr[i++] = mapping;

        return arr;
    }
}
