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

import dev.triumphteam.nebula.provider.LoggerProvider
import dev.triumphteam.nebula.provider.providers
import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory

/**
 * An abstract class representing a common entry point for a modular Minecraft mod.
 * It extends the ModularMod class and implements the ModInitializer interface.
 *
 * This class provides a blueprint for initializing the mod by calling the initialize() method.
 *
 * @see ModularMod
 * @see ModInitializer
 */
public abstract class CommonEntryPoint(modId: String) : ModularMod(modId), ModInitializer {

    override fun onInitialize() {
        // Installs the logger provider
        providers {
            install(LoggerProvider) {
                default(LoggerFactory.getLogger(modId))
                key { modId }
            }
        }

        // Call initialize block
        initialize()
    }
}
