package dev.triumphteam.core.func

import dev.triumphteam.core.TriumphPlugin
import dev.triumphteam.core.configuration.Config
import dev.triumphteam.core.context.CommandContext
import dev.triumphteam.core.context.ListenerContext
import dev.triumphteam.core.locale.Language
import dev.triumphteam.core.locale.Locale
import me.mattstudios.config.SettingsHolder
import me.mattstudios.config.beanmapper.PropertyMapper
import org.bukkit.Bukkit
import org.bukkit.ChatColor

/**
 * Utility to use color codes easily
 */
fun String.color() = ChatColor.translateAlternateColorCodes('&', this)

/**
 * Utility to use color codes easily
 */
fun List<String>.color() = map { string: String -> ChatColor.translateAlternateColorCodes('&', string) }

/**
 * Simplified way for sending console messages
 */
fun String.log() = Bukkit.getConsoleSender().sendMessage(color())

/**
 * Sets the setting holder and mapper for the [Config]
 */
fun TriumphPlugin.config(holder: SettingsHolder, mapper: PropertyMapper?) {
    config.create(holder::class.java, mapper)
}

/**
 * Sets the setting holder and the language of the [Locale] and creates the file
 */
fun TriumphPlugin.locale(holder: SettingsHolder, language: Language) {
    locale.setHolder(holder::class.java).setLocale(language).create()
}

/**
 * Creates a [CommandContext] and applies the logic from enable
 */
fun TriumphPlugin.commands(context: CommandContext.() -> Unit) {
    CommandContext(commandManager).apply(context)
}

/**
 * Creates a [ListenerContext] and applies the logic from enable
 */
fun TriumphPlugin.listeners(context: ListenerContext.() -> Unit) {
    ListenerContext(this).apply(context)
}