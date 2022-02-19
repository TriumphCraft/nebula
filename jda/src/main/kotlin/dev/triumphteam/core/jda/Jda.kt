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
package dev.triumphteam.core.jda

import dev.triumphteam.core.TriumphApplication
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.events.ReadyEvent
import net.dv8tion.jda.api.events.guild.GuildReadyEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.requests.GatewayIntent
import java.io.File

/**
 * Main JDA implementation of the [TriumphApplication].
 */
public abstract class JdaApplication(
    private val token: String,
    private val intents: Collection<GatewayIntent> = emptyList(),
    public override val applicationFolder: File = File("data"),
    private val extra: TriumphApplication.() -> Unit = {},
) : TriumphApplication {

    /**
     * Gets the [JDA] instance.
     */
    public val jda: JDA = createJda()

    init {
        startApplication()
    }

    public override fun onStart() {}

    public override fun onStop() {}

    /**
     * Runs when [JDA] is ready.
     */
    public open fun onReady() {}

    /**
     * Runs when a [Guild] is ready.
     */
    public open fun onGuildReady(guild: Guild) {}

    private fun createJda(): JDA {
        return JDABuilder.createDefault(token, intents)
            .addEventListeners(JdaApplicationListener(this, extra))
            .build()
    }

    private fun startApplication() {
        onStart()
    }
}

/**
 * Listener for ready events.
 */
private class JdaApplicationListener(
    private val jdaApplication: JdaApplication,
    private val module: TriumphApplication.() -> Unit,
) : ListenerAdapter() {

    override fun onReady(event: ReadyEvent) {
        module(jdaApplication)
        jdaApplication.onReady()
    }

    override fun onGuildReady(event: GuildReadyEvent) {
        jdaApplication.onGuildReady(event.guild)
    }

}