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
package dev.triumphteam.jda

import dev.triumphteam.bukkit.TriumphApplication
import dev.triumphteam.bukkit.feature.attribute.Attributes
import dev.triumphteam.bukkit.feature.attribute.attributesOf
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.requests.GatewayIntent
import java.io.File

/**
 * Creates a [JdaApplication] using a specified module.
 * @param module The [TriumphApplication] module to be used.
 * @param token The discord token.
 * @param intents A [Collection] of [GatewayIntent] to pass to JDA.
 * @param builder A instance of [JDABuilder] so users can customize the JDA.
 */
public fun jda(
    module: JdaApplication.() -> Unit,
    token: String,
    intents: Collection<GatewayIntent> = emptyList(),
    applicationFolder: File = File(""),
    builder: JDABuilder.() -> Unit = {}
) {
    val jda = JDABuilder.create(token, intents).apply(builder).build()
    module(JdaApplication(jda, applicationFolder))
}

/**
 * Main JDA implementation of the [TriumphApplication].
 * @param jda A [JDA] instance.
 * @param applicationFolder A folder that is the default for the application, similar to bukkit's dataFolder.
 */
public class JdaApplication(
    public val jda: JDA,
    public override val applicationFolder: File
) : TriumphApplication {

    public override val attributes: Attributes = attributesOf()

}