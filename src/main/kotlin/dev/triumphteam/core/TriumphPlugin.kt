package dev.triumphteam.core

import dev.triumphteam.core.configuration.Config
import dev.triumphteam.core.context.Enable
import dev.triumphteam.core.context.EnableContext
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

    // Functions for handling plugin stages
    private var loadFunction: () -> Unit = {}
    private var enableFunction: EnableContext.() -> Unit = {}
    private var disableFunction: () -> Unit = {}

    /**
     * Runs the plugin main logic
     */
    protected abstract fun plugin()

    // Calls the plugin load
    override fun onLoad() {
        plugin()
        loadFunction()
    }

    override fun onEnable() {
        config = Config(dataFolder)
        locale = Locale(dataFolder)

        commandManager = CommandManager(this, true)

        // Calls the plugin enable
        enableFunction(Enable(this))
    }

    // Calls the plugin disable
    override fun onDisable() = disableFunction()

    /**
     * Used to pass the onLoad to the main plugin
     */
    protected fun load(loadFunction: () -> Unit) {
        this.loadFunction = loadFunction
    }

    /**
     * Used to pass the onEnable to the main plugin
     */
    protected fun enable(enableFunction: EnableContext.() -> Unit) {
        this.enableFunction = enableFunction
    }

    /**
     * Used to pass onDisable to the main plugin
     */
    protected fun disable(disableFunction: () -> Unit) {
        this.disableFunction = disableFunction
    }

    /**
     * Gets the config, overridden from the spigot one
     */
    override fun getConfig() = config

}