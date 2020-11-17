package me.mattstudios.core.func

import org.bukkit.Bukkit
import org.bukkit.ChatColor

/**
 * Utility to use color codes easily
 */
fun String.color() = ChatColor.translateAlternateColorCodes('&', this)

/**
 * Utility to use color codes easily
 */
fun List<String>.color() = map { string: String -> ChatColor.translateAlternateColorCodes('&', string) }

/**
 * Simplified way for sending console messages
 */
fun String.log() = Bukkit.getConsoleSender().sendMessage(color())