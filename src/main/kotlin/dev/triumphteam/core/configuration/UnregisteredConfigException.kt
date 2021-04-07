package dev.triumphteam.core.configuration

import kotlin.reflect.KClass

class UnregisteredConfigException(clazz: KClass<*>) :
    RuntimeException("No Config find for class: `${clazz.simpleName}`.")