package io.github.utk003.util.data.singleton;

import io.github.utk003.util.misc.annotations.ScheduledForRelease;
import io.github.utk003.util.misc.annotations.RequiresDocumentation;
import org.jetbrains.annotations.NotNull;

@ScheduledForRelease(inVersion = "v2.3.0")
@RequiresDocumentation
public abstract class ArrayedSingletonFactory<S extends ArrayedSingleton<S, F>, F extends ArrayedSingletonFactory<S, F>> extends SingletonFactory<S, F> {
    private static final @NotNull Object[] EMPTY_ARRAY = new Object[0];
    @Override
    protected @NotNull S constructSingleton(@NotNull String key) {
        return constructSingleton(key, EMPTY_ARRAY);
    }
    protected abstract @NotNull S constructSingleton(@NotNull String key, @NotNull Object[] attributes);

    public @NotNull S getOrDefine(@NotNull String key, @NotNull Object... attributes) {
        S singleton = get(key);
        if (singleton == null) {
            singleton = constructSingleton(key, attributes);
            defineSingleton(key, singleton);
        }
        return singleton;
    }
}
