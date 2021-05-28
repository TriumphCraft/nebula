@file:Suppress("unused")

package dev.triumphteam.bukkit.func

import dev.triumphteam.bukkit.BukkitPlugin
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitTask

/**
 * Tasks util for easier usage of Bukkit's tasks
 */
public object Task {

    private val plugin = JavaPlugin.getPlugin(BukkitPlugin::class.java)

    /**
     * Better syntax for running sync tasks
     */
    public fun queue(task: Runnable): BukkitTask = Bukkit.getScheduler().runTask(plugin, task)

    /**
     * Better syntax for running task async
     */
    public fun async(task: Runnable): BukkitTask = Bukkit.getScheduler().runTaskAsynchronously(plugin, task)

    /**
     * Better syntax for running task later
     */
    public fun later(delay: Long, task: Runnable): BukkitTask = Bukkit.getScheduler().runTaskLater(plugin, task, delay)

    /**
     * Better syntax for running task later async
     */
    public fun laterAsync(delay: Long, task: Runnable): BukkitTask =
        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, task, delay)

    /**
     * Better syntax for running task timers
     */
    public fun timer(period: Long, delay: Long = 0L, task: Runnable): BukkitTask =
        Bukkit.getScheduler().runTaskTimer(plugin, task, delay, period)

    /**
     * Better syntax for running task timers async
     */
    public fun asyncTimer(period: Long, delay: Long = 0L, task: Runnable): BukkitTask =
        Bukkit.getScheduler().runTaskTimerAsynchronously(
            plugin, task, delay, period
        )

}