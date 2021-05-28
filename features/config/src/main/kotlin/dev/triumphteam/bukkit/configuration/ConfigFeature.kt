package dev.triumphteam.core.configuration

import dev.triumphteam.core.TriumphApplication
import dev.triumphteam.core.feature.ApplicationFeature

/**
 * Simple config factory for using with companion objects
 */
public interface ConfigFeature<in A : TriumphApplication, out C : Any, F : BaseConfig> : ApplicationFeature<A, C, F>