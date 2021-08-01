package io.github.utk003.util.data.singleton;

import org.jetbrains.annotations.NotNull;

import static io.github.utk003.util.data.singleton.TestSingleton.Factory;

public final class TestSingleton extends Singleton<TestSingleton, Factory> {
    private TestSingleton(@NotNull String key, @NotNull Factory factory) {
        super(key, factory);
    }

    public static final class Factory extends SingletonFactory<TestSingleton, Factory> {
        @Override
        protected TestSingleton constructSingleton(@NotNull String key) {
            return new TestSingleton(key, this);
        }
    }
}
