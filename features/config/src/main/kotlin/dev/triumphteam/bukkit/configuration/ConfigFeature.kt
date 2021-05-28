package dev.triumphteam.bukkit.configuration

import dev.triumphteam.bukkit.TriumphApplication
import dev.triumphteam.bukkit.feature.ApplicationFeature

/**
 * Simple config factory for using with companion objects.
 * @param A The application, can be a plugin or anything.
 * @param C A configuration, can be anything even [F].
 * @param F The [BaseConfig] you want to return.
 */
public interface ConfigFeature<in A : TriumphApplication, out C : Any, F : BaseConfig> : ApplicationFeature<A, C, F>