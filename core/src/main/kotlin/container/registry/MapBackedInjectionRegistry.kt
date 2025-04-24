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
package dev.triumphteam.nebula.container.registry

import dev.triumphteam.nebula.container.Container
import dev.triumphteam.nebula.core.annotation.NebulaInternalApi
import dev.triumphteam.nebula.core.exception.DuplicateModuleException
import dev.triumphteam.nebula.provider.Provider
import dev.triumphteam.nebula.registrable.Registrable
import java.util.Objects

@NebulaInternalApi
public fun interface RegistrationFailure : (Throwable, RegistrationFailure.Stage) -> Unit {
    public enum class Stage {
        REGISTRATION, UNREGISTRATION;
    }
}

/** Map like registry implementation for holding the needed attributes. */
@NebulaInternalApi
@Suppress("UNCHECKED_CAST")
public open class MapBackedInjectionRegistry(
    override val key: String,
    /** Map holding the instances. */
    instances: MutableMap<Class<*>, Any> = mutableMapOf(),
) : InjectionRegistry, MutableMap<Class<*>, Any> by instances {

    private val failureHandler: MutableMap<Class<*>, RegistrationFailure> = mutableMapOf()

    /**
     * Retrieves an instance of the specified class from the instance cache or returns null if it doesn't exist.
     *
     * @param clazz The class of the instance to retrieve.
     * @param target An optional target container to provide context for the instance retrieval.
     * @return The instance of the specified class, or null if it does not exist or is not a Provider instance.
     */
    @NebulaInternalApi
    override fun <T : Any> get(
        clazz: Class<out T>,
        target: Container?,
    ): T? {
        val instance = this[clazz] ?: return null
        if (instance !is Provider<*>) return instance as T?
        return instance.provide(target) as T?
    }

    /**
     * Puts an instance of a class into the instances map.
     *
     * @param clazz The class of the instance to be put.
     * @param value The instance to be put.
     *
     * @throws DuplicateModuleException if a module of the same class already exists in the instances map.
     */
    @NebulaInternalApi
    override fun <T : Any> put(
        clazz: Class<out T>,
        value: T,
        onFailure: RegistrationFailure,
    ) {
        // Check for duplicates
        if (clazz in this) throw DuplicateModuleException(clazz, key)

        // Main adding.
        this[clazz] = value
        failureHandler[clazz] = onFailure // Add failure handler.
        if (value is Provider<*>) return
        val superClass = clazz.superclass
        if (superClass == null || superClass == Objects::class.java) return
        // Add as superclass if none is already present.
        putIfAbsent(superClass, value)
    }

    @NebulaInternalApi
    override fun registerAll() {
        values.filterIsInstance<Registrable>().toSet().forEach { registrable ->
            runCatching {
                registrable.register()
            }.onFailure { throwable ->
                failureHandler[registrable::class.java]?.invoke(throwable, RegistrationFailure.Stage.REGISTRATION)
                    ?: throw IllegalStateException(
                        "Could not register module ${registrable::class.java.name}.",
                        throwable,
                    )
            }
        }
    }

    @NebulaInternalApi
    override fun unregisterAll() {
        values.filterIsInstance<Registrable>().toSet().forEach { registrable ->
            runCatching {
                registrable.unregister()
            }.onFailure { throwable ->
                failureHandler[registrable::class.java]?.invoke(throwable, RegistrationFailure.Stage.UNREGISTRATION)
                    ?: throw IllegalStateException(
                        "Could not unregister module ${registrable::class.java.name}.",
                        throwable,
                    )
            }
        }
    }
}

/**
 * A global registry, objects inside will be accessible everywhere through the `inject` function.
 * Adding to this registry should only be done from a [dev.triumphteam.nebula.ModularApplication].
 */
@NebulaInternalApi
public object GlobalInjectionRegistry : MapBackedInjectionRegistry("global")
