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