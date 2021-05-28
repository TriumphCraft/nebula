package dev.triumphteam.bukkit.feature.attribute

/**
 * Class for holding the attributes keys.
 * @param T Is type of the value stored in the attribute.
 * @param name The key name for the specific feature.
 */
public data class AttributeKey<T>(public val name: String) {
    override fun toString(): String = if (name.isEmpty()) super.toString() else "AttributeKey: $name"
}

/**
 * Function to make it easier to create an [AttributeKey].
 */
public fun <T> key(name: String): AttributeKey<T> {
    return AttributeKey(name)
}