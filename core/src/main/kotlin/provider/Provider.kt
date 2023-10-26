package dev.triumphteam.nebula.provider

import dev.triumphteam.nebula.ModularApplication
import dev.triumphteam.nebula.container.Container
import dev.triumphteam.nebula.module.ModuleFactory

/** A provider, allows for custom injection based on the container it's injecting from. */
public interface Provider<T : Any> {
    /** Provides an object based on the given [container]. */
    public fun provide(container: Container?): T
}

/** Similar to a normal [ModuleFactory] but requires to provide the type of the providing instance. */
public interface ProviderFactory<T : Any, CF> {
    /** The type of the instance this provider will provide. */
    public val clazz: Class<T>

    /** Provider installation. */
    public fun <C : Container> install(
        container: C,
        configure: CF.() -> Unit,
    ): Provider<T>
}

/** Installs a provider into a [ModularApplication]. */
public fun <T : Any, C : Container, CF> C.install(
    factory: ProviderFactory<T, CF>,
    configure: CF.() -> Unit,
): Provider<T> = factory.install(this, configure).also { registry.put(factory.clazz, it) }
