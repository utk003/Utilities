package io.github.utk003.util.data.collection.multi.map.unique;

import io.github.utk003.util.data.collection.multi.set.AbstractMultiSet;
import io.github.utk003.util.data.collection.multi.set.MultiSet;
import io.github.utk003.util.misc.annotations.ScheduledForRelease;
import io.github.utk003.util.misc.annotations.RequiresDocumentation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

@ScheduledForRelease(inVersion = "v2.2.0")
@RequiresDocumentation
public class SetMapBackedUniqueMappingMultiMap<K, V> extends AbstractUniqueMappingMultiMap<K, V> implements UniqueMappingMultiMap<K, V> {
    private final @NotNull Map<K, Set<V>> SET_MAP;
    private final @NotNull Constructor<K, Set<V>> SET_MAKER;
    private int numMappings = 0;

    /**
     * Creates a new {@code SetMapBackedUniqueMappingMultiMap} backed by a map of the specified class.
     * <p>
     * This method takes a {@link Class} object to instantiate
     * the map and the sets that will hold this multimap's elements.
     *
     * @param underlyingMapType The {@code Class} object of type {@code Map<K, Set<V>>}
     * @param underlyingSetType The {@code Class} object of type {@code Set<V>}
     * @throws IllegalAccessException If one is thrown by the {@link Class#newInstance()} method
     * @throws InstantiationException If one is thrown by the {@link Class#newInstance()} method
     */
    @SuppressWarnings("rawtypes")
    public SetMapBackedUniqueMappingMultiMap(@NotNull Class<? extends Map> underlyingMapType,
                                             @NotNull Class<? extends Set> underlyingSetType)
            throws IllegalAccessException, InstantiationException {
        //noinspection unchecked
        this(underlyingMapType.newInstance(), wrapClassTypeIntoConstructor(underlyingSetType));
    }
    /**
     * Creates a new {@code MapBackedMultiSet} backed by the given map.
     *
     * @param underlyingMap The {@code Map} object mapping objects of type {@code E} to {@code Integer}s
     * @param setMaker A {@link Constructor} for sets of type {@code Set<V>}
     */
    public SetMapBackedUniqueMappingMultiMap(@NotNull Map<K, Set<V>> underlyingMap, @NotNull Constructor<K, Set<V>> setMaker) {
        SET_MAP = underlyingMap;
        SET_MAKER = setMaker;

        for (Set<V> set : underlyingMap.values())
            numMappings += set.size();
    }

    @Override
    public int numMappings() {
        return numMappings;
    }

    @Override
    public @Nullable Set<V> get(K key) {
        return SET_MAP.get(key);
    }

    @Override
    public boolean add(K key, V value) {
        boolean added = SET_MAP.computeIfAbsent(key, SET_MAKER).add(value);
        if (added) numMappings++;
        return added;
    }

    @Override
    public boolean remove(K key, V value) {
        Set<V> values = SET_MAP.get(key);
        if (values == null)
            return false;

        boolean removed = values.remove(value);
        if (values.isEmpty())
            SET_MAP.remove(key);
        if (removed) numMappings--;
        return removed;
    }
    @Override
    public @Nullable Set<V> removeAll(K key) {
        Set<V> removed = SET_MAP.remove(key);
        if (removed != null) numMappings -= removed.size();
        return removed;
    }

    private @Nullable MultiSet<K> keyMultiSet = null;
    @Override
    public @NotNull MultiSet<K> keys() {
        return keyMultiSet == null ? keyMultiSet = new KeyMultiSet() : keyMultiSet;
    }
    private class KeyMultiSet extends AbstractMultiSet<K> implements MultiSet<K> {
        @Override
        public int size() {
            return numMappings;
        }

        @Override
        public int count(K obj) {
            Set<V> values = get(obj);
            return values != null ? values.size() : 0;
        }

