/**
 * MIT License
 *
 * Copyright (c) 2021-2022 TriumphTeam
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
package dev.triumphteam.nebula.commands

import dev.triumphteam.cmd.core.BaseCommand
import dev.triumphteam.cmd.core.CommandManager
import dev.triumphteam.cmd.core.argument.ArgumentResolver
import dev.triumphteam.cmd.core.message.MessageKey
import dev.triumphteam.cmd.core.message.MessageResolver
import dev.triumphteam.cmd.core.message.context.MessageContext
import dev.triumphteam.cmd.core.suggestion.SuggestionKey
import dev.triumphteam.cmd.core.suggestion.SuggestionResolver
import dev.triumphteam.nebula.container.Container
import dev.triumphteam.nebula.feature.Feature

public abstract class CommandWrapper<DS, S>(
    container: Container,
    public val commandManager: CommandManager<DS, S>,
) : Feature(container) {

    private fun register(command: BaseCommand) {
        commandManager.registerCommand(command)
    }

    public fun register(vararg commands: BaseCommand) {
        commands.forEach(::register)
    }

    public fun suggestion(key: SuggestionKey, resolver: SuggestionResolver<S>) {
        commandManager.registerSuggestion(key, resolver)
    }

    public fun suggestion(type: Class<*>, resolver: SuggestionResolver<S>) {
        commandManager.registerSuggestion(type, resolver)
    }

    public fun argument(type: Class<*>, resolver: ArgumentResolver<S>) {
        commandManager.registerArgument(type, resolver)
    }

    public inline fun <reified T> argument(resolver: ArgumentResolver<S>) {
        commandManager.registerArgument(T::class.java, resolver)
    }

    public fun <C : MessageContext> message(key: MessageKey<C>, messageResolver: MessageResolver<S, C>) {
        commandManager.registerMessage(key, messageResolver)
    }
}