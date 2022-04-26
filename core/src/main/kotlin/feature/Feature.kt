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

/**
 * A feature representation.
 * Idk there isn't much to it just yet.
 * Will have soon:tm: though.
 */
public abstract class Feature(parent: Container) : BaseContainer(parent)

/** Defines a installable feature. */
public interface FeatureFactory<F : Any> {

    /** Feature installation, works like a factory. */
    public fun install(container: Container): F
}

/** Installs a feature into a [TriumphApplication]. */
context(TriumphApplication)
public fun <F : Any> Container.install(
    feature: FeatureFactory<F>,
    configure: F.() -> Unit = {},
): F = feature.install(this).apply(configure).also { registry.put(it.javaClass, it) }

/** Installs a feature into a [TriumphApplication]. */
context(TriumphApplication)
public fun <F : Feature> Container.install(block: (Container) -> F): F =
    block(this).also { registry.put(it.javaClass, it) }