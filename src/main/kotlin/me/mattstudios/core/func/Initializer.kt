package me.mattstudios.core.func

/**
 * Initializer for simply adding specific "utils" for initializing values in the main class
 */
interface Initializer<T> {
    fun initialize(manager: T)
}