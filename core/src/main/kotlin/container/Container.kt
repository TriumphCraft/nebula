/**
 * MIT License
 *
 * Copyright (c) 2021-2022 TriumphTeam
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
package dev.triumphteam.nebula.container

import dev.triumphteam.nebula.exception.MissingFeatureException
import dev.triumphteam.nebula.feature.registry.GlobalInjectionRegistry
import dev.triumphteam.nebula.feature.registry.InjectionRegistry
import dev.triumphteam.nebula.feature.registry.SimpleInjectionRegistry
import dev.triumphteam.nebula.key.Keyed

/** Represents a keyed container, which holders a parent and an injection registry. */
public interface Container : Keyed {

    /**
     * The container's [InjectionRegistry], which holds the injecting required for this container.
     * Or for its children.
     */
    public val registry: InjectionRegistry

    /** The container this container is bound to. */
    public val parent: Container?

    /** Gets a specific object from this container or from its parent. */
    public fun <T : Any> get(klass: Class<T>): T =
        registry.get(klass) ?: parent?.get(klass) ?: throw MissingFeatureException(klass)
}

/** Simple abstract implementation of a container, simply initializes the [registry]. */
public abstract class BaseContainer(override val parent: Container) : Container {

    /** Simple injection registry of this container. */
    public override val registry: InjectionRegistry = SimpleInjectionRegistry()

    /** Defaults to the class name to be easier. */
    override val key: String = javaClass.simpleName
}

/**
 * Injection function that allows for lazy injection of objects, features, or containers into another container.
 * Based on if it has or not access to the object.
 * If an object is not present in the current container, it'll search for it on its parents.
 */
public inline fun <reified T : Any> Container.inject(
    mode: LazyThreadSafetyMode = LazyThreadSafetyMode.NONE,
): Lazy<T> = lazy(mode) { get(T::class.java) }

/**
 * Injection function that allows for lazy injection of objects, features, or containers.
 * This injection can only be done with objects from the global registry.
 * Container specific objects will not be present.
 */
public inline fun <reified T : Any> inject(
    mode: LazyThreadSafetyMode = LazyThreadSafetyMode.NONE,
): Lazy<T> = lazy(mode) { GlobalInjectionRegistry.get(T::class.java) ?: throw MissingFeatureException(T::class.java) }
