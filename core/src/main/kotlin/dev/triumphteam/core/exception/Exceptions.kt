package dev.triumphteam.core.exception

import dev.triumphteam.core.feature.attribute.AttributeKey
import kotlin.reflect.KClass

public class DuplicateFeatureException(override val message: String) : IllegalStateException()

public class MissingFeatureException(key: AttributeKey<*>) : IllegalStateException() {
    override val message: String = "Application feature ${key.name} is not installed"
}

/**
 * Simple exception for trying to use an unregistered Config
 */
public class UnregisteredConfigException(clazz: KClass<*>) : RuntimeException() {
    override val message: String = "No Config found for class: `${clazz.simpleName}`."
}