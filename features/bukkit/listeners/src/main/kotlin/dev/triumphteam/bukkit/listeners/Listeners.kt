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
package dev.triumphteam.bukkit.listeners

import dev.triumphteam.core.BukkitApplication
import dev.triumphteam.core.container.Container
import dev.triumphteam.core.container.inject
import dev.triumphteam.core.dsl.TriumphDsl
import dev.triumphteam.core.feature.Feature
import dev.triumphteam.core.feature.FeatureFactory
import dev.triumphteam.core.feature.install
import org.bukkit.Bukkit
import org.bukkit.event.Listener
import org.bukkit.plugin.Plugin

/** Simplifies registration of [Listener]s. */
public class Listeners(container: Container) : Feature(container) {

    /** Scope key. */
    override val key: String = "listeners"

    private val plugin: Plugin by inject()

    /** Registers a bukkit [Listener]. */
    public fun register(listener: Listener): Unit = Bukkit.getPluginManager().registerEvents(listener, plugin)

    /** Registers a bukkit [Listener], but provides the parent container. */
    public fun register(block: (Container) -> Listener): Unit =
        Bukkit.getPluginManager().registerEvents(block(parent), plugin)

    /** Registers multiple listeners in a row. */
    public fun register(vararg listeners: Listener): Unit = listeners.forEach(::register)

    /** Feature companion, which is a factory for the [Listeners]. */
    public companion object : FeatureFactory<Listeners> {
        override fun install(container: Container): Listeners {
            return Listeners(container)
        }
    }
}

/**
 * Simplifies adding new listeners.
 * It's a feature that installs itself when used.
 */
@TriumphDsl
public fun <P : BukkitApplication> P.listeners(configuration: Listeners.() -> Unit): Listeners =
    install(Listeners, configuration)