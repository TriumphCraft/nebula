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
package dev.triumphteam.nebula

import dev.triumphteam.nebula.container.Container
import dev.triumphteam.nebula.container.registry.GlobalInjectionRegistry
import dev.triumphteam.nebula.container.registry.InjectionRegistry
import dev.triumphteam.nebula.registerable.Registerable
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

/** Main implementation for Bukkit plugins. */
public abstract class ModularPlugin :
    JavaPlugin(),
    ModularApplication,
    ModularApplication.SetupStage,
    ModularApplication.StopStage {

    /** Plugin uses the global registry. */
    public override val registry: InjectionRegistry = GlobalInjectionRegistry

    /** Scope key, the highest scope in the application. */
    public override val key: String = "plugin"

    /** Plugin has no parent container. */
    public override val parent: Container? = null

    public override val applicationFolder: File = dataFolder

    public override fun onLoad() {
        onSetup()
    }

    public override fun onEnable() {
        // Makes plugin instance injectable.
        bind<Plugin>()
        // Calls main start block.
        onStart()
        // Registers all installed registerables.
        registry.instances.values
            .filterIsInstance<Registerable>()
            .filterNot(Registerable::isRegistered)
            .forEach(Registerable::register)
    }

    public override fun onDisable() {
        // Calls main stop block.
        onStop()
        // Unregisters all installed registerables.
        registry.instances.values.filterIsInstance<Registerable>()
            .filter(Registerable::isRegistered)
            .forEach(Registerable::unregister)
    }

    public override fun onSetup() {}

    /** Function to be called when the plugin is starting (enable). */
    public override fun onStart() {}

    /** Function to be called when the plugin is stopping (disable). */
    public override fun onStop() {}
}
