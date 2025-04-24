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
package dev.triumphteam.nebula.module

import dev.triumphteam.nebula.ModularApplication
import dev.triumphteam.nebula.container.BaseContainer
import dev.triumphteam.nebula.container.Container
import dev.triumphteam.nebula.container.registry.GlobalInjectionRegistry
import dev.triumphteam.nebula.container.registry.RegistrationFailure
import dev.triumphteam.nebula.core.annotation.NebulaInternalApi
import dev.triumphteam.nebula.registrable.RegisterScope
import dev.triumphteam.nebula.registrable.Registrable
import java.util.concurrent.atomic.AtomicBoolean

public typealias RegisterAction<S> = S.() -> Unit
public typealias UnRegisterAction = () -> Unit

/**
 * A module to be extended.
 * It contains registering of things.
 * Allows you to run things when modules are registered and unregistered.
 */
@OptIn(NebulaInternalApi::class)
public abstract class BaseModule<S : RegisterScope>(parent: Container? = null) : BaseContainer(parent), Registrable {

    private val registering: MutableList<RegisterAction<S>> = mutableListOf()
    private val unregistering: MutableList<UnRegisterAction> = mutableListOf()

    private val _isRegistered: AtomicBoolean = AtomicBoolean(false)

    override val isRegistered: Boolean
        get() = _isRegistered.get()

    /** The register scope of this module. */
    protected abstract val registerScope: S

    /** Adds actions to be run when the module is registering. */
    protected fun onRegister(block: RegisterAction<S>) {
        registering.add(block)
    }

    /** Adds actions to be run when the module is unregistering. */
    protected fun onUnregister(block: UnRegisterAction) {
        unregistering.add(block)
    }

    @NebulaInternalApi
    public fun addRegistration(action: RegisterAction<S>) {
        onRegister(action)
    }

    @NebulaInternalApi
    public fun addUnregistration(action: UnRegisterAction) {
        onUnregister(action)
    }

    /** Registers the current module and its children. */
    public override fun register() {
        if (isRegistered) return
        _isRegistered.set(true)
        registering.forEach { action -> action(registerScope) }
        // prevent re-registering global registrable.
        if (registry == GlobalInjectionRegistry) return
        registry.registerAll()
    }

    /** Unregisters the current module and its children. */
    public override fun unregister() {
        if (!isRegistered) return
        _isRegistered.set(false)
        unregistering.forEach(UnRegisterAction::invoke)
        // prevent re-unregistering global registrable.
        if (registry == GlobalInjectionRegistry) return
        registry.unregisterAll()
    }
}

/** Defines a installable module. */
public interface ModuleFactory<M : Any, C : Container> {

    /** Overrides the return type. */
    public val returnType: Class<*>?
        get() = null

    /** Module installation works like a factory. */
    public fun install(container: C): M

    public abstract class Simple<M : Any, C : Container>(private val provider: () -> M) : ModuleFactory<M, C> {
        override fun install(container: C): M {
            return provider()
        }
    }
}

/** Object to allow us to have a [modules] function that is only available in the <C : Container> context. */
@OptIn(NebulaInternalApi::class)
public data class Modules(@PublishedApi internal val description: String) {

    /** Installs a module into a [ModularApplication]. */
    public fun <T : Any, C : Container> C.install(
        module: ModuleFactory<T, C>,
        configure: T.() -> Unit = {},
    ): T = module.install(this@C).apply(configure).also {
        put(module.returnType ?: it.javaClass, it)
    }

    /** Installs a module into a [ModularApplication]. */
    public fun <T : BaseModule<*>, C : Container> C.install(block: (C) -> T): T =
        block(this@C).also { put(it.javaClass, it) }

    /** Installs a module into a [ModularApplication]. */
    public inline fun <reified T : Any> Container.installDirect(instance: T): Unit =
        put(T::class.java, instance)

    @PublishedApi
    internal fun <T : Any> Container.put(clazz: Class<out T>, instance: T) {
        registry.put(clazz, instance) { throwable, stage ->
            when (stage) {
                RegistrationFailure.Stage.REGISTRATION -> {
                    handleCriticalFailure("An error occurred while registering modules: $description", throwable)
                }

                RegistrationFailure.Stage.UNREGISTRATION -> {
                    handleCriticalFailure("An error occurred while unregistering modules: $description", throwable)
                }
            }
        }
    }
}

/** Defines a function that allows configuring providers within a given context. */
public inline fun <C : Container> C.modules(
    description: String = "Registering generic modules.",
    block: Modules.() -> Unit,
): Unit = block(Modules(description))
