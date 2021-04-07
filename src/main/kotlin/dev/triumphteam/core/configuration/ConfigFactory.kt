package dev.triumphteam.core.configuration

import dev.triumphteam.core.TriumphPlugin

interface ConfigFactory {

    fun create(plugin: TriumphPlugin): Config

}