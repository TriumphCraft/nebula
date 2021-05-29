/**
 * MIT License
 *
 * Copyright (c) 2021 Mateus Moreira
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
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