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
 * Map like implementation for holding the needed attributes.
 * TODO Create concurrent map implementation.
 */
public class HashMapAttributes : Attributes {

    /**
     * Map holding the features.
     */
    private val map: MutableMap<AttributeKey<*>, Any> = HashMap()

    /**
     * Gets a value of the attribute for the specified [key], or return `null` if an attribute doesn't exist.
     * @param T Any class that is considered a feature.
     * @param key The feature [AttributeKey].
     */
    @Suppress("UNCHECKED_CAST")
    public override fun <T : Any> getOrNull(key: AttributeKey<T>): T? = map[key] as T?

    /**
     * Checks if an attribute with the specified [key] exists.
     * @param key The feature [AttributeKey].
     */
    public override operator fun contains(key: AttributeKey<*>): Boolean = map.containsKey(key)

    /**
     * Creates or changes an attribute with the specified [key] using [value].
     * @param T Any class that is considered a feature.
     * @param key The feature [AttributeKey].
     * @param value A feature to be added.
     */
    public override fun <T : Any> put(key: AttributeKey<T>, value: T) {
        map[key] = value
    }

    /**
     * Removes an attribute with the specified [key].
     * @param T Any class that is considered a feature.
     * @param key The feature [AttributeKey].
     */
    public override fun <T : Any> remove(key: AttributeKey<T>) {
        map.remove(key)
    }

    /**
     * Returns [List] of all [AttributeKey] instances in this map.
     */
    public override val allKeys: List<AttributeKey<*>>
        get() = map.keys.toList()

}

/**
 * Simple function for creating a new [Attributes].
 */
public fun attributesOf(): HashMapAttributes {
    return HashMapAttributes()
}