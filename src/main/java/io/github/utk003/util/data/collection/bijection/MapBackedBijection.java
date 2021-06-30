/*
MIT License

Copyright (c) 2021 Utkarsh Priyam

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */

package io.github.utk003.util.data.collection.bijection;

import io.github.utk003.util.misc.Verifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * An implementation of {@link Bijection} that uses {@link Map}s to
 * maintain its bijective mappings.
 * <p>
 * This class can be extended very easily to create {@code Bijection}s
 * backed by specific map types, or the class can be directly instantiated
 * and used in its generic form as well.
 *
 * @param <A> The type of objects in the first set of this bijection
 * @param <B> The type of objects in the second set of this bijection
 * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
 * @version June 29, 2021
 * @see Bijection
 * @since 2.0.0
 */
public class MapBackedBijection<A, B> extends AbstractBijection<A, B> implements Bijection<A, B> {
    private final @NotNull Map<A, Bijection.Pairing<A, B>> MAP_FORWARDS;
    private final @NotNull Map<B, Bijection.Pairing<A, B>> MAP_BACKWARDS;

    /**
     * Creates a new {@code MapBackedBijection} backed by maps of the specified class.
     * <p>
     * This method takes a single {@link Class} object to instantiate
     * both maps that will hold this bijection's bijective mappings.
     *
     * @param mapType The {@code Class} object of this {@code MapBackedBijection}'s underlying {@code Map}s
     * @throws IllegalAccessException If one is thrown by the {@link Class#newInstance()} method
     * @throws InstantiationException If one is thrown by the {@link Class#newInstance()} method
     * @see #MapBackedBijection(Class, Class)
     */
    @SuppressWarnings("rawtypes")
    public MapBackedBijection(@NotNull Class<? extends Map> mapType)
            throws IllegalAccessException, InstantiationException {
        this(mapType, mapType);
    }
    /**
     * Creates a new {@code MapBackedBijection} backed by maps of the specified classes.
     * <p>
     * This method takes two {@link Class} objects to instantiate
     * the maps that will hold this bijection's bijective mappings.
     *
     * @param forwardMapType  The {@code Class} object of type {@code Map<A, Pairing<A, B>>}
     * @param backwardMapType The {@code Class} object of type {@code Map<B, Pairing<A, B>>}
     * @throws IllegalAccessException If one is thrown by the {@link Class#newInstance()} method
     * @throws InstantiationException If one is thrown by the {@link Class#newInstance()} method
     * @see io.github.utk003.util.data.collection.bijection.Bijection.Pairing
     */
    @SuppressWarnings("rawtypes")
    public MapBackedBijection(@NotNull Class<? extends Map> forwardMapType,
                              @NotNull Class<? extends Map> backwardMapType)
            throws IllegalAccessException, InstantiationException {
        //noinspection unchecked
        this(forwardMapType.newInstance(), backwardMapType.newInstance());
    }
    /**
     * Creates a new {@code MapBackedBijection} backed by the given maps.
     *
     * @param forwardMap  The {@code Map} object mapping objects of type {@code A} to {@code Pairing<A, B>}
     * @param backwardMap The {@code Map} object mapping objects of type {@code B} to {@code Pairing<A, B>}
     * @throws io.github.utk003.util.misc.VerificationException If the given maps do not initially create a proper bijective mapping
     * @see io.github.utk003.util.data.collection.bijection.Bijection.Pairing
     */
    public MapBackedBijection(@NotNull Map<A, Bijection.Pairing<A, B>> forwardMap,
                              @NotNull Map<B, Bijection.Pairing<A, B>> backwardMap) {
        MAP_FORWARDS = forwardMap;
        MAP_BACKWARDS = backwardMap;

        boolean isValid = true;
        for (Map.Entry<A, Pairing<A, B>> entry : MAP_FORWARDS.entrySet()) {
            Pairing<A, B> pairing = entry.getValue();
            if (pairing.FIRST != entry.getKey() || pairing != MAP_BACKWARDS.get(pairing.SECOND)) {
                isValid = false;
                break;
            }
        }
        if (!isValid)
            throw IllegalBijectiveStateException.lossOfBijectivity();
    }

