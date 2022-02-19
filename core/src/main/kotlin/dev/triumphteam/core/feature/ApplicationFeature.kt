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
package dev.triumphteam.core.feature

import dev.triumphteam.core.TriumphApplication
import dev.triumphteam.core.exception.MissingFeatureException
import dev.triumphteam.core.feature.attribute.FeatureRegistry.getOrNull
import dev.triumphteam.core.feature.attribute.FeatureRegistry.put

/**
 * Defines a installable feature.
 */
public interface ApplicationFeature<in A : TriumphApplication, out C : Any, F : Any> {

    /**
     * Feature installation, works like a factory.
     */
    public fun install(application: A, configure: C.() -> Unit): F
}

/**
 * Installs a feature into a [TriumphApplication].
 */
public fun <A : TriumphApplication, C : Any, F : Any> A.install(
    feature: ApplicationFeature<A, C, F>,
    configure: C.() -> Unit = {},
): F {
    val installed = feature.install(this, configure)
    put(feature.javaClass, installed)
    return installed
}

/**
 * Retrieves a specific feature.
 */
public inline fun <reified F : Any> feature(): Lazy<F> = lazy {
    getOrNull(F::class.java) ?: throw MissingFeatureException(F::class.java)
}

/**
 * Retrieves a specific feature or null if it doesn't exist.
 */
public inline fun <reified F : Any> featureOrNull(feature: ApplicationFeature<*, *, F>): Lazy<F?> = lazy {
    getOrNull(F::class.java)
}