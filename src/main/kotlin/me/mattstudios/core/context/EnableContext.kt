package me.mattstudios.core.context

import me.mattstudios.config.SettingsHolder
import me.mattstudios.config.beanmapper.PropertyMapper
import me.mattstudios.core.TriumphPlugin
import me.mattstudios.core.locale.Language

/**
 * Allows some logic to only be accessible in the enable instead of globally in the main class
 */
interface EnableContext {

    /**
     * Starts up the config module
     */
    fun config(holder: SettingsHolder, mapper: PropertyMapper? = null)

    /**
     * Starts up the locale module
     */
    fun locale(holder: SettingsHolder, language: Language = Language.ENGLISH)

    /**
     * Used for registering the plugin commands
     */
    fun commands(context: CommandContext.() -> Unit)

    /**
     * Used for registering the plugin listeners
     */
    fun listeners(context: ListenerContext.() -> Unit)

}

/**
 * Implementation for [EnableContext]
 */
class Enable(private val plugin: TriumphPlugin) : EnableContext {

    /**
     * Starts up the config module
     */
    override fun config(holder: SettingsHolder, mapper: PropertyMapper?) {
        plugin.config.create(holder::class.java, mapper)
    }

    /**
     * Starts up the locale module
     */
    override fun locale(holder: SettingsHolder, language: Language) {
        plugin.locale.setHolder(holder::class.java).setLocale(language).create()
    }

    /**
     * Used for registering the plugin commands
     */
    override fun commands(context: CommandContext.() -> Unit) {
        CommandContext(plugin.commandManager).apply(context)
    }

    /**
     * Used for registering the plugin listeners
     */
    override fun listeners(context: ListenerContext.() -> Unit) {
        ListenerContext(plugin).apply(context)
    }

}