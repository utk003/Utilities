package io.github.utk003.util.data.singleton;

import io.github.utk003.util.misc.Verifier;
import io.github.utk003.util.misc.annotations.ScheduledForRelease;
import io.github.utk003.util.misc.annotations.RequiresDocumentation;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

@ScheduledForRelease(inVersion = "v2.3.0")
@RequiresDocumentation
public abstract class MappedSingleton<S extends MappedSingleton<S, F>, F extends MappedSingletonFactory<S, F>> extends Singleton<S, F> {
    public final @NotNull Map<String, ?> ATTRIBUTES;
    protected MappedSingleton(@NotNull String key, @NotNull F factory, @NotNull Map<String, ?> attributes) {
        super(key, factory);
        ATTRIBUTES = Collections.unmodifiableMap(attributes);
    }

    public int numAttributes() {
        return ATTRIBUTES.size();
    }

    public @NotNull Set<String> getAttributeLabels() {
        return ATTRIBUTES.keySet();
    }
    public @NotNull Collection<?> getAttributes() {
        return ATTRIBUTES.values();
    }

    public @Nullable Object getNullableAttributeRaw(@NotNull String label) {
        return ATTRIBUTES.get(label);
    }
    public <T> @Nullable T getNullableAttribute(@NotNull String label) {
        //noinspection unchecked
        return (T) ATTRIBUTES.get(label);
    }

    public @NotNull Object getNotNullAttributeRaw(@NotNull String label) {
        return Verifier.requireNotNull(
                ATTRIBUTES.get(label),
                "Attribute not found for label \"" + label + "\""
        );
    }
    public <T> @NotNull T getNotNullAttribute(@NotNull String label) {
        //noinspection unchecked
        return Verifier.requireNotNull(
                (T) ATTRIBUTES.get(label),
                "Attribute not found for label \"" + label + "\""
        );
    }

    @Contract("_, true -> !null")
    public @Nullable Object getAttributeRaw(@NotNull String label, boolean requireNotNull) {
        return requireNotNull ? getNotNullAttributeRaw(label) : getNullableAttributeRaw(label);
    }
    @Contract("_, true -> !null")
    public <T> @Nullable T getAttribute(@NotNull String label, boolean requireNotNull) {
        return requireNotNull ? getNotNullAttribute(label) : getNullableAttribute(label);
    }

    @Override
    public @NotNull String toString() {
        return "MappedSingleton(key=" + KEY + ", attributes=" + ATTRIBUTES + ")";
    }
}
