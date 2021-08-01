package io.github.utk003.util.data.singleton;

import io.github.utk003.util.misc.annotations.ScheduledForRelease;
import io.github.utk003.util.misc.annotations.RequiresDocumentation;
import org.jetbrains.annotations.NotNull;

@ScheduledForRelease(inVersion = "v2.3.0")
@RequiresDocumentation
public abstract class ClassWrappedSingleton<K, S extends ClassWrappedSingleton<K, S, F>, F extends ClassWrappedSingletonFactory<K, S, F>> extends Singleton<S, F>  {
    public final @NotNull K ATTRIBUTES;
    protected ClassWrappedSingleton(@NotNull String key, @NotNull F factory, @NotNull K attributes) {
        super(key, factory);
        ATTRIBUTES = attributes;
    }

    @Override
    public @NotNull String toString() {
        return "ClassWrappedSingleton(key=" + KEY + ", attributes=" + ATTRIBUTES + ")";
    }
}
