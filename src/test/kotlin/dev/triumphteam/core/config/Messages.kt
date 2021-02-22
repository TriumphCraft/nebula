package dev.triumphteam.core.config

import me.mattstudios.config.SettingsHolder
import me.mattstudios.config.annotations.Path
import me.mattstudios.config.properties.Property
import me.mattstudios.config.properties.types.PropertyType

object Messages : SettingsHolder {

    @Path("message-test")
    val MESSAGE_TEST = Property.create(PropertyType.STRING) { Defaults.MESSAGE_TEST.getDefaultMessage() }

}