        @Override
        public boolean add(K k, int count) {
            throw new UnsupportedOperationException("SetMapBackedUniqueMappingMultiMap keysets cannot add elements to multimap");
        }
        @Override
        public int remove(K k, int count) {
            throw new UnsupportedOperationException("SetMapBackedUniqueMappingMultiMap keysets cannot remove elements from multimap");
        }

        @Override
        public @NotNull Set<K> uniqueElements() {
            return SET_MAP.keySet();
        }

        @Override
        public @NotNull Iterator<K> iterator() {
            return new Itr();
        }

        private class Itr implements Iterator<K> {
            private final @NotNull java.util.Iterator<Map.Entry<K, Set<V>>> IT = SET_MAP.entrySet().iterator();

            private @Nullable Map.Entry<K, Set<V>> currEntry = null;
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex != 0 || IT.hasNext();
            }

            @Override
            public K next() {
                if (!hasNext())
                    throw new IllegalStateException();

                if (currEntry == null) {
                    if (currentIndex != 0)
                        throw new IllegalStateException();

                    currEntry = IT.next();
                    currentIndex = currEntry.getValue().size();
                } else if (currentIndex <= 0) {
                    currEntry = IT.next();
                    currentIndex = currEntry.getValue().size();
                }
                if (currentIndex <= 0)
                    throw new IllegalStateException();

                currentIndex--;
                return currEntry.getKey();
            }
        }
    }

    private @Nullable Set<Map.Entry<K, V>> entrySet = null;
    @Override
    public @NotNull Set<Map.Entry<K, V>> entries() {
        return entrySet == null ? entrySet = new EntrySet() : entrySet;
    }
    private class EntrySet extends AbstractSet<Map.Entry<K, V>> implements Set<Map.Entry<K, V>> {
        @Override
        public int size() {
            return numMappings;
        }

        @Override
        public boolean contains(Object obj) {
            //noinspection unchecked
            Map.Entry<K, V> entry = (Map.Entry<K, V>) obj;
            return SetMapBackedUniqueMappingMultiMap.this.contains(entry.getKey(), entry.getValue());
        }
        @Override
        public boolean add(Map.Entry<K, V> entry) {
            return SetMapBackedUniqueMappingMultiMap.this.add(entry.getKey(), entry.getValue());
        }
        @Override
        public boolean remove(Object obj) {
            //noinspection unchecked
            Map.Entry<K, V> entry = (Map.Entry<K, V>) obj;
            return SetMapBackedUniqueMappingMultiMap.this.remove(entry.getKey(), entry.getValue());
        }

        @Override
        public @NotNull Iterator<Map.Entry<K, V>> iterator() {
            return new Itr();
        }

        private class Itr implements Iterator<Map.Entry<K, V>> {
            private final @NotNull Iterator<Map.Entry<K, Set<V>>> IT = SET_MAP.entrySet().iterator();

            private @Nullable Map.Entry<K, Set<V>> currEntry = null;
            private @Nullable Iterator<V> it = null;

            private @Nullable Map.Entry<K, V> prevRet = null;

            @Override
            public boolean hasNext() {
                return IT.hasNext() || it != null && it.hasNext();
            }

            @Override
            public Map.Entry<K, V> next() {
                if (!hasNext())
                    throw new IllegalStateException();

                if (currEntry == null) {
                    if (it != null && it.hasNext())
                        throw new IllegalStateException();

                    currEntry = IT.next();
                    it = currEntry.getValue().iterator();
                } else if (it == null || !it.hasNext()) {
                    currEntry = IT.next();
                    it = currEntry.getValue().iterator();
                }
                if (!it.hasNext())
                    throw new IllegalStateException();

                return prevRet = new Entry(currEntry.getKey(), it.next());
            }

            @Override
            public void remove() {
                if (prevRet == null)
                    throw new IllegalStateException();

                Map.Entry<K, V> entry = prevRet;
                prevRet = null;

                if (currEntry == null)
                    throw new IllegalStateException();
                Set<V> values = currEntry.getValue();
                if (values.isEmpty())
                    throw new IllegalStateException();

                if (!values.remove(entry.getValue()))
                    throw new IllegalStateException();

                numMappings--;
                if (values.isEmpty()) {
                    IT.remove();
                    currEntry = null;
                }
            }

            private class Entry implements Map.Entry<K, V> {
                private final K key;
                private V value;

                public Entry(K key, V value) {
                    this.key = key;
                    this.value = value;
                }

                @Override
                public K getKey() {
                    return key;
                }
                @Override
                public V getValue() {
                    return value;
                }

                @Override
                public V setValue(V value) {
                    V oldValue = this.value;

                    boolean removed = SetMapBackedUniqueMappingMultiMap.this.remove(key, oldValue);
                    boolean added = SetMapBackedUniqueMappingMultiMap.this.add(key, value);

                    //noinspection StatementWithEmptyBody
                    if (removed && added) ;
                    else throw new IllegalStateException();

                    this.value = value;

                    return oldValue;
                }
            }
        }
    }

    private @Nullable Collection<V> valueSet = null;
    @Override
    public @NotNull Collection<V> values() {
        return valueSet == null ? valueSet = new ValueSet() : valueSet;
    }
    private class ValueSet extends AbstractCollection<V> implements Collection<V> {
        @Override
        public int size() {
            return numMappings;
        }

        @Override
        public boolean add(V value) {
            throw new UnsupportedOperationException("SetMapBackedUniqueMappingMultiMap value sets cannot add elements to multimap");
        }
        @Override
        public boolean remove(Object obj) {
            throw new UnsupportedOperationException("SetMapBackedUniqueMappingMultiMap value sets cannot remove elements from multimap");
        }

        @Override
        public @NotNull Iterator<V> iterator() {
            return new Itr();
        }

        private class Itr implements Iterator<V> {
            private final @NotNull java.util.Iterator<Map.Entry<K, Set<V>>> IT = SET_MAP.entrySet().iterator();

            private @Nullable Map.Entry<K, Set<V>> currEntry = null;
            private @Nullable Iterator<V> it = null;

            @Override
            public boolean hasNext() {
                return IT.hasNext() || it != null && it.hasNext();
            }

            @Override
            public V next() {
                if (!hasNext())
                    throw new IllegalStateException();

                if (currEntry == null) {
                    if (it != null && it.hasNext())
                        throw new IllegalStateException();

                    currEntry = IT.next();
                    it = currEntry.getValue().iterator();
                } else if (it == null || !it.hasNext()) {
                    currEntry = IT.next();
                    it = currEntry.getValue().iterator();
                }

                if (!it.hasNext())
                    throw new IllegalStateException();
                return it.next();
            }
        }
    }

    @Override
    public @NotNull Iterator<Map.Entry<K, V>> iterator() {
        return entries().iterator();
    }

    @SuppressWarnings("rawtypes")
    private static <K, V> @NotNull Constructor<K, Set<V>> wrapClassTypeIntoConstructor(@NotNull Class<? extends Set> clazz) {
        return () -> {
            try {
                //noinspection unchecked
                return clazz.newInstance();
            } catch (Exception e) {
                throw new SetConstructionException(e);
            }
        };
    }

    public static class SetConstructionException extends RuntimeException {
        /**
         * Constructs a new set construction exception
         *
         * @see RuntimeException#RuntimeException()
         */
        public SetConstructionException() {
            super();
        }

        /**
         * Constructs a new set construction exception with the specified detail message
         *
         * @param message The detail message
         * @see RuntimeException#RuntimeException(String)
         */
        public SetConstructionException(String message) {
            super(message);
        }

        /**
         * Constructs a new set construction exception with the specified cause
         *
         * @param cause The cause
         * @see RuntimeException#RuntimeException(Throwable)
         */
        public SetConstructionException(Throwable cause) {
            super(cause);
        }
    }
}
