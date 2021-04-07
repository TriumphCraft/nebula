package dev.triumphteam.core.func

import org.bukkit.Bukkit

/**
 * Simplified way for sending console messages
 */
fun String.log() = Bukkit.getConsoleSender().sendMessage(this)