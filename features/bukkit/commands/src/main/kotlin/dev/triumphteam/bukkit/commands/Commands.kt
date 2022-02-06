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
package dev.triumphteam.bukkit.commands

import dev.triumphteam.cmd.bukkit.BukkitCommandManager
import dev.triumphteam.cmd.core.BaseCommand
import dev.triumphteam.cmd.core.argument.ArgumentResolver
import dev.triumphteam.cmd.core.message.MessageKey
import dev.triumphteam.cmd.core.message.MessageResolver
import dev.triumphteam.cmd.core.message.context.MessageContext
import dev.triumphteam.cmd.core.sender.SenderMapper
import dev.triumphteam.cmd.core.sender.SenderValidator
import dev.triumphteam.cmd.core.suggestion.SuggestionKey
import dev.triumphteam.cmd.core.suggestion.SuggestionResolver
import dev.triumphteam.core.BukkitPlugin
import dev.triumphteam.core.TriumphApplication
import dev.triumphteam.core.dsl.TriumphDsl
import dev.triumphteam.core.feature.ApplicationFeature
import dev.triumphteam.core.feature.featureOrNull
import dev.triumphteam.core.feature.install
import org.bukkit.command.CommandSender
import org.checkerframework.checker.units.qual.C

public abstract class Commands<S>(
    plugin: BukkitPlugin,
    mapper: SenderMapper<CommandSender, S>,
    validator: SenderValidator<S>,
) {

    public val commandManager: BukkitCommandManager<S> = BukkitCommandManager.create(plugin, mapper, validator)

    private fun register(command: BaseCommand) {
        commandManager.registerCommand(command)
    }

    public fun register(vararg commands: BaseCommand) {
        commands.forEach(::register)
    }

    public fun suggestion(key: SuggestionKey, resolver: SuggestionResolver<S>) {
        commandManager.registerSuggestion(key, resolver)
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

/**
 * Command feature is used for specifying the sender of the command.
 */
public interface CommandFeature<S, in A : TriumphApplication, out C : Any, F : Commands<S>> :
    ApplicationFeature<A, C, F>

/**
 * Command utility for easier setup of the commands.
 */
@TriumphDsl
public fun <P : BukkitPlugin, S, C : Commands<S>> P.commands(
    commands: CommandFeature<S, P, C, C>,
    config: C.() -> Unit,
) {
    featureOrNull(commands)?.apply(config) ?: install(commands, config)
}

/*@TriumphDsl
public fun <P : BukkitPlugin> P.commands(configuration: Commands.() -> Unit): Commands =
    featureOrNull(Commands)?.apply(configuration) ?: install(Commands, configuration)*/