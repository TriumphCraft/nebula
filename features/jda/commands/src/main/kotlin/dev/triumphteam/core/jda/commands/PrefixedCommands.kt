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
package dev.triumphteam.core.jda.commands

import dev.triumphteam.cmd.core.BaseCommand
import dev.triumphteam.cmd.core.argument.ArgumentResolver
import dev.triumphteam.cmd.core.message.MessageKey
import dev.triumphteam.cmd.core.message.MessageResolver
import dev.triumphteam.cmd.core.message.context.MessageContext
import dev.triumphteam.cmd.core.requirement.RequirementKey
import dev.triumphteam.cmd.core.requirement.RequirementResolver
import dev.triumphteam.cmd.prefixed.PrefixedCommandManager
import dev.triumphteam.cmd.prefixed.sender.PrefixedSender
import dev.triumphteam.core.dsl.TriumphDsl
import dev.triumphteam.core.feature.ApplicationFeature
import dev.triumphteam.core.feature.attribute.AttributeKey
import dev.triumphteam.core.feature.attribute.key
import dev.triumphteam.core.feature.featureOrNull
import dev.triumphteam.core.feature.install
import dev.triumphteam.core.jda.JdaApplication
import net.dv8tion.jda.api.entities.Guild

/**
 * Feature for simplifying the creation of prefixed commands.
 */
public class PrefixedCommands private constructor(application: JdaApplication) {

    private val commandManager = PrefixedCommandManager.createDefault(application.jda)

    /**
     * Registers a global command.
     */
    public fun register(command: BaseCommand) {
        commandManager.registerCommand(command)
    }

    /**
     * Registers a command for a specific guild.
     */
    public fun register(guild: Guild, command: BaseCommand) {
        commandManager.registerCommand(guild, command)
    }

    /**
     * Registers global commands.
     */
    public fun register(vararg commands: BaseCommand) {
        commands.forEach(::register)
    }

    /**
     * Registers commands for a specific guild.
     */
    public fun register(guild: Guild, vararg commands: BaseCommand) {
        commands.forEach { register(guild, it) }
    }


    /**
     * Register an argument type to be used in the commands.
     */
    public fun argument(type: Class<*>, resolver: ArgumentResolver<PrefixedSender>) {
        commandManager.registerArgument(type, resolver)
    }

    /**
     * Register a requirement type to be used in the commands.
     */
    public fun requirement(key: RequirementKey, resolver: RequirementResolver<PrefixedSender>) {
        commandManager.registerRequirement(key, resolver)
    }

    /**
     * Registers a command message.
     */
    public fun <C : MessageContext> message(key: MessageKey<C>, resolver: MessageResolver<PrefixedSender, C>) {
        commandManager.registerMessage(key, resolver)
    }

    /**
     * Feature companion, which is a factory for the [PrefixedCommands].
     */
    public companion object Feature : ApplicationFeature<JdaApplication, PrefixedCommands, PrefixedCommands> {

        /**
         * The prefixed commands [AttributeKey].
         */
        public override val key: AttributeKey<PrefixedCommands> = key("jda-prefixed-commands")

        /**
         * Installation function to create a [PrefixedCommands] feature.
         */
        public override fun install(
            application: JdaApplication,
            configure: PrefixedCommands.() -> Unit,
        ): PrefixedCommands {
            return PrefixedCommands(application)
        }
    }

}

/**
 * Utility for easily registering prefixed commands.
 * Will install [PrefixedCommands] if the feature wasn't installed yet.
 */
@TriumphDsl
public fun <A : JdaApplication> A.prefixedCommands(configuration: PrefixedCommands.() -> Unit): PrefixedCommands =
    featureOrNull(PrefixedCommands)?.apply(configuration) ?: install(PrefixedCommands, configuration)