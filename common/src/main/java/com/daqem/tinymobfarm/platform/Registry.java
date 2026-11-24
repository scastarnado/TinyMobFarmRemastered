package com.daqem.tinymobfarm.platform;

import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/** Queues shared definitions until the loader opens the appropriate registry. */
public final class Registry<T> {
    private static final List<Registry<?>> REGISTRIES = new ArrayList<>();
    private final net.minecraft.core.Registry<T> registry;
    private final String namespace;
    private final List<Runnable> registrations = new ArrayList<>();

    public Registry(net.minecraft.core.Registry<T> registry, String namespace) {
        this.registry = registry;
        this.namespace = namespace;
        REGISTRIES.add(this);
    }

    public <V extends T> RegistryEntry<V> register(String name, Supplier<V> factory) {
        return register(name, key -> factory.get());
    }

    public <V extends T> RegistryEntry<V> register(String name, Function<ResourceKey<T>, V> factory) {
        ResourceKey<T> key = ResourceKey.create(registry.key(), Identifier.fromNamespaceAndPath(namespace, name));
        RegistryEntry<V> entry = new RegistryEntry<>(() -> factory.apply(key));
        registrations.add(() -> net.minecraft.core.Registry.register(registry, key, entry.get()));
        return entry;
    }

    public void register() {
        registrations.forEach(Runnable::run);
        registrations.clear();
    }

    public static void registerMatching(net.minecraft.core.Registry<?> target) {
        REGISTRIES.stream().filter(registry -> registry.registry == target).forEach(Registry::register);
    }
}
