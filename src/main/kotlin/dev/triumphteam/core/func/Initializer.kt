package dev.triumphteam.core.func

/**
 * Initializer for simply adding specific "utils" for initializing values in the main class
 */
interface Initializer<T> {

    /**
     * Calls a initializing block, this can be used for registering commands, listeners, etc
     */
    fun initialize(context: T)

}