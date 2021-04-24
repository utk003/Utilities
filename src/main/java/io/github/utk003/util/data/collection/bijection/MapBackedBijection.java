package io.github.utk003.util.data.collection.bijection;

import io.github.utk003.util.misc.Verifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.Map;

/**
 * An implementation of {@link Bijection} that uses {@link Map}s to
 * maintain its bijective mappings.
 * <p>
 * This class can be extended very easily to create {@code Bijection}s
 * backed by specific map types, or the class can be directly instantiated
 * and used in its generic form as well.
 *
 * @param <A>  The type of objects in the first set of this bijection
 * @param <B>  The type of objects in the second set of this bijection
 * @param <M1> The type of the {@code Map} that maps the first set's objects to their mappings
 * @param <M2> The type of the {@code Map} that maps the second set's objects to their mappings
 * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
 * @version April 23, 2021
 * @see Bijection
 * @since 2.0.0
 */
public class MapBackedBijection<A, B, M1 extends Map<A, Bijection.Pairing<A, B>>,
        M2 extends Map<B, Bijection.Pairing<A, B>>> extends AbstractBijection<A, B> implements Bijection<A, B> {
    private final @NotNull M1 MAP_FORWARDS;
    private final @NotNull M2 MAP_BACKWARDS;

    /**
     * Creates a new {@code MapBackedBijection} backed by maps of the specified classes.
     * <p>
     * This method takes two {@link Class} objects to instantiate
     * the maps that will hold this bijection's bijective mappings.
     *
     * @param forwardMapType  The {@code Class} object of the type parameter {@code M1}
     * @param backwardMapType The {@code Class} object of the type parameter {@code M2}
     * @throws IllegalAccessException If one is thrown by the {@link Class#newInstance()} method
     * @throws InstantiationException If one is thrown by the {@link Class#newInstance()} method
     */
    public MapBackedBijection(@NotNull Class<M1> forwardMapType, @NotNull Class<M2> backwardMapType)
            throws IllegalAccessException, InstantiationException {
        this(forwardMapType.newInstance(), backwardMapType.newInstance());
    }
    /**
     * Creates a new {@code MapBackedBijection} backed by the given maps.
     *
     * @param forwardMap  The {@code Map} object of the type {@code M1}
     * @param backwardMap The {@code Map} object of the type {@code M2}
     * @throws io.github.utk003.util.misc.VerificationException If the given maps do not initially create a proper bijective mapping
     */
    public MapBackedBijection(@NotNull M1 forwardMap, @NotNull M2 backwardMap) {
        MAP_FORWARDS = forwardMap;
        MAP_BACKWARDS = backwardMap;

        verifyBijectivity();
    }

    /**
     * Verifies that the {@code Map}s used to create this bijection create a valid bijective mapping.
     * <p>
     * This method throws a {@link io.github.utk003.util.misc.VerificationException} (via the {@link Verifier} class)
     * if the bijectivity of this bijection (as stored within the {@code Map}s used to create it) is violated.
     */
    private void verifyBijectivity() {
        boolean isValid = true;
        for (Map.Entry<A, Pairing<A, B>> entry : MAP_FORWARDS.entrySet()) {
            Pairing<A, B> pairing = entry.getValue();
            if (pairing.FIRST != entry.getKey() || pairing != MAP_BACKWARDS.get(pairing.SECOND)) {
                isValid = false;
                break;
            }
        }
        Verifier.requireTrue(isValid, LOSS_OF_BIJECTIVITY_ERROR_MESSAGE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return Verifier.requireEqual(
                MAP_FORWARDS.size(),
                MAP_BACKWARDS.size(),
                LOSS_OF_BIJECTIVITY_ERROR_MESSAGE
        );
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Iterator<Pairing<A, B>> iterator() {
        return new PairIterator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsPairing(@NotNull Pairing<A, B> pairing) {
        return Verifier.requireEqual(
                pairing.equals(MAP_FORWARDS.get(pairing.FIRST)),
                pairing.equals(MAP_BACKWARDS.get(pairing.SECOND)),
                LOSS_OF_BIJECTIVITY_ERROR_MESSAGE
        );
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addPairing(@NotNull Pairing<A, B> pairing) {
        if (MAP_FORWARDS.get(pairing.FIRST) != null || MAP_BACKWARDS.get(pairing.SECOND) != null)
            return false;

        MAP_FORWARDS.put(pairing.FIRST, pairing);
        MAP_BACKWARDS.put(pairing.SECOND, pairing);
        return true;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removePairing(@NotNull Pairing<A, B> pairing) {
        if (!containsPairing(pairing))
            return false;

        MAP_FORWARDS.remove(pairing.FIRST);
        MAP_BACKWARDS.remove(pairing.SECOND);
        return true;
    }

    /**
     * An iterator for {@link MapBackedBijection}s.
     * <p>
     * This class is used in the {@link MapBackedBijection#iterator()} method.
     *
     * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
     * @version April 23, 2021
     * @see MapBackedBijection
     * @see Iterator
     * @since 2.0.0
     */
    private class PairIterator implements Iterator<Pairing<A, B>> {
        private final @NotNull Iterator<Map.Entry<A, Pairing<A, B>>> BACKING_ITERATOR = MAP_FORWARDS.entrySet().iterator();
        private @Nullable Pairing<A, B> previousPairing = null;

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean hasNext() {
            return BACKING_ITERATOR.hasNext();
        }
        /**
         * {@inheritDoc}
         */
        @Override
        public Pairing<A, B> next() {
            return previousPairing = BACKING_ITERATOR.next().getValue();
        }
        /**
         * {@inheritDoc}
         */
        @Override
        public void remove() {
            BACKING_ITERATOR.remove();
            assert previousPairing != null; // if false, then remove() call on previous line will throw exception first
            Verifier.requireEqual(
                    MAP_BACKWARDS.remove(previousPairing.SECOND),
                    previousPairing,
                    LOSS_OF_BIJECTIVITY_ERROR_MESSAGE
            );
            previousPairing = null;
        }
    }
}
