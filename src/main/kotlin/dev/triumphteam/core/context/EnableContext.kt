package dev.triumphteam.core.context

import dev.triumphteam.core.TriumphPlugin
import dev.triumphteam.core.configuration.Config
import dev.triumphteam.core.locale.Language
import dev.triumphteam.core.locale.Locale
import me.mattstudios.config.SettingsHolder
import me.mattstudios.config.beanmapper.PropertyMapper

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
     * Sets the setting holder and mapper for the [Config]
     */
    override fun config(holder: SettingsHolder, mapper: PropertyMapper?) {
        plugin.config.create(holder::class.java, mapper)
    }

    /**
     * Sets the setting holder and the language of the [Locale] and creates the file
     */
    override fun locale(holder: SettingsHolder, language: Language) {
        plugin.locale.setHolder(holder::class.java).setLocale(language).create()
    }

    /**
     * Creates a [CommandContext] and applies the logic from enable
     */
    override fun commands(context: CommandContext.() -> Unit) {
        CommandContext(plugin.commandManager).apply(context)
    }

    /**
     * Creates a [ListenerContext] and applies the logic from enable
     */
    override fun listeners(context: ListenerContext.() -> Unit) {
        ListenerContext(plugin).apply(context)
    }

}