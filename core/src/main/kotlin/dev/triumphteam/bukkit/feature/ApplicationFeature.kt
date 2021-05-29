/**
 * MIT License
 *
 * Copyright (c) 2021 Mateus Moreira
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
package dev.triumphteam.bukkit.feature

import dev.triumphteam.bukkit.TriumphApplication
import dev.triumphteam.bukkit.feature.attribute.AttributeKey

/**
 * Defines a installable feature.
 * @param A The application, can be a plugin or anything.
 * @param C A configuration, can be anything even [F].
 * @param F The [ApplicationFeature] you want to return.
 */
public interface ApplicationFeature<
        in A : TriumphApplication,
        out C : Any,
        F : Any> {

    /**
     * Key that defines the specific feature.
     */
    public val key: AttributeKey<F>

    /**
     * Feature installation, works like a factory.
     * @param application The current application [A].
     * @param configure A configuration, that can be anything.
     */
    public fun install(application: A, configure: C.() -> Unit): F

}