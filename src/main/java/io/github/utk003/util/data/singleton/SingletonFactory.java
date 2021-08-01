package io.github.utk003.util.data.singleton;

import io.github.utk003.util.data.collection.bijection.Bijection;
import io.github.utk003.util.data.collection.bijection.MapBackedBijection;
import io.github.utk003.util.misc.Verifier;
import io.github.utk003.util.misc.annotations.ScheduledForRelease;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

/**
 * A factory for {@link Singleton}.
 *
 * @param <S> The class type of the singleton this class creates
 * @param <F> The class type of the singleton factory this class represents
 * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
 * @version July 26, 2021
 * @see Singleton
 * @since 2.3.0
 */
@ScheduledForRelease(inVersion = "v2.3.0")
public abstract class SingletonFactory<S extends Singleton<S, F>, F extends SingletonFactory<S, F>> {
    /**
     * The {@link Bijection} backing this {@code SingletonFactory}.
     */
    private final @NotNull Bijection<String, S> SINGLETONS = new MapBackedBijection<>(new HashMap<>(), new HashMap<>());

    /**
     * Returns an array of all singletons currently defined through this factory.
     * <p>
     * This is equivalent to <pre>values.toArray((S[]) new Singleton[]).</pre>
     *
     * @return An array of all singletons currently defined through this factory
     */
    public @NotNull S[] currentValues() {
        //noinspection unchecked
        return (S[]) values().toArray();
    }
    /**
     * Returns a dynamically-updating, modifiable {@code Set} of all singletons
     * defined by this factory.
     * <p>
     * As the returned set is both dynamically-updating and modifiable,
     * changes to the set will be reflected in this factory. As a result,
     * the set's {@link Set#remove(Object)} can be used to "un-define" singletons.
     * However, singletons cannot be added to the factory in this way.
     *
     * @return A dynamic, mutable set of singletons defined by this factory
     */
    public @NotNull Set<S> values() {
        return SINGLETONS.setOfSeconds();
    }

    /**
     * An unmodifiable copy of the set returned by {@link #values()}.
     */
    final @NotNull @Unmodifiable Set<S> UNMODIFIABLE_VALUES = Collections.unmodifiableSet(values());

    /**
     * Returns the singleton defined by this factory whose key is {@code key},
     * or {@code null} if no such singleton exists.
     *
     * @param key The singleton key to check for
     * @return The singleton defined by this factory whose key is {@code key}, if one exists; otherwise, {@code null}
     */
    public @Nullable S get(@NotNull String key) {
        return Bijection.Pairing.getSecond(SINGLETONS.getByFirst(key));
    }
    /**
     * Returns the singleton defined by this factory whose key is {@code key},
     * or throws a {@link NoSuchSingletonException} if no such singleton exists.
     *
     * @param key The singleton key to check for
     * @return The singleton defined by this factory whose key is {@code key}, if one exists
     * @throws NoSuchSingletonException If no such singleton exists
     */
    public @NotNull S getOrFail(@NotNull String key) {
        return Verifier.requireNotNull(
                SINGLETONS.getByFirst(key),
                NoSuchSingletonException.class,
                "Singleton with key \"" + key + "\" does not exist"
        ).getSecond();
    }

    /**
     * Creates a new {@link Singleton} with the specified key.
     * <p>
     * This method is for internal use only, to allow for the existence of
     * custom {@code Singleton} and {@code SingletonFactory} implementations.
     *
     * @param key The string key of the new singleton
     * @return The newly created {@code Singleton}
     */
    @ApiStatus.Internal
    protected abstract @NotNull S constructSingleton(@NotNull String key);

    /**
     * Defines the given key-singleton pair in this factory.
     * <p>
     * The given singleton must have been created by this factory.
     * In other words, {@link Singleton#sharesFactoryWith(Singleton)}
     * should return {@code true} when run for any other singleton
     * defined by {@code this}.
     * <p>
     * This method is for internal use only, to allow for the existence of
     * custom {@code Singleton} and {@code SingletonFactory} implementations.
     *
     * @param key       The string key of the new singleton
     * @param singleton The new singleton instance
     */
    @ApiStatus.Internal
    protected void defineSingleton(@NotNull String key, @NotNull S singleton) {
        SINGLETONS.add(key, singleton);
    }

    /**
     * Gets the singleton defined by this factory whose key is {@code key},
     * or defines a new singleton with that key if none exists.
     * <p>
     * If a custom {@link Singleton} implementation permits additional
     * data storage, then this method can be overridden to provide
     * default values for that data or to remove an argument-free
     * creation method altogether.
     *
     * @param key The key of the singleton to get
     * @return The (possibly newly created) singleton whose key is {@code key}
     */
    public @NotNull S getOrDefine(@NotNull String key) {
        S singleton = get(key);
        if (singleton == null) {
            singleton = constructSingleton(key);
            defineSingleton(key, singleton);
        }
        return singleton;
    }

    /**
     * Removes the identified singleton from this factory, if it exists.
     *
     * @param key The key of the singleton to remove
     * @return The removed singleton, if it exists; otherwise, {@code null}
     * @see #remove(Singleton)
     */
    public @Nullable S remove(@NotNull String key) {
        return Bijection.Pairing.getSecond(SINGLETONS.removeByFirst(key));
    }
    /**
     * Removes the identified singleton from this factory, if it exists.
     *
     * @param singleton The singleton instance to remove
     * @return The removed singleton, if it exists; otherwise, {@code null}
     * @see #remove(String)
     */
    public @Nullable S remove(@NotNull S singleton) {
        return Bijection.Pairing.getSecond(SINGLETONS.removeBySecond(singleton));
    }
}
