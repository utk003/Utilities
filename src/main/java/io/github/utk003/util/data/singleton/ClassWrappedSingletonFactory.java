package io.github.utk003.util.data.singleton;

import io.github.utk003.util.misc.annotations.ScheduledForRelease;
import io.github.utk003.util.misc.annotations.RequiresDocumentation;
import org.jetbrains.annotations.NotNull;

@ScheduledForRelease(inVersion = "v2.3.0")
@RequiresDocumentation
public abstract class ClassWrappedSingletonFactory<K, S extends ClassWrappedSingleton<K, S, F>, F extends ClassWrappedSingletonFactory<K, S, F>> extends SingletonFactory<S, F> {
    private final @NotNull K DEFAULT_ATTRIBUTES;
    public ClassWrappedSingletonFactory(@NotNull K defaultAttributes) {
        DEFAULT_ATTRIBUTES = defaultAttributes;
    }

    @Override
    protected @NotNull S constructSingleton(@NotNull String key) {
        return constructSingleton(key, DEFAULT_ATTRIBUTES);
    }
    protected abstract @NotNull S constructSingleton(@NotNull String key, @NotNull K attributes);

    public @NotNull S getOrDefine(@NotNull String key, @NotNull K attributes) {
        S singleton = get(key);

        if (singleton == null) {
            singleton = constructSingleton(key, attributes);
            defineSingleton(key, singleton);
        }
        return singleton;
    }
}
