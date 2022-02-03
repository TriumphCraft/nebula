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
package dev.triumphteam.bukkit.commands

import dev.triumphteam.cmd.bukkit.BukkitCommandManager
import dev.triumphteam.cmd.core.BaseCommand
import dev.triumphteam.core.BukkitPlugin
import dev.triumphteam.core.dsl.TriumphDsl
import dev.triumphteam.core.feature.ApplicationFeature
import dev.triumphteam.core.feature.attribute.AttributeKey
import dev.triumphteam.core.feature.attribute.key
import dev.triumphteam.core.feature.featureOrNull
import dev.triumphteam.core.feature.install

public class Commands(plugin: BukkitPlugin) {

    private val commandManager = BukkitCommandManager.create(plugin)

    private fun register(command: BaseCommand) {
        //commandManager.register(command)
    }

    public fun register(vararg commands: BaseCommand) {
        commands.forEach(::register)
    }

    /*public fun completion(id: String, resolver: SuggestionResolver<CommandSender>) {
        //commandManager.completionHandler.register(id, resolver)
    }

    /**
     * Register a parameter type to be used in the commands
     */
    public fun parameter(type: Class<*>, resolver: ParameterResolver) {
       // commandManager.parameterHandler.register(type, resolver)
    }*/

    /**
     * Registers a command message
     */
    /*public fun message(id: String, resolver: MessageResolver) {
        //commandManager.messageHandler.register(id, resolver)
    }*/

    /**
     * TODO
     * Simple initializer for doing the above but not clog up the main class
     */
    /*public fun initialize(initializer: Initializer<T>) {
        initializer.initialize(plugin)
    }*/

    /**
     * Feature companion, which is a factory for the [Commands].
     */
    public companion object Feature : ApplicationFeature<BukkitPlugin, Commands, Commands> {

        /**
         * The locale [AttributeKey].
         */
        public override val key: AttributeKey<Commands> = key("Commands")

        /**
         * Installation function to create a [Commands] feature.
         */
        public override fun install(application: BukkitPlugin, configure: Commands.() -> Unit): Commands {
            return Commands(application)
        }
    }

}

@TriumphDsl
public fun <P : BukkitPlugin> P.commands(configuration: Commands.() -> Unit): Commands =
    featureOrNull(Commands)?.apply(configuration) ?: install(Commands, configuration)