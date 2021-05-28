package dev.triumphteam.bukkit.context

/**
 * Handles registering listeners in a simple way
 */
/*public class ListenerContext<T : BukkitPlugin>(public val plugin: T) {

    /**
     * Registers a new listener
     */
    public fun register(listener: Listener) {
        Bukkit.getPluginManager().registerEvents(listener, plugin)
    }

    /**
     * Registers a new command
     */
    public fun register(vararg listeners: Listener) {
        listeners.forEach(this::register)
    }

    /**
     * Simple initializer for doing the above but not clog up the main class
     */
    public fun initialize(initializer: Initializer<T>) {
        initializer.initialize(plugin)
    }

}*/