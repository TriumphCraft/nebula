package dev.triumphteam.core

import dev.triumphteam.core.configuration.Config
import dev.triumphteam.core.context.CommandContext
import dev.triumphteam.core.context.ListenerContext
import dev.triumphteam.core.locale.Language
import dev.triumphteam.core.locale.Locale
import me.mattstudios.config.SettingsHolder
import me.mattstudios.config.beanmapper.PropertyMapper
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

    /**
     * Sets the setting holder and mapper for the [Config]
     */
    protected fun config(holder: SettingsHolder, mapper: PropertyMapper?) {
        config.create(holder::class.java, mapper)
    }

    /**
     * Sets the setting holder and the language of the [Locale] and creates the file
     */
    protected fun locale(holder: SettingsHolder, language: Language) {
        locale.setHolder(holder::class.java).setLocale(language).create()
    }

    /**
     * Creates a [CommandContext] and applies the logic from enable
     */
    fun <T : TriumphPlugin> T.commands(context: CommandContext<T>.() -> Unit) {
        CommandContext(this).apply(context)
    }


    /**
     * Creates a [ListenerContext] and applies the logic from enable
     */
    fun <T : TriumphPlugin> T.listeners(context: ListenerContext<T>.() -> Unit) {
        ListenerContext(this).apply(context)
    }

}