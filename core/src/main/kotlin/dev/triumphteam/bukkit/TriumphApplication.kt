package dev.triumphteam.bukkit

import dev.triumphteam.bukkit.feature.attribute.Attributes
import java.io.File

/**
 * An application, this represents any type of application for any platform.
 */
public interface TriumphApplication {

    /**
     * Main attributes for all features.
     */
    public val attributes: Attributes

    /**
     * A folder where the application wants to store data, similar to Bukkit's "dataFolder".
     */
    public val applicationFolder: File

    /**
     * Load function, that should be ran when the application is loading.
     * Depending on the platform this won't be needed, or might be called different.
     * @param load Function to be executed when the application loads.
     */
    public fun onLoad(load: () -> Unit) {}

    /**
     * Enable function, that should be ran when the application is done enabling.
     * Depending on the platform this won't be needed, or might be called different.
     * @param enable Function to be executed when the application is enabled.
     */
    public fun onEnable(enable: () -> Unit) {}

    /**
     * Disable function, that should be ran when the application is disabling.
     * Depending on the platform this won't be needed, or might be called different.
     * @param disable Function to be executed when the application is disabled.
     */
    public fun onDisable(disable: () -> Unit) {}

}