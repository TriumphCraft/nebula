package me.mattstudios.core

import me.mattstudios.core.configuration.Config
import me.mattstudios.core.locale.Locale
import me.mattstudios.mf.base.CommandBase
import me.mattstudios.mf.base.CommandManager
import me.mattstudios.mf.base.components.CompletionResolver
import me.mattstudios.mf.base.components.MessageResolver
import me.mattstudios.mf.base.components.ParameterResolver
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

@Suppress("MemberVisibilityCanBePrivate", "unused")
abstract class TriumphPlugin : JavaPlugin() {

    // Command manager from MF
    protected lateinit var commandManager: CommandManager
        private set

    // Config object for config handling
    private lateinit var config: Config

    // Locale object for message handling
    protected lateinit var locale: Locale
        private set

    // Calls the plugin load
    override fun onLoad() = load()

    override fun onEnable() {
        config = Config(this)
        locale = Locale(this)

        commandManager = CommandManager(this, true)

        // Calls the plugin enable
        enable()
    }

    // Calls the plugin disable
    override fun onDisable() = disable()

    /**
     * Used to pass the onLoad to the main plugin
     */
    protected open fun load() {}

    /**
     * Used to pass the onEnable to the main plugin
     */
    protected open fun enable() {}

    /**
     * Used to pass onDisable to the main plugin
     */
    protected open fun disable() {}

    /**
     * Gets the config, overridden from the spigot one
     *
     * @return The config
     */
    override fun getConfig() = config

    /**
     * Used for registering the plugin commands
     */
    protected fun registerCommands(vararg commands: CommandBase) = commands.forEach(commandManager::register)

    /**
     * Used for registering command completions
     *
     */
    protected fun registerCompletion(completionId: String, resolver: CompletionResolver) = commandManager.completionHandler.register(completionId, resolver)

    /**
     * Used for registering command parameters
     */
    protected fun registerParamType(clss: Class<*>, resolver: ParameterResolver) = commandManager.parameterHandler.register(clss, resolver)

    /**
     * Used for registering command messages
     */
    protected fun registerMessage(messageId: String, resolver: MessageResolver) = commandManager.messageHandler.register(messageId, resolver)

    /**
     * Used for registering the listeners easily
     */
    protected fun registerListeners(vararg listeners: Listener) = listeners.forEach { server.pluginManager.registerEvents(it, this) }

}