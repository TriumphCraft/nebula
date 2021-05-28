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