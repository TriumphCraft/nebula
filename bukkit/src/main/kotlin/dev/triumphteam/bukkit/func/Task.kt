/**
 * MIT License
 *
 * Copyright (c) 2021 Mateus Moreira
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
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