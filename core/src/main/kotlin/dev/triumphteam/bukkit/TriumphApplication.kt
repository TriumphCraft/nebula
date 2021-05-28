package dev.triumphteam.core

import dev.triumphteam.core.feature.attribute.Attributes
import java.io.File

public interface TriumphApplication {

    public val attributes: Attributes

    public val applicationFolder: File

    public fun onLoad(load: () -> Unit)

    public fun onEnable(enable: () -> Unit)

    public fun onDisable(disable: () -> Unit)

}