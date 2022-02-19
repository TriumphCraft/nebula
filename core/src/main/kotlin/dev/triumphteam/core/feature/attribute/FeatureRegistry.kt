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
package dev.triumphteam.core.feature.attribute

import dev.triumphteam.core.TriumphApplication

/**
 * Map like implementation for holding the needed attributes.
 * TODO Create concurrent map implementation.
 */
public object FeatureRegistry {

    /**
     * Map holding the features.
     */
    private val map: MutableMap<Class<*>, Any> = HashMap()

    /**
     * Gets a value of the attribute for the specified [klass], or return `null` if an attribute doesn't exist.
     */
    @Suppress("UNCHECKED_CAST")
    public fun <T : Any> getOrNull(klass: Class<out T>): T? = map[klass] as T?

    /**
     * Checks if an attribute with the specified [klass] exists.
     */
    public operator fun contains(klass: Class<*>): Boolean = map.containsKey(klass)

    /**
     * Creates or changes an attribute with the specified [klass] using [value].
     */
    public fun <T : Any> TriumphApplication.put(klass: Class<out T>, value: T) {
        map[klass] = value
    }

    /**
     * Removes an attribute with the specified [klass].
     */
    public fun <T : Any> TriumphApplication.remove(klass: Class<out T>) {
        map.remove(klass)
    }

    /**
     * Clears all the features.
     */
    public fun TriumphApplication.clear() {
        map.clear()
    }
}
