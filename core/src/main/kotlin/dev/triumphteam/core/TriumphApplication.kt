package dev.triumphteam.core

import dev.triumphteam.core.configuration.BaseConfig
import dev.triumphteam.core.feature.attribute.AttributeKey
import dev.triumphteam.core.feature.attribute.Attributes
import java.io.File

public interface TriumphApplication {

    public val attributes: Attributes

    public val configs: MutableMap<AttributeKey<*>, BaseConfig>

    public val applicationFolder: File

    public fun onLoad(load: () -> Unit)

    public fun onEnable(enable: () -> Unit)

    public fun onDisable(disable: () -> Unit)

}