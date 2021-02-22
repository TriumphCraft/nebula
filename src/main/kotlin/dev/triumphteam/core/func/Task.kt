@file:Suppress("unused")

package dev.triumphteam.core.func

import dev.triumphteam.core.TriumphPlugin
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

/**
 * Tasks util for easier usage of Bukkit's tasks
 */
object Task {

    private val plugin = JavaPlugin.getPlugin(TriumphPlugin::class.java)

    /**
     * Better syntax for running sync tasks
     */
    fun queue(task: Runnable) = Bukkit.getScheduler().runTask(plugin, task)

    /**
     * Better syntax for running task async
     */
    fun async(task: Runnable) = Bukkit.getScheduler().runTaskAsynchronously(plugin, task)

    /**
     * Better syntax for running task later
     */
    fun later(delay: Long, task: Runnable) = Bukkit.getScheduler().runTaskLater(plugin, task, delay)

    /**
     * Better syntax for running task later async
     */
    fun laterAsync(delay: Long, task: Runnable) = Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, task, delay)

    /**
     * Better syntax for running task timers
     */
    fun timer(period: Long, delay: Long = 0L, task: Runnable) = Bukkit.getScheduler().runTaskTimer(plugin, task, delay, period)

    /**
     * Better syntax for running task timers async
     */
    fun asyncTimer(period: Long, delay: Long = 0L, task: Runnable) = Bukkit.getScheduler().runTaskTimerAsynchronously(
        plugin, task, delay, period)

}