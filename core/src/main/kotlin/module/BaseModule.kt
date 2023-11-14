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

import dev.triumphteam.nebula.Nebula
import dev.triumphteam.nebula.container.BaseContainer
import dev.triumphteam.nebula.container.Container
import dev.triumphteam.nebula.container.registry.GlobalInjectionRegistry
import dev.triumphteam.nebula.core.annotation.NebulaInternalApi
import dev.triumphteam.nebula.registrable.Registrable
import dev.triumphteam.nebula.registrable.registerAll
import dev.triumphteam.nebula.registrable.unregisterAll
import java.util.concurrent.atomic.AtomicBoolean

private typealias RegisterAction = () -> Unit

/**
 * A module to be extended.
 * It contains registering of things.
 * Allows you to run things when modules are registered and unregistered.
 */
@OptIn(NebulaInternalApi::class)
public abstract class BaseModule(parent: Container? = null) : BaseContainer(parent), Registrable {

    private val registering: MutableList<RegisterAction> = mutableListOf()
    private val unregistering: MutableList<RegisterAction> = mutableListOf()

    private val _isRegistered: AtomicBoolean = AtomicBoolean(false)

    override val isRegistered: Boolean
        get() = _isRegistered.get()

    /** Adds actions to be run when the module is registering. */
    protected fun onRegister(block: RegisterAction) {
        registering.add(block)
    }

    /** Adds actions to be run when the module is unregistering. */
    protected fun onUnregister(block: RegisterAction) {
        unregistering.add(block)
    }

    /** Registers the current module and its children. */
    public override fun register() {
        if (isRegistered) return
        _isRegistered.set(true)
        registering.forEach(RegisterAction::invoke)
        // prevent re-registering global registrable.
        if (registry == GlobalInjectionRegistry) return
        registry.registerAll()
    }

    /** Unregisters the current module and its children. */
    public override fun unregister() {
        if (!isRegistered) return
        _isRegistered.set(false)
        unregistering.forEach(RegisterAction::invoke)
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

    /** Module installation, works like a factory. */
    public fun install(container: C): M
}

/** Object to allow us to have a [modules] function that is only available in the <C : Container> context. */
public object Modules {

    /** Installs a module into a [Nebula]. */
    context(C)
    @OptIn(NebulaInternalApi::class)
    public fun <T : Any, C : Container> install(
        module: ModuleFactory<T, C>,
        configure: T.() -> Unit = {},
    ): T = module.install(this@C).apply(configure).also {
        registry.put(module.returnType ?: it.javaClass, it)
    }

    /** Installs a module into a [Nebula]. */
    context(C)
    @OptIn(NebulaInternalApi::class)
    public fun <T : BaseModule, C : Container> install(block: (C) -> T): T =
        block(this@C).also { registry.put(it.javaClass, it) }
}

/** Defines a function that allows configuring providers within a given context. */
context(C)
public inline fun <C : Container> modules(block: Modules.() -> Unit): Unit = block(Modules)
