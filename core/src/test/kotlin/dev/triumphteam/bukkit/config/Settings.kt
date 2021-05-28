package dev.triumphteam.core.config

import me.mattstudios.config.SettingsHolder
import me.mattstudios.config.annotations.Path
import me.mattstudios.config.properties.Property

object Settings : SettingsHolder {

    @Path("test")
    val TEST_PROPERTY = Property.create("Default value")

}