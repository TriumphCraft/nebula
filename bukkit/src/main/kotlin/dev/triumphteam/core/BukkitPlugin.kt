package dev.triumphteam.core

import dev.triumphteam.core.feature.HashMapAttributes
import org.bukkit.plugin.java.JavaPlugin

/**
 * Main implementation for Bukkit
 * @param P Implementation that extends [BukkitPlugin]
 * @param module The main module of the plugin (might add support for multiple)
 */
public abstract class BukkitPlugin<P : TriumphApplication>(
    private val module: P.() -> Unit,
    private val common: (TriumphApplication.() -> Unit)? = null
) : JavaPlugin(), TriumphApplication {

    override val attributes: HashMapAttributes = HashMapAttributes()

    private var load: () -> Unit = {}
    private var enable: () -> Unit = {}
    private var disable: () -> Unit = {}

    init {
        build()
    }

    override fun onLoad() {
        load()
    }

    override fun onEnable() {
        enable()
    }

    override fun onDisable() {
        disable()
    }

    public fun load(load: () -> Unit) {
        this.load = load
    }

    public fun enable(enable: () -> Unit) {
        this.enable = enable
    }

    public fun disable(disable: () -> Unit) {
        this.disable = disable
    }

    /**
     * Not happy with the init and the nasty unchecked cast
     */
    @Suppress("UNCHECKED_CAST")
    private fun build() {
        module(this as P)
        common?.let { it(this) }
    }

}
