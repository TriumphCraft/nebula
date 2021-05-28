package dev.triumphteam.bukkit

import dev.triumphteam.bukkit.feature.attribute.HashMapAttributes
import dev.triumphteam.bukkit.feature.attribute.attributesOf
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

/**
 * Main implementation for Bukkit
 * @param P Implementation that extends [BukkitPlugin]
 * @param module The main module of the plugin (might add support for multiple)
 */
public abstract class BukkitPlugin<P : TriumphApplication>(
    private val module: P.() -> Unit,
    private val common: TriumphApplication.() -> Unit = {}
) : JavaPlugin(), TriumphApplication {

    /**
     * Attributes holder for all the features
     */
    public override val attributes: HashMapAttributes = attributesOf()
    public override val applicationFolder: File = dataFolder

    /**
     * Holders for multiple functions containing load, enable, and disable
     * This is needed so the common module and the main module can be ran together
     */

    private var loadFunctions: MutableSet<() -> Unit> = mutableSetOf()
    private var enableFunctions: MutableSet<() -> Unit> = mutableSetOf()
    private var disableFunctions: MutableSet<() -> Unit> = mutableSetOf()

    init {
        runModules()
    }

    /**
     * Main Bukkit's load function, that'll run the load functions
     */
    override fun onLoad() {
        loadFunctions.forEach { it() }
    }

    /**
     * Main Bukkit's enable function, that'll run the enable functions
     */
    override fun onEnable() {
        enableFunctions.forEach { it() }
    }

    /**
     * Main Bukkit's disable function, that'll run the disable functions
     */
    override fun onDisable() {
        enableFunctions.forEach { it() }
    }

    /**
     * Load function from [TriumphApplication] to add a load function to the set
     * @param load Function to be executed when [onLoad] is called
     */
    public override fun onLoad(load: () -> Unit) {
        loadFunctions.add(load)
    }

    /**
     * Enable function from [TriumphApplication] to add a enable function to the set
     * @param enable Function to be executed when [onEnable] is called
     */
    public override fun onEnable(enable: () -> Unit) {
        enableFunctions.add(enable)
    }

    /**
     * Disable function from [TriumphApplication] to add a disable function to the set
     * @param disable Function to be executed when [onDisable] is called
     */
    public override fun onDisable(disable: () -> Unit) {
        disableFunctions.add(disable)
    }

    /**
     * Not happy with the init and the unchecked cast, but hey
     */
    @Suppress("UNCHECKED_CAST")
    private fun runModules() {
        common(this)
        module(this as P)
    }

}
