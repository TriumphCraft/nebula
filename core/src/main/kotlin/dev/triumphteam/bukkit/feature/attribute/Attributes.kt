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
package dev.triumphteam.bukkit.feature.attribute

/**
 * Application attributes.
 */
public interface Attributes {

    /**
     * Gets a value of the attribute for the specified [key], or throws an exception if an attribute doesn't exist.
     */
    public operator fun <T : Any> get(key: AttributeKey<T>): T =
        getOrNull(key) ?: throw IllegalStateException("No instance for key $key")

    /**
     * Gets a value of the attribute for the specified [key], or return `null` if an attribute doesn't exist.
     * @param T Any class that is considered a feature.
     * @param key The feature [AttributeKey].
     */
    public fun <T : Any> getOrNull(key: AttributeKey<T>): T?

    /**
     * Checks if an attribute with the specified [key] exists.
     * @param key The feature [AttributeKey].
     */
    public operator fun contains(key: AttributeKey<*>): Boolean

    /**
     * Creates or changes an attribute with the specified [key] using [value].
     * @param T Any class that is considered a feature.
     * @param key The feature [AttributeKey].
     * @param value A feature to be added.
     */
    public fun <T : Any> put(key: AttributeKey<T>, value: T)

    /**
     * Removes an attribute with the specified [key].
     * @param T Any class that is considered a feature.
     * @param key The feature [AttributeKey].
     */
    public fun <T : Any> remove(key: AttributeKey<T>)

    /**
     * Removes an attribute with the specified [key] and returns its current value, throws an exception if an attribute doesn't exist.
     * @param T Any class that is considered a feature.
     * @param key The feature [AttributeKey].
     */
    public fun <T : Any> take(key: AttributeKey<T>): T = get(key).also { remove(key) }

    /**
     * Removes an attribute with the specified [key] and returns its current value, returns `null` if an attribute doesn't exist.
     * @param T Any class that is considered a feature.
     * @param key The feature [AttributeKey]
     */
    public fun <T : Any> takeOrNull(key: AttributeKey<T>): T? = getOrNull(key).also { remove(key) }

    /**
     * Returns [List] of all [AttributeKey] instances in this map.
     */
    public val allKeys: List<AttributeKey<*>>

}