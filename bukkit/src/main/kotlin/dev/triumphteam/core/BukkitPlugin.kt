package dev.triumphteam.core

import org.bukkit.plugin.java.JavaPlugin

/**
 * Main implementation for Bukkit
 * @param P Implementation that extends [BukkitPlugin]
 * @param module The main module of the plugin (might add support for multiple)
 */
public abstract class BukkitPlugin<P : TriumphModule>(
    private val module: P.() -> Unit
) : JavaPlugin(), TriumphModule {

    init {
        build()
    }

    /**
     * Not happy with the init and the nasty unchecked cast
     */
    @Suppress("UNCHECKED_CAST")
    private fun build() {
        module(this as P)
    }

}
