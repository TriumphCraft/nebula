package dev.triumphteam.core

import dev.triumphteam.core.feature.attribute.HashMapAttributes
import dev.triumphteam.core.feature.attribute.attributesOf
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

    override val attributes: HashMapAttributes = attributesOf()
    override val applicationFolder: File = dataFolder

    private var loadFunctions: MutableList<() -> Unit> = mutableListOf()
    private var enableFunctions: MutableList<() -> Unit> = mutableListOf()
    private var disableFunctions: MutableList<() -> Unit> = mutableListOf()
    private val test: () -> Unit = {}

    init {
        build()
    }

    override fun onLoad() {
        loadFunctions.forEach { it() }
    }

    override fun onEnable() {
        enableFunctions.forEach { it() }
    }

    override fun onDisable() {
        enableFunctions.forEach { it() }
    }

    public override fun onLoad(load: () -> Unit) {
        loadFunctions.add(load)
    }

    public override fun onEnable(enable: () -> Unit) {
        enableFunctions.add(enable)
    }

    public override fun onDisable(disable: () -> Unit) {
        disableFunctions.add(disable)
    }

    /**
     * Not happy with the init and the nasty unchecked cast
     */
    @Suppress("UNCHECKED_CAST")
    private fun build() {
        common(this)
        module(this as P)
    }

}
