/**
 * MIT License
 *
 * Copyright (c) 2021-2022 TriumphTeam
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package dev.triumphteam.core.locale

import dev.triumphteam.core.TriumphApplication
import dev.triumphteam.core.feature.FeatureFactory
import me.mattstudios.config.SettingsHolder
import me.mattstudios.config.SettingsManager
import me.mattstudios.config.properties.Property
import java.nio.file.Path
import java.nio.file.Paths

/**
 * **Locale** feature for all applications.
 */
public class Locale private constructor(private val configuration: LocaleConfiguration) {

    /**
     * Current being used [Language].
     */
    public var language: Language = configuration.language

    /**
     * The message [SettingsHolder].
     */
    private var messageHolder: Class<out SettingsHolder> = configuration.holder::class.java

    /**
     * A [SettingsManager] for the config.
     * It's a `var` because when language is changed a new [SettingsManager] must be created.
     */
    private var settingsManager: SettingsManager = create()

    /**
     * Creates the [SettingsManager].
     */
    private fun create(): SettingsManager {
        return SettingsManager.from(Paths.get(configuration.folder.toString(), "${language.file}.yml"))
            .configurationData(messageHolder)
            .create()
    }

    /**
     * Gets a message from the locale file.
     */
    public operator fun <T> get(property: Property<T>): T = settingsManager[property]

    /**
     * Used to reload the lang file.
     */
    public fun reload() {
        settingsManager.reload()
    }

    /**
     * The configuration to be used by the feature.
     */
    public class LocaleConfiguration(application: TriumphApplication) {

        /**
         * Default [Language].
         */
        public var language: Language = Language.ENGLISH

        /**
         * The [SettingsHolder] which must be initialized.
         */
        public lateinit var holder: SettingsHolder

        /**
         * The folder [Path] or default.
         */
        public var folder: Path = Paths.get(application.applicationFolder.path, "lang/")

    }

    /**
     * Feature companion, which is a factory for the [Locale].
     */
    public companion object Feature : FeatureFactory<TriumphApplication, LocaleConfiguration, Locale> {

        /**
         * Installation function to create a [Locale] feature.
         */
        override fun install(application: TriumphApplication, configure: LocaleConfiguration.() -> Unit): Locale {
            return Locale(LocaleConfiguration(application).apply(configure))
        }
    }

}