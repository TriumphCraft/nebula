package me.mattstudios.mattscore.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public final class MessageUtils {

    /**
     * Utility to use color codes easily
     *
     * @param message The message String
     * @return returns the string with color
     */
    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    /**
     * Simplified way for sending console messages
     *
     * @param message the message to be sent to the console
     */
    public static void info(String message) {
        Bukkit.getServer().getConsoleSender().sendMessage(message);
    }

}
