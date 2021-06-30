package io.github.utk003.util.data.singleton;

import io.github.utk003.util.data.collection.bijection.Bijection;
import io.github.utk003.util.data.collection.bijection.MapBackedBijection;
import io.github.utk003.util.func.Lambda2;
import io.github.utk003.util.misc.Verifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Set;

final class DefaultSingletonFactory<S extends Singleton> extends SingletonFactory<S> {
    private final @NotNull Bijection<String, S> ENUM_BIJECTION = new MapBackedBijection<>(new HashMap<>(), new HashMap<>());
    private @NotNull Lambda2<S, String, Object[]> singletonMaker;

    /**
     * Creates a new {@code DefaultSingletonFactory} with the given singleton creator lambda function.
     *
     * @param singletonMaker This {@code DefaultSingletonFactory}'s singleton creator
     */
    public DefaultSingletonFactory(@NotNull Lambda2<S, String, Object[]> singletonMaker) {
        this.singletonMaker = singletonMaker;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull S[] currentValues() {
        //noinspection unchecked
        return values().toArray((S[]) new Singleton[0]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Set<S> values() {
        return ENUM_BIJECTION.setOfSeconds();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @Nullable S get(@NotNull String key) {
        Bijection.Pairing<String, S> pairing = ENUM_BIJECTION.getByFirst(key);
        return pairing != null ? pairing.getSecond() : null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull S add(@NotNull String key, @NotNull Object... args) {
        S singleton = get(key);
        if (singleton != null)
            return singleton;

        singleton = singletonMaker.get(key, args);
        Verifier.requireTrue(
                ENUM_BIJECTION.add(key, singleton),
                "Unable to create singleton " + singleton
        );
        return singleton;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public @Nullable S remove(@NotNull String key) {
        Bijection.Pairing<String, S> pairing = ENUM_BIJECTION.removeByFirst(key);
        return pairing != null ? pairing.getSecond() : null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeSingletonMaker(@NotNull Lambda2<S, String, Object[]> newSingletonMaker) {
        singletonMaker = newSingletonMaker;
    }
}
