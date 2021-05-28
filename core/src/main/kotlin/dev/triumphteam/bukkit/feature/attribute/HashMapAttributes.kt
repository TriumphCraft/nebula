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