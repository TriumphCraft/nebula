package me.mattstudios.core.locale

import me.mattstudios.config.SettingsHolder
import me.mattstudios.config.SettingsManager
import me.mattstudios.config.properties.Property
import java.io.File
import java.nio.file.Paths

/**
 * **Locale** handler for all plugins
 */
@Suppress("MemberVisibilityCanBePrivate")
class Locale(private val folder: File) {

    var language = Language.ENGLISH
        private set

    // The settings holder of the languages
    private lateinit var settingsManager: SettingsManager

    // The message [SettingHolder]
    private lateinit var messageClass: Class<out SettingsHolder>

    /**
     * Sets the message [SettingsHolder] to be used for the [Locale]
     */
    fun setHolder(messageClass: Class<out SettingsHolder>): Locale {
        this.messageClass = messageClass
        return this
    }

    /**
     * Sets the current [Language] and starts the new [SettingsManager]
     */
    fun setLocale(language: Language): Locale {
        this.language = language
        settingsManager = SettingsManager.from(Paths.get(folder.path, "lang/${language.file}.yml"))
            .configurationData(messageClass)
            .create()

        return this
    }

    /**
     * Gets a message from the locale file
     */
    operator fun <T> get(property: Property<T>) = settingsManager[property]

    /**
     * Used to reload the lang file
     */
    fun reload() = settingsManager.reload()

}