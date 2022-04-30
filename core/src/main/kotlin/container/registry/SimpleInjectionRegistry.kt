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
package dev.triumphteam.nebula.container.registry

import dev.triumphteam.nebula.container.Container
import dev.triumphteam.nebula.provider.Provider
import java.util.concurrent.ConcurrentHashMap

/** Map like registry implementation for holding the needed attributes. */
@Suppress("UNCHECKED_CAST")
public open class SimpleInjectionRegistry(default: InjectionRegistry? = null) : InjectionRegistry {

    /** Map holding the instances. */
    override val instances: MutableMap<Class<*>, Any> =
        if (default == null) ConcurrentHashMap() else ConcurrentHashMap(default.instances)

    /**
     * Gets a value of the attribute for the specified [klass].
     * Or return `null` if an object doesn't exist.
     */
    override fun <T : Any> get(klass: Class<out T>, target: Container?): T? {
        val instance = instances[klass] ?: return null
        if (instance !is Provider<*>) return instances[klass] as T?
        return instance.provide(target) as T?
    }

    /** Creates or changes an attribute with the specified [klass] using [value]. */
    override fun <T : Any> put(klass: Class<out T>, value: T) {
        instances[klass] = value
    }
}

/**
 * A global registry, objects inside will be accessible everywhere through the `inject` function.
 * Adding to this registry should only be done from a [dev.triumphteam.nebula.ModularApplication].
 */
public object GlobalInjectionRegistry : SimpleInjectionRegistry()
