package io.github.utk003.util.data.singleton;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

import static io.github.utk003.util.data.singleton.TestMappedSingleton.Factory;

public final class TestMappedSingleton extends MappedSingleton<TestMappedSingleton, Factory> {
    private TestMappedSingleton(@NotNull String key, @NotNull Factory factory, @NotNull Map<String, ?> attributes) {
        super(key, factory, attributes);
    }

    public static final class Factory extends MappedSingletonFactory<TestMappedSingleton, Factory> {
        @Override
        protected TestMappedSingleton constructSingleton(@NotNull String key, @NotNull Map<String, ?> attributes) {
            return new TestMappedSingleton(key, this, attributes);
        }
    }
}
