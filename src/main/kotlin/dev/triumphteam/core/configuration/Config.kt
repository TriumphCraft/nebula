package dev.triumphteam.core.configuration

import me.mattstudios.config.properties.Property

/**
 * Simple config interface
 * All configs registered by the main plugin must implement this
 */
interface Config {

    /**
     * Gets a property from the config
     */
    operator fun <T> get(property: Property<T>): T

    /**
     * Sets a property value to the config, requires [save] for the setting to be affective in the file
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