package dev.triumphteam.bukkit.configuration

import me.mattstudios.config.SettingsHolder
import me.mattstudios.config.SettingsManager
import me.mattstudios.config.beanmapper.PropertyMapper
import me.mattstudios.config.properties.Property
import java.nio.file.Path

/**
 * Simple config.
 * All configs registered by the main plugin must extend this.
 */
public abstract class BaseConfig(
    path: Path,
    holder: Class<out SettingsHolder>,
    propertyMapper: PropertyMapper? = null,
) {

    private val config: SettingsManager

    init {
        val configBuilder = SettingsManager.from(path)
        if (propertyMapper != null) configBuilder.propertyMapper(propertyMapper)
        config = configBuilder.configurationData(holder).create()
    }

    /**
     * Gets a property from the config.
     */
    public operator fun <T> get(property: Property<T>): T = config[property]


    /**
     * Sets a property value to the config.
     * @param property The property key.
     * @param value The new value.
     */
    public operator fun <T : Any> set(property: Property<T>, value: T) {
        config[property] = value
    }

    /**
     * Reloads the config.
     */
    public fun reload() {
        config.reload()
    }

    /**
     * Saves the config.
     */
    public fun save() {
        config.save()
    }

}