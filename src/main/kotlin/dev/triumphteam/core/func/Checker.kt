package dev.triumphteam.core.func

import dev.triumphteam.core.TriumphPlugin

/**
 * Like the [Initializer], this is used for organizing the main class better
 */
interface Checker<T : TriumphPlugin> {

    /**
     * Calls a checking block, this can be used for checking dependencies, etc
     */
    fun check(plugin: T): Boolean

}