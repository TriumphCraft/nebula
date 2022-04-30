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
package dev.triumphteam.core.configuration

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