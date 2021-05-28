package dev.triumphteam.core.locale

import dev.triumphteam.core.TriumphApplication
import dev.triumphteam.core.feature.ApplicationFeature
import dev.triumphteam.core.feature.attribute.AttributeKey
import dev.triumphteam.core.feature.attribute.key
import me.mattstudios.config.SettingsHolder
import me.mattstudios.config.SettingsManager
import me.mattstudios.config.properties.Property
import java.nio.file.Path
import java.nio.file.Paths

/**
 * **Locale** feature for all applications
 */
public class Locale(private val configuration: LocaleConfiguration) {

    // Current being used language
    public var language: Language = configuration.language

    // The message [SettingHolder]
    private var messageHolder: Class<out SettingsHolder> = configuration.holder::class.java

    // The settings holder of the languages
    private var settingsManager: SettingsManager = create()

    /**
     * Creates the [SettingsManager]
     */
    private fun create(): SettingsManager {
        return SettingsManager.from(Paths.get(configuration.folder.toString(), "${language.file}.yml"))
            .configurationData(messageHolder)
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

    /**
     * The configuration to be used by the feature
     * @param application [TriumphApplication] to get the folder from
     */
    public class LocaleConfiguration(application: TriumphApplication) {

        /**
         * Default language
         */
        public var language: Language = Language.ENGLISH

        /**
         * Holder which must be initialized
         */
        public lateinit var holder: SettingsHolder

        /**
         * The folder [Path] or default
         */
        public var folder: Path = Paths.get(application.applicationFolder.path, "lang/")

    }

    /**
     * Feature companion
     */
    public companion object Feature : ApplicationFeature<TriumphApplication, LocaleConfiguration, Locale> {

        /**
         * The locale key
         */
        override val key: AttributeKey<Locale> = key("Locale")

        /**
         * Installation function to create a [Locale] feature
         */
        override fun install(application: TriumphApplication, configure: LocaleConfiguration.() -> Unit): Locale {
            return Locale(LocaleConfiguration(application).apply(configure))
        }
    }

}