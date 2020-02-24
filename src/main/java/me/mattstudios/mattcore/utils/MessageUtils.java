package me.mattstudios.mattcore.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.List;
import java.util.stream.Collectors;

public final class MessageUtils {

    /**
     * Utility to use color codes easily
     *
     * @param message The message String
     * @return returns the string with color
     */
    public static String color(final String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    /**
     * Utility to use color codes easily
     *
     * @param messages The message String
     * @return returns the string with color
     */
    public static List<String> color(final List<String> messages) {
        return messages.stream().map(s -> ChatColor.translateAlternateColorCodes('&', s)).collect(Collectors.toList());
    }

    /**
     * Simplified way for sending console messages
     *
     * @param message the message to be sent to the console
     */
    public static void info(final String message) {
        Bukkit.getConsoleSender().sendMessage(color(message));
    }

    /**
     * Simplified way for sending console warn messages
     *
     * @param message the warn message to be sent to the console
     */
    public static void warn(final String message) {
        Bukkit.getLogger().warning(color(message));
    }

}