    /**
     * Creates a new {@code MapBackedBijection} pairing {@code enum}s with objects.
     * <p>
     * This method is simply a wrapper for the {@link #MapBackedBijection(Map, Map)} constructor
     * that simplifies working with {@code enum}s and {@link EnumMap}s in creating a new
     * {@code MapBackedBijection}.
     *
     * @param enumClass The class object of the {@code enum} in the bijection
     * @param objectMap A {@code Map} object mapping objects of type {@code B} to bijection pairings
     * @param <A>       The class type of the {@code enum} in the bijection
     * @param <B>       The class type of the object in the bijection
     * @return The newly-constructed {@code enum}-object {@code MapBackedBijection}
     * @see #MapBackedBijection(Map, Map)
     */
    public static <A extends Enum<A>, B> @NotNull MapBackedBijection<A, B> createEnumObjectBijection(@NotNull Class<A> enumClass, @NotNull Map<B, Bijection.Pairing<A, B>> objectMap) {
        return new MapBackedBijection<>(new EnumMap<>(enumClass), objectMap);
    }
    /**
     * Creates a new {@code MapBackedBijection} pairing objects with {@code enum}s.
     * <p>
     * This method is simply a wrapper for the {@link #MapBackedBijection(Map, Map)} constructor
     * that simplifies working with {@code enum}s and {@link EnumMap}s in creating a new
     * {@code MapBackedBijection}.
     *
     * @param objectMap A {@code Map} object mapping objects of type {@code B} to bijection pairings
     * @param enumClass The class object of the {@code enum} in the bijection
     * @param <A>       The class type of the object in the bijection
     * @param <B>       The class type of the {@code enum} in the bijection
     * @return The newly-constructed object-{@code enum} {@code MapBackedBijection}
     * @see #MapBackedBijection(Map, Map)
     */
    public static <A, B extends Enum<B>> @NotNull MapBackedBijection<A, B> createObjectEnumBijection(@NotNull Map<A, Bijection.Pairing<A, B>> objectMap, @NotNull Class<B> enumClass) {
        return new MapBackedBijection<>(objectMap, new EnumMap<>(enumClass));
    }
    /**
     * Creates a new {@code MapBackedBijection} pairing {@code enum}s with {@code enum}s.
     * <p>
     * This method is simply a wrapper for the {@link #MapBackedBijection(Map, Map)} constructor
     * that simplifies working with {@code enum}s and {@link EnumMap}s in creating a new
     * {@code MapBackedBijection}.
     *
     * @param enumClass1 The class object of the first {@code enum} in the bijection
     * @param enumClass2 The class object of the second {@code enum} in the bijection
     * @param <A>        The class type of the first {@code enum} in the bijection
     * @param <B>        The class type of the second {@code enum} in the bijection
     * @return The newly-constructed {@code enum}-{@code enum} {@code MapBackedBijection}
     * @see #MapBackedBijection(Map, Map)
     */
    public static <A extends Enum<A>, B extends Enum<B>> @NotNull MapBackedBijection<A, B> createEnumEnumBijection(@NotNull Class<A> enumClass1, @NotNull Class<B> enumClass2) {
        return new MapBackedBijection<>(new EnumMap<>(enumClass1), new EnumMap<>(enumClass2));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        int s1 = MAP_FORWARDS.size(), s2 = MAP_BACKWARDS.size();
        if (s1 != s2)
            throw IllegalBijectiveStateException.lossOfBijectivity();
        return s1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @Nullable Pairing<A, B> getByFirst(@NotNull A obj1) {
        return MAP_FORWARDS.get(obj1);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public @Nullable Pairing<A, B> getBySecond(@NotNull B obj2) {
        return MAP_BACKWARDS.get(obj2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean add(@NotNull Pairing<A, B> pairing) {
        A a = pairing.FIRST;
        B b = pairing.SECOND;

        if (getByFirst(a) != null || getBySecond(b) != null)
            return false;

        MAP_FORWARDS.put(a, pairing);
        MAP_BACKWARDS.put(b, pairing);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(@NotNull A obj1, @NotNull B obj2) {
        if (!contains(obj1, obj2))
            return false;

        MAP_FORWARDS.remove(obj1);
        MAP_BACKWARDS.remove(obj2);
        return true;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public @Nullable Pairing<A, B> removeByFirst(@NotNull A obj1) {
        Pairing<A, B> pairing = MAP_FORWARDS.remove(obj1);
        if (pairing != null) {
            Pairing<A, B> other = MAP_BACKWARDS.remove(pairing.SECOND);
            if (!pairing.equals(other))
                throw IllegalBijectiveStateException.lossOfBijectivity();
        }
        return pairing;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public @Nullable Pairing<A, B> removeBySecond(@NotNull B obj2) {
        Pairing<A, B> pairing = MAP_BACKWARDS.remove(obj2);
        if (pairing != null) {
            Pairing<A, B> other = MAP_FORWARDS.remove(pairing.FIRST);
            if (!pairing.equals(other))
                throw IllegalBijectiveStateException.lossOfBijectivity();
        }
        return pairing;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Iterator<Pairing<A, B>> iterator() {
        return new PairingItr();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Iterator<A> iteratorOverFirsts() {
        return new FirstItr();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Iterator<B> iteratorOverSeconds() {
        return new SecondItr();
    }

    private @Nullable PairingSet pairingSet = null;
    private @Nullable FirstsSet setOfFirsts = null;
    private @Nullable SecondsSet setOfSeconds = null;
    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Set<Pairing<A, B>> pairingSet() {
        return pairingSet == null ? pairingSet = new PairingSet() : pairingSet;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Set<A> setOfFirsts() {
        return setOfFirsts == null ? setOfFirsts = new FirstsSet() : setOfFirsts;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Set<B> setOfSeconds() {
        return setOfSeconds == null ? setOfSeconds = new SecondsSet() : setOfSeconds;
    }

    /**
     * An iterator for {@link MapBackedBijection}s.
     * <p>
     * This class is used in the {@link MapBackedBijection#iterator()} method.
     *
     * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
     * @version June 29, 2021
     * @see MapBackedBijection
     * @see Iterator
     * @since 3.0.0
     */
    private final class PairingItr implements Iterator<Pairing<A, B>> {
        private final @NotNull Iterator<Pairing<A, B>> it = MAP_FORWARDS.values().iterator();
        private @Nullable Pairing<A, B> justReturned = null;

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean hasNext() {
            return it.hasNext();
        }
        /**
         * {@inheritDoc}
         */
        @Override
        public Pairing<A, B> next() {
            return justReturned = it.next();
        }
        /**
         * {@inheritDoc}
         */
        @Override
        public void remove() {
            if (justReturned == null)
                throw new IllegalStateException("No pairing to remove: call next() before each call to remove()");

            Pairing<A, B> rem = justReturned;
            justReturned = null;

            MapBackedBijection.this.remove(rem);
        }
    }

    /**
     * An iterator for {@link MapBackedBijection}s.
     * <p>
     * This class is used in the {@link MapBackedBijection#iteratorOverFirsts()} method.
     *
     * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
     * @version June 29, 2021
     * @see MapBackedBijection
     * @see Iterator
     * @since 3.0.0
     */
    private final class FirstItr implements Iterator<A> {
        private final @NotNull Iterator<A> it = MAP_FORWARDS.keySet().iterator();
        private @Nullable A justReturned = null;

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean hasNext() {
            return it.hasNext();
        }
        /**
         * {@inheritDoc}
         */
        @Override
        public A next() {
            return justReturned = it.next();
        }
        /**
         * {@inheritDoc}
         */
        @Override
        public void remove() {
            if (justReturned == null)
                throw new IllegalStateException("No pairing to remove: call next() before each call to remove()");

            A rem = justReturned;
            justReturned = null;

            MapBackedBijection.this.removeByFirst(rem);
        }
    }

    /**
     * An iterator for {@link MapBackedBijection}s.
     * <p>
     * This class is used in the {@link MapBackedBijection#iteratorOverSeconds()} method.
     *
     * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
     * @version June 29, 2021
     * @see MapBackedBijection
     * @see Iterator
     * @since 3.0.0
     */
    private final class SecondItr implements Iterator<B> {
        private final @NotNull Iterator<B> it = MAP_BACKWARDS.keySet().iterator();
        private @Nullable B justReturned = null;

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean hasNext() {
            return it.hasNext();
        }
        /**
         * {@inheritDoc}
         */
        @Override
        public B next() {
            return justReturned = it.next();
        }
        /**
         * {@inheritDoc}
         */
        @Override
        public void remove() {
            if (justReturned == null)
                throw new IllegalStateException("No pairing to remove: call next() before each call to remove()");

            B rem = justReturned;
            justReturned = null;

            MapBackedBijection.this.removeBySecond(rem);
        }
    }

    /**
     * An element set for {@link MapBackedBijection}s.
     * <p>
     * This class is used in the {@link MapBackedBijection#pairingSet()} method.
     *
     * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
     * @version June 29, 2021
     * @see MapBackedBijection
     * @see Set
     * @since 3.0.0
     */
    private final class PairingSet extends AbstractSet<Pairing<A, B>> implements Set<Pairing<A, B>> {
        /**
         * {@inheritDoc}
         */
        @Override
        public int size() {
            return MapBackedBijection.this.size();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean contains(Object o) {
            if (!(o instanceof Pairing<?, ?>))
                return false;
            Pairing<?, ?> pairing = (Pairing<?, ?>) o;
            //noinspection unchecked
            return MapBackedBijection.this.contains((A) pairing.FIRST, (B) pairing.SECOND);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean add(Pairing<A, B> pairing) {
            return MapBackedBijection.this.add(pairing);
        }
        /**
         * {@inheritDoc}
         */
        @Override
        public boolean remove(Object o) {
            if (!(o instanceof Pairing<?, ?>))
                return false;
            Pairing<?, ?> pairing = (Pairing<?, ?>) o;
            //noinspection unchecked
            return MapBackedBijection.this.remove((A) pairing.FIRST, (B) pairing.SECOND);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Iterator<Pairing<A, B>> iterator() {
            return new PairingItr();
        }
    }

    /**
     * An element set for {@link MapBackedBijection}s.
     * <p>
     * This class is used in the {@link MapBackedBijection#setOfFirsts()} method.
     *
     * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
     * @version June 29, 2021
     * @see MapBackedBijection
     * @see Set
     * @since 3.0.0
     */
    private final class FirstsSet extends AbstractSet<A> implements Set<A> {
        /**
         * {@inheritDoc}
         */
        @Override
        public int size() {
            return MapBackedBijection.this.size();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean contains(Object o) {
            //noinspection unchecked
            return o != null && MapBackedBijection.this.getByFirst((A) o) != null;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean add(A pairing) {
            throw new UnsupportedOperationException("MapBackedBijection's \"Set-of-Firsts\" does not support add()");
        }
        /**
         * {@inheritDoc}
         */
        @Override
        public boolean remove(Object o) {
            //noinspection unchecked
            return o != null && MapBackedBijection.this.removeByFirst((A) o) != null;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Iterator<A> iterator() {
            return new FirstItr();
        }
    }

    /**
     * An element set for {@link MapBackedBijection}s.
     * <p>
     * This class is used in the {@link MapBackedBijection#setOfSeconds()} method.
     *
     * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
     * @version June 29, 2021
     * @see MapBackedBijection
     * @see Set
     * @since 3.0.0
     */
    private final class SecondsSet extends AbstractSet<B> implements Set<B> {
        /**
         * {@inheritDoc}
         */
        @Override
        public int size() {
            return MapBackedBijection.this.size();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean contains(Object o) {
            //noinspection unchecked
            return o != null && MapBackedBijection.this.getBySecond((B) o) != null;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean add(B pairing) {
            throw new UnsupportedOperationException("MapBackedBijection's \"Set-of-Seconds\" does not support add()");
        }
        /**
         * {@inheritDoc}
         */
        @Override
        public boolean remove(Object o) {
            //noinspection unchecked
            return o != null && MapBackedBijection.this.removeBySecond((B) o) != null;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Iterator<B> iterator() {
            return new SecondItr();
        }
    }
}
