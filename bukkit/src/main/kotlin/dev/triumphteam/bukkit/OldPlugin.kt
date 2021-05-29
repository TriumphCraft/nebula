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
package dev.triumphteam.bukkit

import me.mattstudios.mf.base.CommandManager
import me.mattstudios.mf.base.components.MessageResolver
import org.bukkit.plugin.java.JavaPlugin
import java.util.Locale

/**
 * Adds many pre-made functionalities to make life easier
 */
@Suppress("MemberVisibilityCanBePrivate", "unused")
public abstract class OldPlugin : JavaPlugin() {

    // Config map for saving all the configs used by the main plugin
    // Unfortunately it's required to be public due to the reified inline function for the getter
    //public val configs: MutableMap<KClass<out BaseConfig>, BaseConfig> = mutableMapOf()

    // Command manager from MF
    internal lateinit var commandManager: CommandManager
        private set

    // Locale object for message handling
    public lateinit var locale: Locale
        private set

    /**
     * Main on load function
     */
    protected open fun load() {}

    /**
     * Main on enable function
     */
    protected open fun enable() {}

    /**
     * Main on disable function
     */
    protected open fun disable() {}

    /**
     * Calls [JavaPlugin]'s onLoad and calls [load], does nothing more, exists so the functions can be named similarly
     */
    override fun onLoad() {
        load()
    }

    /**
     * Calls [JavaPlugin]'s onEnable, initialize some values and call the main [enable]
     */
    override fun onEnable() {
        commandManager = CommandManager(this, true)

        // Calls the plugin enable
        enable()
    }

    /**
     * Calls [JavaPlugin]'s onDisable and calls [disable], does nothing more, exists so the functions can be named similarly
     */
    override fun onDisable() {
        disable()
    }

    /**
     * Gets a config that has been registered before
     */
    /*public inline fun <reified T : BaseConfig> config(): T {
        return (configs[T::class] ?: throw UnregisteredConfigException(T::class)) as T
    }*/

    /**
     * Creates a [CommandContext] and applies the logic from enable
     */
    /*inline fun <T : OldPlugin> T.commands(context: CommandContext<T>.() -> Unit) {
        CommandContext(this).apply(context)
    }*/

    /**
     * Simple initializer for doing the above but not clog up the main class
     */
    /*protected fun <T : OldPlugin> T.initialize(initializer: Initializer<T>) {
        initializer.initialize(this)
    }*/

    /**
     * Util for doing enable checks, like dependency checks and things like that
     */
    /*protected fun <T : OldPlugin> T.check(checker: Checker<T>): Boolean {
        return checker.check(this)
    }*/


    /**
     * Creates a [ListenerContext] and applies the logic from enable
     */
    /*inline fun <T : OldPlugin> T.listeners(context: ListenerContext<T>.() -> Unit) {
        ListenerContext(this).apply(context)
    }*/

    private fun registerMessages(vararg pairs: Pair<String, MessageResolver>) =
        pairs.forEach { pair -> commandManager.messageHandler.register(pair.first, pair.second) }

}