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
package dev.triumphteam.nebula.feature

import dev.triumphteam.nebula.container.Container
import dev.triumphteam.nebula.container.inject
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.plugin.Plugin

/**
 * A Bukkit specific feature.
 * Will auto register any listener present in the parent Class.
 */
public abstract class Feature(container: Container) : BaseFeature(container), Listener {

    private val plugin: Plugin by inject()

    // Check to know if should or not unregister listeners.
    private var listeners = false

    init {
        // Registers this listener if there is an EventHandler present.
        onRegister {
            val handlers = javaClass.declaredMethods.any { it.isAnnotationPresent(EventHandler::class.java) }
            if (!handlers) return@onRegister

            listeners = true
            Bukkit.getPluginManager().registerEvents(this, plugin)
        }

        // Unregisters this listener if it was registered before.
        onUnregister {
            if (!listeners) return@onUnregister
            HandlerList.unregisterAll(this)
        }
    }
}