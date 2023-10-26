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
import dev.triumphteam.nebula.container.inject
import dev.triumphteam.nebula.container.registry.GlobalInjectionRegistry
import dev.triumphteam.nebula.container.registry.InjectionRegistry
import dev.triumphteam.nebula.container.registry.bind
import dev.triumphteam.nebula.registerable.Registerable
import net.fabricmc.loader.api.FabricLoader
import java.io.File

public abstract class ModularMod : ModularApplication {
    /** Plugin uses the global registry. */
    public override val registry: InjectionRegistry = GlobalInjectionRegistry

    /** Plugin has no parent container. */
    public override val parent: Container? = null

    override val applicationFolder: File by lazy {
        fabricLoader.configDir.toFile()
    }

    /** Make fabric loader always available to mod implementations. */
    protected val fabricLoader: FabricLoader by inject()

    /** Function to be called when the plugin is starting (enable). */
    public override fun onStart() {}

    protected fun initialize() {
        // Binds the fabric loader
        registry.bind<FabricLoader>(FabricLoader.getInstance())
        // Calls main start block.
        onStart()
        // Registers all installed registerables.
        registry.instances.values
            .filterIsInstance<Registerable>()
            .filterNot(Registerable::isRegistered)
            .forEach(Registerable::register)
    }
}
