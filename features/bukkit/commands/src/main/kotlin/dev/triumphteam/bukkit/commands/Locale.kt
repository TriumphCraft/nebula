/**
 * MIT License
 *
 * Copyright (c) 2021 Mateus Moreira
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
package dev.triumphteam.bukkit.commands

import dev.triumphteam.bukkit.TriumphApplication
import dev.triumphteam.bukkit.feature.ApplicationFeature
import dev.triumphteam.bukkit.feature.attribute.AttributeKey
import dev.triumphteam.bukkit.feature.attribute.key
import me.mattstudios.config.SettingsHolder
import me.mattstudios.config.SettingsManager
import me.mattstudios.config.properties.Property
import org.intellij.lang.annotations.Language
import java.nio.file.Path
import java.nio.file.Paths
import java.util.Locale

public class Commands(private val configuration: LocaleConfiguration) {


    /**
     * The configuration to be used by the feature.
     * @param application [TriumphApplication] to get the folder from.
     */
    public class LocaleConfiguration(application: TriumphApplication) {

    }

    /**
     * Feature companion, which is a factory for the [Locale].
     */
    public companion object Feature : ApplicationFeature<TriumphApplication, LocaleConfiguration, Locale> {

        /**
         * The locale [AttributeKey].
         */
        override val key: AttributeKey<Locale> = key("Locale")

        /**
         * Installation function to create a [Locale] feature.
         * @param application The current application, platform agnostic.
         * @param configure A [LocaleConfiguration] to configure the [Locale].
         */
        override fun install(application: TriumphApplication, configure: LocaleConfiguration.() -> Unit): Locale {
            return Locale(LocaleConfiguration(application).apply(configure))
        }
    }

}