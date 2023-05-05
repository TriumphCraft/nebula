package com.triumphcraft.nebula.provider

import com.triumphcraft.nebula.container.Container
import com.triumphcraft.nebula.module.ModuleFactory

/** A provider, allows for custom injection based on the container it's injecting from. */
public interface Provider<T : Any> {

    /** Provides an object based on the given [container]. */
    public fun provide(container: Container?): T?
}

/** Similar to a normal [ModuleFactory] but requires to provide the type of the providing instance. */
public interface ProviderFactory<T : Any> : ModuleFactory<Provider<T>> {

    /** The type of the instance this provider will provide. */
    public val clazz: Class<T>
}

/** Installs a provider into a [ModularApplication]. */
// context(TriumphApplication)
public fun <T : Any> Container.install(factory: ProviderFactory<T>): Provider<T> =
    factory.install(this).also { registry.put(factory.clazz, it) }
