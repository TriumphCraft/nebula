package dev.triumphteam.bukkit

import me.mattstudios.mf.base.CommandManager
import me.mattstudios.mf.base.components.MessageResolver
import org.bukkit.plugin.java.JavaPlugin
import java.util.Locale

/**
 * Adds many pre-made functionalities to make life easier
 */
@Suppress("MemberVisibilityCanBePrivate", "unused")
public abstract class OldPlugin : JavaPlugin() {

    // Config map for saving all the configs used by the main plugin
    // Unfortunately it's required to be public due to the reified inline function for the getter
    //public val configs: MutableMap<KClass<out BaseConfig>, BaseConfig> = mutableMapOf()

    // Command manager from MF
    internal lateinit var commandManager: CommandManager
        private set

    // Locale object for message handling
    public lateinit var locale: Locale
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
    override fun onLoad() {
        load()
    }

    /**
     * Calls [JavaPlugin]'s onEnable, initialize some values and call the main [enable]
     */
    override fun onEnable() {
        commandManager = CommandManager(this, true)

        // Calls the plugin enable
        enable()
    }

    /**
     * Calls [JavaPlugin]'s onDisable and calls [disable], does nothing more, exists so the functions can be named similarly
     */
    override fun onDisable() {
        disable()
    }

    /**
     * Gets a config that has been registered before
     */
    /*public inline fun <reified T : BaseConfig> config(): T {
        return (configs[T::class] ?: throw UnregisteredConfigException(T::class)) as T
    }*/

    /**
     * Creates a [CommandContext] and applies the logic from enable
     */
    /*inline fun <T : OldPlugin> T.commands(context: CommandContext<T>.() -> Unit) {
        CommandContext(this).apply(context)
    }*/

    /**
     * Simple initializer for doing the above but not clog up the main class
     */
    /*protected fun <T : OldPlugin> T.initialize(initializer: Initializer<T>) {
        initializer.initialize(this)
    }*/

    /**
     * Util for doing enable checks, like dependency checks and things like that
     */
    /*protected fun <T : OldPlugin> T.check(checker: Checker<T>): Boolean {
        return checker.check(this)
    }*/


    /**
     * Creates a [ListenerContext] and applies the logic from enable
     */
    /*inline fun <T : OldPlugin> T.listeners(context: ListenerContext<T>.() -> Unit) {
        ListenerContext(this).apply(context)
    }*/

    private fun registerMessages(vararg pairs: Pair<String, MessageResolver>) =
        pairs.forEach { pair -> commandManager.messageHandler.register(pair.first, pair.second) }

}