package me.mattstudios.core.context

import me.mattstudios.core.TriumphPlugin
import me.mattstudios.core.func.Initializer
import org.bukkit.Bukkit
import org.bukkit.event.Listener

class ListenerContext(private val plugin: TriumphPlugin) {

    /**
     * Registers a new listener
     */
    fun add(listener: Listener) {
        Bukkit.getPluginManager().registerEvents(listener, plugin)
    }

    /**
     * Simple initializer for doing the above but not clog up the main class
     */
    fun initialize(initializer: Initializer<TriumphPlugin>) {
        initializer.initialize(plugin)
    }

}