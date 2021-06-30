package io.github.utk003.util.data.singleton;

import org.jetbrains.annotations.NotNull;

public abstract class Singleton {
    public final @NotNull String KEY;
    public Singleton(@NotNull String key) {
        KEY = key;
    }

    @Override
    public abstract @NotNull String toString();

    @Override
    public int hashCode() {
        return KEY.hashCode();
    }
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Singleton && KEY.equals(((Singleton) obj).KEY);
    }
}
