package dev.triumphteam.core.feature.attribute

public data class AttributeKey<T>(public val name: String) {
    override fun toString(): String = if (name.isEmpty()) super.toString() else "AttributeKey: $name"
}