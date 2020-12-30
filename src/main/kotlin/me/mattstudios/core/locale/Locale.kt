package me.mattstudios.core.locale

import me.mattstudios.config.SettingsHolder
import me.mattstudios.config.SettingsManager
import me.mattstudios.config.properties.Property
import org.bukkit.plugin.Plugin
import java.io.File

class Locale(private val plugin: Plugin) {

    // The settings holder of the languages
    private lateinit var settingsManager: SettingsManager

    /**
     * Loads the messages file
     */
    fun load(messageClass: Class<out SettingsHolder>, fileName: String) {
        settingsManager = SettingsManager.from(File(plugin.dataFolder, "lang/$fileName.yml"))
                .configurationData(messageClass)
                .create()
    }

    /**
     * Gets a message from the file colored
     */
    operator fun <T> get(property: Property<T>): T {
        return settingsManager[property]
    }

    /**
     * Used to reload the lang file
     */
    fun reload() {
        settingsManager.reload()
    }

}