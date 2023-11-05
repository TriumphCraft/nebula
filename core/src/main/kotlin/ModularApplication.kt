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
import dev.triumphteam.nebula.core.annotation.NebulaInternalApi
import java.io.File

/** An application, this represents any type of application for any platform. */
public interface ModularApplication : Container {

    /** A folder where the application wants to store data, similar to Bukkit's "dataFolder". */
    public val applicationFolder: File

    /** Function to be called when the application starts. */
    public fun onStart()

    public interface SetupStage {

        /**
         * Performs the setup for the application.
         * This method is responsible for configuring the application and preparing it for use.
         */
        public fun onSetup()
    }

    public interface StopStage {

        /** Function to be called when the application stops. */
        public fun onStop()
    }
}

/** Binds the instance of the current modular application to the registry. */
@OptIn(NebulaInternalApi::class)
public inline fun <reified T : Any> ModularApplication.bind() {
    registry.put(T::class.java, this)
}
