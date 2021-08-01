package io.github.utk003.util.data.singleton;

import io.github.utk003.util.misc.annotations.ScheduledForRelease;
import io.github.utk003.util.misc.annotations.RequiresDocumentation;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Map;

@ScheduledForRelease(inVersion = "v2.3.0")
@RequiresDocumentation
public abstract class MappedSingletonFactory<S extends MappedSingleton<S, F>, F extends MappedSingletonFactory<S, F>> extends SingletonFactory<S, F> {
    @Override
    protected @NotNull S constructSingleton(@NotNull String key) {
        return constructSingleton(key, Collections.emptyMap());
    }
    protected abstract @NotNull S constructSingleton(@NotNull String key, @NotNull Map<String, ?> attributes);

    public @NotNull S getOrDefine(@NotNull String key, @NotNull Map<String, ?> attributes) {
        S singleton = get(key);
        if (singleton == null) {
            singleton = constructSingleton(key, attributes);
            defineSingleton(key, singleton);
        }
        return singleton;
    }
}
