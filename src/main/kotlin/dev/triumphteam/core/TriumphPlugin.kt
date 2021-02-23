package dev.triumphteam.core

import dev.triumphteam.core.configuration.Config
import dev.triumphteam.core.locale.Locale
import me.mattstudios.mf.base.CommandManager
import org.bukkit.plugin.java.JavaPlugin

/**
 * Adds many pre-made functionalities to make life easier
 */
@Suppress("MemberVisibilityCanBePrivate", "unused")
abstract class TriumphPlugin : JavaPlugin() {

    // Command manager from MF
    internal lateinit var commandManager: CommandManager
        private set

    // Config object for config handling
    private lateinit var config: Config

    // Locale object for message handling
    lateinit var locale: Locale
        private set

    /**
     * Main on load function
     */
    protected open fun load() {}

    /**
     * Main on enable function
     */
    protected open fun enable() {}

    /**
     * Main on disable function
     */
    protected open fun disable() {}

    /**
     * Calls [JavaPlugin]'s onLoad and calls [load], does nothing more, exists so the functions can be named similarly
     */
    override fun onLoad() = load()

    /**
     * Calls [JavaPlugin]'s onEnable, initialize some values and call the main [enable]
     */
    override fun onEnable() {
        config = Config(dataFolder)
        locale = Locale(dataFolder)

        commandManager = CommandManager(this, true)

        // Calls the plugin enable
        enable()
    }

    /**
     * Calls [JavaPlugin]'s onDisable and calls [disable], does nothing more, exists so the functions can be named similarly
     */
    override fun onDisable() = disable()

    /**
     * Gets the config, overridden from the spigot one
     */
    override fun getConfig() = config

}