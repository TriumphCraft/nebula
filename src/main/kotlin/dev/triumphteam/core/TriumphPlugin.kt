package dev.triumphteam.core

import dev.triumphteam.core.configuration.Config
import dev.triumphteam.core.configuration.ConfigFactory
import dev.triumphteam.core.configuration.UnregisteredConfigException
import dev.triumphteam.core.context.CommandContext
import dev.triumphteam.core.context.ListenerContext
import dev.triumphteam.core.func.Initializer
import dev.triumphteam.core.locale.Language
import dev.triumphteam.core.locale.Locale
import me.mattstudios.config.SettingsHolder
import me.mattstudios.mf.base.CommandManager
import org.bukkit.plugin.java.JavaPlugin
import kotlin.reflect.KClass

/**
 * Adds many pre-made functionalities to make life easier
 */
@Suppress("MemberVisibilityCanBePrivate", "unused")
abstract class TriumphPlugin : JavaPlugin() {

    // Config map for saving all the configs used by the main plugin
    // Unfortunately it's required to be public due to the reified inline function for the getter
    val configs = mutableMapOf<KClass<out Config>, Config>()

    // Command manager from MF
    internal lateinit var commandManager: CommandManager
        private set

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
     *  "Registers" a config to the plugin's configs
     *  Uses a [ConfigFactory] which usually is a companion object, for easy creation of the config
     */
    protected fun config(configFactory: ConfigFactory) {
        configFactory.create(this).also {
            configs[it::class] = it
        }
    }

    /**
     * Gets a config that has been registered before
     */
    inline fun <reified T : Config> config(): T {
        return (configs[T::class] ?: throw UnregisteredConfigException(T::class)) as T
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
    inline fun <T : TriumphPlugin> T.commands(context: CommandContext<T>.() -> Unit) {
        CommandContext(this).apply(context)
    }

    /**
     * Simple initializer for doing the above but not clog up the main class
     */
    protected fun <T : TriumphPlugin> T.initialize(initializer: Initializer<T>) {
        initializer.initialize(this)
    }


    /**
     * Creates a [ListenerContext] and applies the logic from enable
     */
    inline fun <T : TriumphPlugin> T.listeners(context: ListenerContext<T>.() -> Unit) {
        ListenerContext(this).apply(context)
    }

}