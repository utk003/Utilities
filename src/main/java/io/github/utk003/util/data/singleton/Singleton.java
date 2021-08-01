package io.github.utk003.util.data.singleton;

import io.github.utk003.util.misc.annotations.ScheduledForRelease;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Set;

/**
 * A runtime-modifiable equivalent of {@code enum}s.
 * <p>
 * {@code Singleton} and the corresponding factory {@link SingletonFactory}
 * introduce the possibility for runtime-modifiable {@code enum}s.
 * Individual factory instances independently track "defined" singletons,
 * so there can be multiple separate pools of enums for singletons of the same class.
 * Additionally, the unique {@code String} key {@link #KEY} allows for singletons
 * to effectively be compared in {@code switch} statements. Finally, custom
 * extensions can replicate the effect of instance methods and variables for
 * {@code enum}s, allowing singletons to carry additional data.
 * <p>
 * TODO improve Singleton documentation
 *
 * @param <S> The class type of the singleton this class represents
 * @param <F> The class type of the factory that creates this type of singleton
 * @author Utkarsh Priyam (<a href="https://github.com/utk003" target="_top">utk003</a>)
 * @version July 26, 2021
 * @see SingletonFactory
 * @since 2.3.0
 */
@ScheduledForRelease(inVersion = "v2.3.0")
public abstract class Singleton<S extends Singleton<S, F>, F extends SingletonFactory<S, F>> {
    /**
     * A dynamically updating, unmodifiable {@code Set} of all {@code Singleton}s defined in the
     * same factory as {@code this} {@code Singleton}.
     * <p>
     * This set acts identically to a unique identifier for the {@code SingletonFactory}
     * that created this {@code Singleton}. A direct reference to the factory was not
     * included to permit {@code private} or limited-access factory set-ups.
     */
    public final @NotNull @Unmodifiable Set<S> ALL_VALUES;
    /**
     * A {@code String} key that uniquely identifies this singleton from all
     * singletons defined by a factory (or equivalently contained in {@link #ALL_VALUES}).
     */
    public final @NotNull String KEY;

    /**
     * Creates a new {@code Singleton} with the given {@code String} key bound to the given
     * {@link SingletonFactory}.
     * <p>
     * Although protected, this constructor (and constructors for any extending classes)
     * should not be called directly. These should be reserved for the
     * {@link SingletonFactory#constructSingleton(String)}, so as to
     * maintain the uniqueness of created singletons.
     *
     * @param key     The key for this {@code Singleton}
     * @param factory The factory this {@code Singleton} was created by
     */
    @ApiStatus.Internal
    protected Singleton(@NotNull String key, @NotNull F factory) {
        KEY = key;
        ALL_VALUES = factory.UNMODIFIABLE_VALUES;
    }

    /**
     * Checks whether the given singleton was created by the same factory as {@code this}.
     * <p>
     * This is equivalent to <pre>this.ALL_VALUES == singleton.ALL_VALUES.</pre>
     *
     * @param singleton The other singleton to check
     * @return {@code true}, if {@code this} singleton was created by the same factory
     * as the other singleton; otherwise, {@code false}
     * @see #ALL_VALUES
     * @see #shareFactories(Singleton, Singleton)
     */
    public boolean sharesFactoryWith(@NotNull Singleton<?, ?> singleton) {
        return ALL_VALUES == singleton.ALL_VALUES;
    }
    /**
     * Checks whether the given singletons were created by the same factory.
     *
     * @param singleton1 The first singleton to check
     * @param singleton2 The second singleton to check
     * @return {@code true}, if the singletons were created by the same factory; otherwise, {@code false}
     * @see #ALL_VALUES
     * @see #sharesFactoryWith(Singleton)
     */
    public static boolean shareFactories(@NotNull Singleton<?, ?> singleton1, @NotNull Singleton<?, ?> singleton2) {
        return singleton1.ALL_VALUES == singleton2.ALL_VALUES;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull String toString() {
        return "Singleton(key=" + KEY + ")";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return KEY.hashCode();
    }
    /**
     * {@inheritDoc}
     * <p>
     * Due to the uniqueness of {@code Singleton}s, this method is equivalent to {@code this == other}.
     */
    @Override
    public final boolean equals(Object obj) {
        return this == obj;
    }
}
