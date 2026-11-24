package com.daqem.tinymobfarm.platform;

import java.util.function.Supplier;

public final class RegistryEntry<T> implements Supplier<T> {
    private final Supplier<T> factory;
    private T value;

    RegistryEntry(Supplier<T> factory) {
        this.factory = factory;
    }

    @Override
    public T get() {
        if (value == null) value = factory.get();
        return value;
    }
}
