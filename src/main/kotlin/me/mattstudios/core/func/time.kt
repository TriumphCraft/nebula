package me.mattstudios.core.func

import java.util.concurrent.TimeUnit

/**
 * Gets the difference in seconds between times
 */
fun Long.getSecondsDifference() = TimeUnit.SECONDS.convert(System.currentTimeMillis() - this, TimeUnit.MILLISECONDS)