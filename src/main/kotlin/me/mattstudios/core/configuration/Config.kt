package me.mattstudios.core.configuration

import me.mattstudios.config.SettingsHolder
import me.mattstudios.config.SettingsManager
import me.mattstudios.config.properties.Property
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.Plugin
import java.io.File

class Config(private val plugin: Plugin) : YamlConfiguration() {

    // The settings holder of the config
    private lateinit var settingsManager: SettingsManager

    /**
     * Loads the config file selected
     */
    fun load(configClass: Class<out SettingsHolder>) {
        settingsManager = SettingsManager.from(File(plugin.dataFolder, "config.yml"))
                .configurationData(configClass)
                .create()
    }

    /**
     * Gets a property
     */
    operator fun <T> get(property: Property<T>): T {
        return settingsManager.getProperty(property)
    }

    /**
     * Reloads the config
     */
    fun reload() {
        settingsManager.reload()
    }

}