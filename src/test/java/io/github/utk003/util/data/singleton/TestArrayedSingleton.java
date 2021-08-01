package io.github.utk003.util.data.singleton;

import org.jetbrains.annotations.NotNull;

import static io.github.utk003.util.data.singleton.TestArrayedSingleton.Factory;

public final class TestArrayedSingleton extends ArrayedSingleton<TestArrayedSingleton, Factory> {
    private TestArrayedSingleton(@NotNull String key, @NotNull Factory factory, @NotNull Object[] attributes) {
        super(key, factory, attributes);
    }

    public static final class Factory extends ArrayedSingletonFactory<TestArrayedSingleton, Factory> {
        @Override
        protected TestArrayedSingleton constructSingleton(@NotNull String key, @NotNull Object[] attributes) {
            return new TestArrayedSingleton(key, this, attributes);
        }
    }
}
