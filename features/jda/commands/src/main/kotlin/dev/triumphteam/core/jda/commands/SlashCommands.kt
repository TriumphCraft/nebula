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
import dev.triumphteam.cmd.slash.SlashCommandManager
import dev.triumphteam.cmd.slash.sender.SlashSender
import dev.triumphteam.core.dsl.TriumphDsl
import dev.triumphteam.core.feature.ApplicationFeature
import dev.triumphteam.core.feature.attribute.AttributeKey
import dev.triumphteam.core.feature.attribute.key
import dev.triumphteam.core.feature.featureOrNull
import dev.triumphteam.core.feature.install
import dev.triumphteam.core.jda.JdaApplication
import net.dv8tion.jda.api.entities.Guild

public class SlashCommands private constructor(application: JdaApplication) {

    private val commandManager = SlashCommandManager.createDefault(application.jda)

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
     * Registers a global command with role restrictions.
     */
    public fun register(command: BaseCommand, enabledRoles: List<Long>, disabledRoles: List<Long>) {
        commandManager.registerCommand(command, enabledRoles, disabledRoles)
    }

    /**
     * Registers a command for a specific guild with role restrictions.
     */
    public fun register(guild: Guild, command: BaseCommand, enabledRoles: List<Long>, disabledRoles: List<Long>) {
        commandManager.registerCommand(guild, command, enabledRoles, disabledRoles)
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
     * Registers global commands with role restrictions.
     */
    public fun register(
        guild: Guild,
        vararg commands: BaseCommand,
        enabledRoles: List<Long>,
        disabledRoles: List<Long>
    ) {
        commands.forEach { register(guild, it, enabledRoles, disabledRoles) }
    }

    /**
     * Registers commands for a specific guild with role restrictions.
     */
    public fun register(vararg commands: BaseCommand, enabledRoles: List<Long>, disabledRoles: List<Long>) {
        commands.forEach { register(it, enabledRoles, disabledRoles) }
    }


    /**
     * Register an argument type to be used in the commands.
     */
    public fun argument(type: Class<*>, resolver: ArgumentResolver<SlashSender>) {
        commandManager.registerArgument(type, resolver)
    }

    /**
     * Register a requirement type to be used in the commands.
     */
    public fun requirement(key: RequirementKey, resolver: RequirementResolver<SlashSender>) {
        commandManager.registerRequirement(key, resolver)
    }

    /**
     * Registers a command message.
     */
    public fun <C : MessageContext> message(key: MessageKey<C>, resolver: MessageResolver<SlashSender, C>) {
        commandManager.registerMessage(key, resolver)
    }

    /**
     * Feature companion, which is a factory for the [SlashCommands].
     */
    public companion object Feature : ApplicationFeature<JdaApplication, SlashCommands, SlashCommands> {

        /**
         * The slash commands [AttributeKey].
         */
        public override val key: AttributeKey<SlashCommands> = key("jda-slash-commands")

        /**
         * Installation function to create a [SlashCommands] feature.
         */
        public override fun install(
            application: JdaApplication,
            configure: SlashCommands.() -> Unit
        ): SlashCommands {
            return SlashCommands(application)
        }
    }

}

/**
 * Utility for easily registering commands.
 * Will install [SlashCommands] if the feature wasn't installed yet.
 */
@TriumphDsl
public fun <A : JdaApplication> A.commands(configuration: SlashCommands.() -> Unit): SlashCommands =
    featureOrNull(SlashCommands)?.apply(configuration) ?: install(SlashCommands, configuration)