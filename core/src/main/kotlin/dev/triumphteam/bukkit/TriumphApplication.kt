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
package dev.triumphteam.bukkit

import dev.triumphteam.bukkit.feature.attribute.Attributes
import java.io.File

/**
 * An application, this represents any type of application for any platform.
 */
public interface TriumphApplication {

    /**
     * Main attributes for all features.
     */
    public val attributes: Attributes

    /**
     * A folder where the application wants to store data, similar to Bukkit's "dataFolder".
     */
    public val applicationFolder: File

    /**
     * Load function, that should be ran when the application is loading.
     * Depending on the platform this won't be needed, or might be called different.
     * @param load Function to be executed when the application loads.
     */
    public fun onLoad(load: () -> Unit) {}

    /**
     * Enable function, that should be ran when the application is done enabling.
     * Depending on the platform this won't be needed, or might be called different.
     * @param enable Function to be executed when the application is enabled.
     */
    public fun onEnable(enable: () -> Unit) {}

    /**
     * Disable function, that should be ran when the application is disabling.
     * Depending on the platform this won't be needed, or might be called different.
     * @param disable Function to be executed when the application is disabled.
     */
    public fun onDisable(disable: () -> Unit) {}

}