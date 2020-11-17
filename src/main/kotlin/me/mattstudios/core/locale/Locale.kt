package me.mattstudios.core.locale

import me.mattstudios.config.SettingsHolder
import me.mattstudios.config.SettingsManager
import me.mattstudios.config.properties.Property
import me.mattstudios.core.func.color
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
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
        return settingsManager.getProperty(property)
    }

    /**
     * Sends a message to the sender from the lang file
     */
    fun sendMessage(sender: CommandSender, property: Property<String>) {
        sender.sendMessage(settingsManager.getProperty(property).color())
    }

    /**
     * Sends a message to the sender from the lang file
     */
    fun sendMessage(property: Property<String>) {
        Bukkit.getConsoleSender().sendMessage(settingsManager.getProperty(property).color())
    }

    /**
     * Used to reload the lang file
     */
    fun reload() {
        settingsManager.reload()
    }

}