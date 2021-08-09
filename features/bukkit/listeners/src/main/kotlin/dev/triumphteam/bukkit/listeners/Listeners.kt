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
package dev.triumphteam.bukkit.listeners

import dev.triumphteam.bukkit.BukkitPlugin
import dev.triumphteam.bukkit.dsl.TriumphDsl
import dev.triumphteam.bukkit.feature.ApplicationFeature
import dev.triumphteam.bukkit.feature.attribute.AttributeKey
import dev.triumphteam.bukkit.feature.attribute.key
import dev.triumphteam.bukkit.feature.featureOrNull
import dev.triumphteam.bukkit.feature.install
import org.bukkit.Bukkit
import org.bukkit.event.Cancellable
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener

public class Listeners(public val plugin: BukkitPlugin<*>) {

    init {
        println("fuck")
    }

    public val triumphListener: TriumphListener = TriumphListener()

    public fun register(listener: Listener) {
        Bukkit.getPluginManager().registerEvents(listener, plugin)
    }

    public fun register(vararg listeners: Listener) {
        listeners.forEach(::register)
    }

    public inline fun <reified E : Event> on(
        priority: EventPriority = EventPriority.NORMAL,
        cancel: Boolean = false,
        crossinline function: E.() -> Unit
    ) {
        Bukkit.getPluginManager().registerEvent(
            E::class.java,
            triumphListener,
            priority,
            { _, event ->
                if (cancel && event is Cancellable) event.isCancelled = true
                function(event as E)
            },
            plugin
        )
    }

    /**
     * TODO
     * Simple initializer for doing the above but not clog up the main class
     */
    /*public fun initialize(initializer: Initializer<T>) {
        initializer.initialize(plugin)
    }*/

    /**
     * Feature companion, which is a factory for the [Listeners].
     */
    public companion object Feature : ApplicationFeature<BukkitPlugin<*>, Listeners, Listeners> {

        /**
         * The locale [AttributeKey].
         */
        public override val key: AttributeKey<Listeners> = key("Listeners")

        /**
         * Installation function to create a [Listeners] feature.
         * @param application The current application, Bukkit specific.
         * @param configure A [Listeners] to configure the [Listeners].
         */
        public override fun install(application: BukkitPlugin<*>, configure: Listeners.() -> Unit): Listeners {
            return Listeners(application).apply(configure)
        }

    }

}

@TriumphDsl
public fun <P : BukkitPlugin<P>> BukkitPlugin<P>.listeners(configuration: Listeners.() -> Unit): Listeners =
    featureOrNull(Listeners)?.apply(configuration) ?: install(Listeners, configuration)

public class TriumphListener : Listener