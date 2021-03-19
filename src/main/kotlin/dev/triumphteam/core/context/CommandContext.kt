package dev.triumphteam.core.context

import dev.triumphteam.core.func.Initializer
import me.mattstudios.mf.base.CommandBase
import me.mattstudios.mf.base.CommandManager
import me.mattstudios.mf.base.components.CompletionResolver
import me.mattstudios.mf.base.components.MessageResolver
import me.mattstudios.mf.base.components.ParameterResolver

/**
 * Command builder for starting up command related things in the main class
 */
class CommandContext(private val commandManager: CommandManager) {

    /**
     * Registers a new command
     */
    fun add(commandBase: CommandBase) {
        commandManager.register(commandBase)
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
    fun initialize(initializer: Initializer<CommandContext>) {
        initializer.initialize(this)
    }

}