package dev.triumphteam.core.configuration

import kotlin.reflect.KClass

/**
 * Simple exception for trying to use an unregistered Config
 */
class UnregisteredConfigException(clazz: KClass<*>) :
    RuntimeException("No Config find for class: `${clazz.simpleName}`.")