package dev.triumphteam.core.exception

import dev.triumphteam.core.feature.attribute.AttributeKey

public class DuplicateFeatureException(override val message: String) : IllegalStateException()

public class MissingFeatureException(private val key: AttributeKey<*>) : IllegalStateException() {
    
    override val message: String
        get() = "Application feature ${key.name} is not installed"
}