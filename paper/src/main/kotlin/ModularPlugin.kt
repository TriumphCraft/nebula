/**
 * MIT License
 *
 * Copyright (c) 2021-2023 TriumphTeam
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
import dev.triumphteam.nebula.core.annotation.NebulaInternalApi
import dev.triumphteam.nebula.module.modules
import dev.triumphteam.nebula.provider.LoggerProvider
import dev.triumphteam.nebula.provider.providers
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin
import java.nio.file.Path

/** Main implementation for Paper plugins. */
@OptIn(NebulaInternalApi::class)
public abstract class ModularPlugin :
    JavaPlugin(),
    ModularApplication,
    ModularApplication.SetupStage,
    ModularApplication.StopStage {

    /** Plugin uses the global registry. */
    public override val registry: InjectionRegistry = GlobalInjectionRegistry

    /** Plugin has no parent container. */
    public override var parent: Container? = null

    public override val applicationFolder: Path = dataFolder.toPath()

    public override fun onLoad() {
        onSetup()
    }

    public override fun onEnable() {
        // Adds logger as injectable provider.
        providers {
            install(LoggerProvider) {
                default(slF4JLogger)
                key(Container::key)
            }
        }

        // Makes plugin instance injectable.
        modules {
            installDirect<Plugin>(this@ModularPlugin)
        }

        // Calls the main start block.
        onStart()
        // Registers all installed registrables.
        registry.registerAll()
    }

    public override fun onDisable() {
        // Calls the main stop block.
        onStop()
        // Unregisters all installed registrables.
        registry.unregisterAll()
    }

    /** Function to be called when the plugin is loading (load). */
    public override fun onSetup() {}

    /** Function to be called when the plugin is starting (enable). */
    public override fun onStart() {}

    /** Function to be called when the plugin is stopping (disable). */
    public override fun onStop() {}
}
