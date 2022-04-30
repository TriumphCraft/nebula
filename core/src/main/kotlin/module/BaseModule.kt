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
package dev.triumphteam.nebula.module

import dev.triumphteam.nebula.ModularApplication
import dev.triumphteam.nebula.container.BaseContainer
import dev.triumphteam.nebula.container.Container
import dev.triumphteam.nebula.registerable.Registerable

private typealias RegisterAction = () -> Unit

/**
 * A module to be extended.
 * It contains registering of things.
 * Allows you to run things when modules are registered and unregistered.
 */
public abstract class BaseModule(parent: Container) : BaseContainer(parent), Registerable {

    private val registering: MutableList<RegisterAction> = mutableListOf()
    private val unregistering: MutableList<RegisterAction> = mutableListOf()

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
        registering.forEach(RegisterAction::invoke)
        registry.instances.values.filterIsInstance<Registerable>().forEach(Registerable::register)
    }

    /** Unregisters the current module and its children. */
    public override fun unregister() {
        unregistering.forEach(RegisterAction::invoke)
        registry.instances.values.filterIsInstance<Registerable>().forEach(Registerable::unregister)
    }
}

/** Defines a installable module. */
public interface ModuleFactory<F : Any> {

    /** Module installation, works like a factory. */
    public fun install(container: Container): F
}

/** Installs a module into a [ModularApplication]. */
// context(TriumphApplication)
public fun <F : Any> Container.install(
    module: ModuleFactory<F>,
    configure: F.() -> Unit = {},
): F = module.install(this).apply(configure).also { registry.put(it.javaClass, it) }

/** Installs a module into a [ModularApplication]. */
// context(TriumphApplication)
public fun <F : BaseModule> Container.install(block: (Container) -> F): F =
    block(this).also { registry.put(it.javaClass, it) }
