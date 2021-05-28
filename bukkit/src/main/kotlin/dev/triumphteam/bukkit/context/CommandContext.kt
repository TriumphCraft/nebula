package dev.triumphteam.bukkit.context

/**
 * Command builder for starting up command related things in the main class
 */
/*class CommandContext<T : TriumphPlugin>(val plugin: T) {

    private val commandManager = plugin.commandManager

    /**
     * Registers a new command
     */
    fun register(command: CommandBase) {
        commandManager.register(command)
    }

    /**
     * Registers a new command
     */
    fun register(vararg commands: CommandBase) {
        commands.forEach(this::register)
    }

    /**
     * Register a completion to be used in the commands
     */
    fun completion(id: String, resolver: CompletionResolver) {
        commandManager.completionHandler.register(id, resolver)
    }

    /**
     * Register a parameter type to be used in the commands
     */
    fun parameter(type: Class<*>, resolver: ParameterResolver) {
        commandManager.parameterHandler.register(type, resolver)
    }

    /**
     * Registers a command message
     */
    fun message(id: String, resolver: MessageResolver) {
        commandManager.messageHandler.register(id, resolver)
    }

    /**
     * Simple initializer for doing the above but not clog up the main class
     */
    fun initialize(initializer: Initializer<T>) {
        initializer.initialize(plugin)
    }

}*/