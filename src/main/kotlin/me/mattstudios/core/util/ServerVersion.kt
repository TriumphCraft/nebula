package me.mattstudios.core.util

import org.bukkit.Bukkit

/**
 * @author Matt
 */
enum class ServerVersion(private val versionNumber: Int) {

    UNKNOWN(0),

    /**
     * Legacy versions
     */
    V1_8_R1(81),
    V1_8_R2(82),
    V1_8_R3(83),

    V1_9_R1(91),
    V1_9_R2(92),

    V1_10_R1(101),
    V1_11_R1(111),
    V1_12_R1(121),


    /**
     * New versions
     */
    V1_13_R1(131),
    V1_13_R2(132),

    V1_14_R1(141),
    V1_15_R1(151),

    V1_16_R1(161);

    val PACKAGE_NAME = Bukkit.getServer().javaClass.getPackage().name
    val NMS_VERSION = PACKAGE_NAME.substring(PACKAGE_NAME.lastIndexOf('.') + 1)
    val CURRENT_VERSION = getByNmsName(NMS_VERSION)

    /**
     * Checks if the current version is newer than the [ServerVersion] specified
     */
    fun isNewerThan(version: ServerVersion) = versionNumber > version.versionNumber

    /**
     * Checks if the current version is older than the [ServerVersion] specified
     */
    fun isOlderThan(version: ServerVersion) = versionNumber < version.versionNumber

    /**
     * Checks if the server is using a legacy version
     */
    fun isLegacy() = versionNumber < V1_13_R1.versionNumber

    /**
     * Gets a version from the NMS name
     */
    fun getByNmsName(name: String) = values().find { it.name == name } ?: UNKNOWN

}