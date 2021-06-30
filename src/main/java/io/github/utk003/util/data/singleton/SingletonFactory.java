package io.github.utk003.util.data.singleton;

import io.github.utk003.util.func.Lambda2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public abstract class SingletonFactory<S extends Singleton> {
    public abstract @NotNull S[] currentValues();
    public abstract @NotNull Set<S> values();

    public abstract @Nullable S get(@NotNull String key);
    public abstract @NotNull S add(@NotNull String key, @NotNull Object... args);
    public abstract @Nullable S remove(@NotNull String key);

    public abstract void changeSingletonMaker(@NotNull Lambda2<S, String, Object[]> newSingletonMaker);

    public static <S extends Singleton> @NotNull SingletonFactory<S> createNewFactory(@NotNull Lambda2<S, String, Object[]> singletonMaker) {
        return new DefaultSingletonFactory<>(singletonMaker);
    }
}
