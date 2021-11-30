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

import dev.triumphteam.core.dsl.TriumphDsl
import dev.triumphteam.core.feature.ApplicationFeature
import dev.triumphteam.core.feature.attribute.AttributeKey
import dev.triumphteam.core.feature.attribute.key
import dev.triumphteam.core.feature.featureOrNull
import dev.triumphteam.core.feature.install
import dev.triumphteam.jda.JdaApplication
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.hooks.ListenerAdapter

public class Listeners(private val jda: JDA) {

    /**
     * Registers a listener.
     */
    public fun register(listener: ListenerAdapter) {
        jda.addEventListener(listener)
    }

    /**
     * Registers multiple listeners.
     */
    public fun register(vararg listeners: ListenerAdapter) {
        listeners.forEach(::register)
    }

    /**
     * Feature companion, which is a factory for the [Listeners].
     */
    public companion object Feature : ApplicationFeature<JdaApplication, Listeners, Listeners> {

        /**
         * The locale [AttributeKey].
         */
        public override val key: AttributeKey<Listeners> = key("jda-listeners")

        /**
         * Installation function to create a [Listeners] feature.
         */
        public override fun install(application: JdaApplication, configure: Listeners.() -> Unit): Listeners {
            return Listeners(application.jda)
        }
    }

}

/**
 * Utility for easily registering Listeners.
 * Will install [Listeners] if the feature wasn't installed yet.
 */
@TriumphDsl
public fun <A : JdaApplication> A.listeners(configuration: Listeners.() -> Unit): Listeners =
    featureOrNull(Listeners)?.apply(configuration) ?: install(Listeners, configuration)