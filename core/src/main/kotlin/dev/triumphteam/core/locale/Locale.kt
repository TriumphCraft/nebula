package dev.triumphteam.core.locale

import me.mattstudios.config.SettingsHolder
import me.mattstudios.config.SettingsManager
import me.mattstudios.config.properties.Property
import java.io.File
import java.nio.file.Paths

/**
 * **Locale** handler for all plugins
 */
@Suppress("MemberVisibilityCanBePrivate")
public class Locale(private val folder: File) {

    // Current being used language
    public var language: Language = Language.ENGLISH
        private set

    // The settings holder of the languages
    private lateinit var settingsManager: SettingsManager

    // The message [SettingHolder]
    private lateinit var messageClass: Class<out SettingsHolder>

    /**
     * Sets the message [SettingsHolder] to be used for the [Locale]
     */
    public fun setHolder(messageClass: Class<out SettingsHolder>): Locale {
        this.messageClass = messageClass
        return this
    }

    /**
     * Sets the current [Language]
     */
    public fun setLocale(language: Language): Locale {
        this.language = language
        return this
    }

    /**
     * Creates the [SettingsManager]
     */
    public fun create() {
        settingsManager = SettingsManager.from(Paths.get(folder.path, "lang/${language.file}.yml"))
            .configurationData(messageClass)
            .create()
    }

    /**
     * Gets a message from the locale file
     */
    public operator fun <T> get(property: Property<T>): T = settingsManager[property]

    /**
     * Used to reload the lang file
     */
    public fun reload() {
        settingsManager.reload()
    }

}