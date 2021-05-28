package dev.triumphteam.core.feature.attribute

/**
 * Map like implementation for holding the needed attributes
 */
public class HashMapAttributes : Attributes {

    /**
     * Map holding the features
     */
    private val map: MutableMap<AttributeKey<*>, Any> = HashMap()

    @Suppress("UNCHECKED_CAST")
    public override fun <T : Any> getOrNull(key: AttributeKey<T>): T? = map[key] as T?

    public override operator fun contains(key: AttributeKey<*>): Boolean = map.containsKey(key)

    public override fun <T : Any> put(key: AttributeKey<T>, value: T) {
        map[key] = value
    }

    public override fun <T : Any> remove(key: AttributeKey<T>) {
        map.remove(key)
    }

    public override val allKeys: List<AttributeKey<*>>
        get() = map.keys.toList()

}

public fun attributesOf(): HashMapAttributes {
    return HashMapAttributes()
}

public fun <T> key(name: String): AttributeKey<T> {
    return AttributeKey(name)
}