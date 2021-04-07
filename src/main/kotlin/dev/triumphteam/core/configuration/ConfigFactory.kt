package dev.triumphteam.core.configuration

import dev.triumphteam.core.TriumphPlugin

/**
 * Simple config factory for using with companion objects
 */
interface ConfigFactory {

    /**
     * Factory for creating the config
     */
    fun create(plugin: TriumphPlugin): Config

}