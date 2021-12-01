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
package dev.triumphteam.core

import dev.triumphteam.core.feature.attribute.HashMapAttributes
import dev.triumphteam.core.feature.attribute.attributesOf
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

/**
 * Main implementation for Bukkit.
 */
public abstract class BukkitPlugin<out P : TriumphApplication>(
    private val module: P.() -> Unit,
    private val common: TriumphApplication.() -> Unit = {}
) : JavaPlugin(), TriumphApplication {

    /**
     * Attributes holder for all the features.
     */
    public override val attributes: HashMapAttributes = attributesOf()
    public override val applicationFolder: File = dataFolder

    /**
     * Holders for multiple functions containing load, enable, and disable.
     * This is needed so the common module and the main module can be run together.
     */

    private var loadFunctions: MutableSet<() -> Unit> = mutableSetOf()
    private var enableFunctions: MutableSet<() -> Unit> = mutableSetOf()
    private var disableFunctions: MutableSet<() -> Unit> = mutableSetOf()

    init {
        runModules()
    }

    /**
     * Main Bukkit's load function, that'll run the load functions.
     */
    override fun onLoad() {
        loadFunctions.forEach { it() }
    }

    /**
     * Main Bukkit's enable function, that'll run the enable functions.
     */
    override fun onEnable() {
        enableFunctions.forEach { it() }
    }

    /**
     * Main Bukkit's disable function, that'll run the disable functions.
     */
    override fun onDisable() {
        disableFunctions.forEach { it() }
        attributes.clear()
    }

    /**
     * Load function from [TriumphApplication] to add a load function to the set.
     */
    public fun onLoad(load: () -> Unit) {
        loadFunctions.add(load)
    }

    /**
     * Enable function from [TriumphApplication] to add a enable function to the set.
     */
    public fun onEnable(enable: () -> Unit) {
        enableFunctions.add(enable)
    }

    /**
     * Disable function from [TriumphApplication] to add a disable function to the set.
     */
    public fun onDisable(disable: () -> Unit) {
        disableFunctions.add(disable)
    }

    /**
     * Not happy with the init and the unchecked cast, but hey.
     */
    @Suppress("UNCHECKED_CAST")
    private fun runModules() {
        common(this)
        module(this as P)
    }

}
