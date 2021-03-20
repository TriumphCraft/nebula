package dev.triumphteam.core.context

import dev.triumphteam.core.TriumphPlugin
import dev.triumphteam.core.func.Initializer
import org.bukkit.Bukkit
import org.bukkit.event.Listener

/**
 * Handles registering listeners in a simple way
 */
class ListenerContext<T : TriumphPlugin>(private val plugin: T) {

    /**
     * Registers a new listener
     */
    fun add(listener: Listener) {
        Bukkit.getPluginManager().registerEvents(listener, plugin)
    }

    /**
     * Simple initializer for doing the above but not clog up the main class
     */
    fun initialize(initializer: Initializer<T>) {
        initializer.initialize(plugin)
    }

}