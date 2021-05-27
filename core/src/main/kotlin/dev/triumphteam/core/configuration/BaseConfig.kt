package dev.triumphteam.core.configuration

import me.mattstudios.config.SettingsHolder
import me.mattstudios.config.SettingsManager
import me.mattstudios.config.beanmapper.PropertyMapper
import me.mattstudios.config.properties.Property
import java.nio.file.Path

/**
 * Simple config
 * All configs registered by the main plugin must extend this
 */
/*abstract class BaseConfig(
    path: Path,
    holder: Class<out SettingsHolder>,
    propertyMapper: PropertyMapper? = null,
) {

    protected val config: SettingsManager

    init {
        val configBuilder = SettingsManager.from(path)
        if (propertyMapper != null) configBuilder.propertyMapper(propertyMapper)
        config = configBuilder.configurationData(holder).create()
    }

    /**
     * Gets a property from the config
     */
    operator fun <T> get(property: Property<T>): T = config[property]


    /**
     * Sets a property value to the config, requires [save] for the setting to be affective in the file
     */
    operator fun <T : Any> set(property: Property<T>, value: T) {
        config[property] = value
    }

    /**
     * Reloads the config
     */
    fun reload() = config.reload()

    /**
     * Saves the config
     */
    fun save() = config.save()

}*/