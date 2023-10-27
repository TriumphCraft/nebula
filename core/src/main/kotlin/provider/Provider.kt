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
public interface ProviderFactory<C : Container, T : Any, CF> {
    /** The type of the instance this provider will provide. */
    public val clazz: Class<T>

    /** Provider installation. */
    public fun install(
        container: C,
        configure: CF.() -> Unit,
    ): Provider<T>
}

/** Object to allow us to have a [providers] function that is only available in the <C : Container> context. */
public object ProvidersBlockHandler {
    /** Installs a provider into a [ModularApplication]. */
    context(C)
    public fun <T : Any, C : Container, CF> install(
        factory: ProviderFactory<C, T, CF>,
        configure: CF.() -> Unit = {},
    ): Provider<T> = factory.install(this@C, configure).also { registry.put(factory.clazz, it) }
}

/** Defines a function that allows configuring providers within a given context. */
context(C)
public inline fun <C : Container> providers(block: ProvidersBlockHandler.() -> Unit) {
    block(ProvidersBlockHandler)
}
