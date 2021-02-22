package dev.triumphteam.core.func

import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

/**
 * Utils taken from AuthMe/ConfigMe
 */
fun copyFileFromResources(path: String, temporaryFolder: Path): Path {
    return try {
        val source = getJarPath(path)
        val destination: Path = temporaryFolder.resolve(source.fileName)
        Files.createFile(destination)
        Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING)
        destination
    } catch (e: IOException) {
        throw IllegalStateException("Could not copy test file", e)
    }
}

fun getJarPath(path: String): Path {
    println(object : Any() {}.javaClass.getResourceAsStream("config.yml"))
    val filePath = object : Any() {}.javaClass.getResource(path).path
    // Windows prepends the path with a '/' or '\', which Paths cannot handle
    val appropriatePath = if (System.getProperty("os.name").contains("indow")) filePath.substring(1) else filePath
    return Paths.get(appropriatePath)
}