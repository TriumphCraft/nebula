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
package dev.triumphteam.nebula.feature

import dev.triumphteam.nebula.TriumphApplication
import dev.triumphteam.nebula.container.BaseContainer
import dev.triumphteam.nebula.container.Container
import dev.triumphteam.nebula.registerable.Registerable

private typealias RegisterAction = () -> Unit

/**
 * A feature representation.
 * It contains registering of things.
 * Allows you to run things when features are registered and unregistered.
 */
public abstract class BaseFeature(parent: Container) : BaseContainer(parent), Registerable {

    private val registering: MutableList<RegisterAction> = mutableListOf()
    private val unregistering: MutableList<RegisterAction> = mutableListOf()

    /** Adds actions to be run when the feature is registering. */
    protected fun onRegister(block: RegisterAction) {
        registering.add(block)
    }

    /** Adds actions to be run when the feature is unregistering. */
    protected fun onUnregister(block: RegisterAction) {
        unregistering.add(block)
    }

    /** Registers the current feature and its children. */
    public override fun register() {
        registering.forEach(RegisterAction::invoke)
        registry.instances.values.filterIsInstance<Registerable>().forEach(Registerable::register)
    }

    /** Unregisters the current feature and its children. */
    public override fun unregister() {
        unregistering.forEach(RegisterAction::invoke)
        registry.instances.values.filterIsInstance<Registerable>().forEach(Registerable::unregister)
    }
}

/** Defines a installable feature. */
public interface FeatureFactory<F : Any> {

    /** Feature installation, works like a factory. */
    public fun install(container: Container): F
}

/** Installs a feature into a [TriumphApplication]. */
// context(TriumphApplication)
public fun <F : Any> Container.install(
    feature: FeatureFactory<F>,
    configure: F.() -> Unit = {},
): F = feature.install(this).apply(configure).also { registry.put(it.javaClass, it) }

/** Installs a feature into a [TriumphApplication]. */
// context(TriumphApplication)
public fun <F : BaseFeature> Container.install(block: (Container) -> F): F =
    block(this).also { registry.put(it.javaClass, it) }
