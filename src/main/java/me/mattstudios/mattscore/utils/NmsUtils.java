package me.mattstudios.mattscore.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public final class NmsUtils {

    /**
     * Gets the NMS class from class name.
     *
     * @return The NMS class.
     */
    public static Class<?> getNMSClass() {
        try {
            return Class.forName("net.minecraft.server." + getServerVersion() + "." + "Packet");
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    /**
     * Gets the server version.
     *
     * @return The string with the server version.
     */
    public static String getServerVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().substring(Bukkit.getServer().getClass().getPackage().getName().lastIndexOf('.') + 1);
    }

    /**
     * Send packets to a player.
     *
     * @param player The player to receive the packet.
     * @param packet The packet to be sent.
     */
    public static void sendPacket(Player player, Object packet) {
        try {
            Object handler = player.getClass().getMethod("getHandle").invoke(player);
            Object playerConnection = handler.getClass().getField("playerConnection").get(handler);
            playerConnection.getClass().getMethod("sendPacket", getNMSClass()).invoke(playerConnection, packet);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the NMS craft class from class name.
     *
     * @param className The class name.
     * @return The NMS craft class.
     */
    public static Class<?> getNMSCraftClass(String className) {
        try {
            return Class.forName("org.bukkit.craftbukkit." + getServerVersion() + "." + className);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

}
