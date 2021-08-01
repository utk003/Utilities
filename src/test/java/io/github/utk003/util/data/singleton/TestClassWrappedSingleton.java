package io.github.utk003.util.data.singleton;

import org.jetbrains.annotations.NotNull;

import static io.github.utk003.util.data.singleton.TestClassWrappedSingleton.Attributes;
import static io.github.utk003.util.data.singleton.TestClassWrappedSingleton.Factory;

public final class TestClassWrappedSingleton extends ClassWrappedSingleton<Attributes, TestClassWrappedSingleton, Factory> {
    private TestClassWrappedSingleton(@NotNull String key, @NotNull Factory factory, @NotNull Attributes attributes) {
        super(key, factory, attributes);
    }

    public static final class Attributes {
        private static final @NotNull Attributes DEFAULT = new Attributes();
    }

    public static final class Factory extends ClassWrappedSingletonFactory<Attributes, TestClassWrappedSingleton, Factory> {
        public Factory() {
            super(Attributes.DEFAULT);
        }

        @Override
        protected TestClassWrappedSingleton constructSingleton(@NotNull String key, @NotNull Attributes attributes) {
            return new TestClassWrappedSingleton(key, this, attributes);
        }
    }
}
