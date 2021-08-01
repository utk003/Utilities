package io.github.utk003.util.data.singleton;

import io.github.utk003.util.misc.annotations.ScheduledForRelease;
import io.github.utk003.util.misc.annotations.RequiresDocumentation;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

@ScheduledForRelease(inVersion = "v2.3.0")
@RequiresDocumentation
public abstract class ArrayedSingleton<S extends ArrayedSingleton<S, F>, F extends ArrayedSingletonFactory<S, F>> extends Singleton<S, F> {
    private final @NotNull Object[] ATTRIBUTES;
    protected ArrayedSingleton(@NotNull String key, @NotNull F factory, @NotNull Object[] attributes) {
        super(key, factory);
        ATTRIBUTES = attributes;
    }

    public int numAttributes() {
        return ATTRIBUTES.length;
    }

    public @NotNull Object getAttributeRaw(int index) {
        return ATTRIBUTES[index];
    }
    public <T> @NotNull T getAttribute(int index) {
        //noinspection unchecked
        return (T) ATTRIBUTES[index];
    }

    @Override
    public @NotNull String toString() {
        return "ArrayedSingleton(key=" + KEY + ", attributes=" + Arrays.toString(ATTRIBUTES) + ")";
    }
}
