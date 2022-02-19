package dev.triumphteam.core.func

import dev.triumphteam.core.BukkitApplication
import dev.triumphteam.core.feature.ApplicationFeature
import java.util.logging.Logger

public object BukkitLogger : ApplicationFeature<BukkitApplication, Logger, Logger> {
    override fun install(application: BukkitApplication, configure: Logger.() -> Unit): Logger {
        return application.logger
    }
}