package dev.triumphteam.core.func

import dev.triumphteam.core.TriumphPlugin

/**
 * Initializer for simply adding specific "utils" for initializing values in the main class
 */
interface Initializer<T : TriumphPlugin> {

    /**
     * Calls a initializing block, this can be used for registering commands, listeners, etc
     */
    fun initialize(plugin: T)

}