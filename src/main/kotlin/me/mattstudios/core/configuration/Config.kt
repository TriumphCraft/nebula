package me.mattstudios.core.configuration

import me.mattstudios.config.SettingsHolder
import me.mattstudios.config.SettingsManager
import me.mattstudios.config.beanmapper.PropertyMapper
import me.mattstudios.config.properties.Property
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.nio.file.Paths

/**
 * Config handler for all plugins
 * *Extends [YamlConfiguration] just to override JavaPlugin's `getConfig`*
 * **Never uses anything from [YamlConfiguration]**
 */
@Suppress("MemberVisibilityCanBePrivate")
class Config(private val folder: File) : YamlConfiguration() {

    // The settings holder of the config
    private lateinit var settingsManager: SettingsManager

    /**
     * Loads the config file selected
     */
    fun create(configClass: Class<out SettingsHolder>) = create(configClass, null)

    /**
     * Loads the config file selected
     */
    fun create(configClass: Class<out SettingsHolder>, mapper: PropertyMapper?) {
        val builder = SettingsManager.from(Paths.get(folder.path, "config.yml")).configurationData(configClass)
        if (mapper != null) builder.propertyMapper(mapper)
        settingsManager = builder.create()
    }

    /**
     * Gets a property
     */
    operator fun <T> get(property: Property<T>) = settingsManager[property]

    /**
     * Gets a property
     */
    operator fun <T : Any> set(property: Property<T>, value: T) {
        settingsManager[property] = value
    }

    /**
     * Reloads the config
     */
    fun reload() {
        settingsManager.reload()
    }

    /**
     * Saves the config
     */
    fun save() {
        settingsManager.save()
    }

}