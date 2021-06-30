package io.github.utk003.util.data.singleton;

import org.jetbrains.annotations.NotNull;

public final class NoArgsSingleton extends Singleton {
    public NoArgsSingleton(@NotNull String key) {
        super(key);
    }
    public NoArgsSingleton(@NotNull String key, @NotNull Object[] args) {
        super(key);
    }

    @Override
    public @NotNull String toString() {
        return "Singleton(" + KEY + ")";
    }
}
