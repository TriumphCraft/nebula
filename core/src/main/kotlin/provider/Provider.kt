/**
 * MIT License
 *
 * Copyright (c) 2021-2023 TriumphTeam
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package dev.triumphteam.nebula.provider

import dev.triumphteam.nebula.ModularApplication
import dev.triumphteam.nebula.container.Container
import dev.triumphteam.nebula.core.annotation.NebulaInternalApi
import dev.triumphteam.nebula.module.ModuleFactory

/** A provider, allows for custom injection based on the container it's injecting from. */
public interface Provider<T : Any> {

    /** Provides an object based on the given [container]. */
    public fun provide(container: Container?): T
}

/** Similar to a normal [ModuleFactory] but requires providing the type of the providing instance. */
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
public object Providers {

    /** Installs a provider into a [ModularApplication]. */
    // context(C)
    @OptIn(NebulaInternalApi::class)
    public fun <T : Any, C : Container, CF> C.install(
        factory: ProviderFactory<C, T, CF>,
        configure: CF.() -> Unit = {},
    ): Provider<T> = factory.install(this@C, configure).also { provider ->
        registry.put(factory.clazz, provider) { throwable, _ ->
            handleCriticalFailure("Failed to install provider for class '${factory.clazz.simpleName}'.", throwable)
        }
    }
}

/** Defines a function that allows configuring providers within a given context. */
// context(C)
public inline fun <C : Container> C.providers(block: Providers.() -> Unit): Unit = block(Providers)
