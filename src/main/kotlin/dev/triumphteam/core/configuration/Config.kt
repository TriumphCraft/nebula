package dev.triumphteam.core.configuration

import me.mattstudios.config.properties.Property
import org.bukkit.configuration.file.YamlConfiguration

/**
 * Config handler for all plugins
 * *Extends [YamlConfiguration] just to override JavaPlugin's `getConfig`*
 * **Never uses anything from [YamlConfiguration]**
 */
@Suppress("MemberVisibilityCanBePrivate")
interface Config {

    /**
     * Gets a property
     */
    operator fun <T> get(property: Property<T>): T

    /**
     * Gets a property
     */
    operator fun <T : Any> set(property: Property<T>, value: T)

    /**
     * Reloads the config
     */
    fun reload()

    /**
     * Saves the config
     */
    fun save()

}