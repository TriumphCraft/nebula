package dev.triumphteam.core.feature

import dev.triumphteam.core.TriumphApplication
import dev.triumphteam.core.exception.DuplicateFeatureException
import dev.triumphteam.core.exception.MissingFeatureException

private val featureRegistryKey = AttributeKey<Attributes>("ApplicationFeatureRegistry")

/**
 * Defines a installable feature
 * @param A The application, can be a plugin or anything
 * @param C A configuration, can be anything even [A]
 * @param F The [ApplicationFeature] you want to return
 */
public interface ApplicationFeature<
        in A : TriumphApplication,
        out C : Any,
        F : Any> {

    /**
     * Key that defines the specific feature
     */
    public val key: AttributeKey<F>

    /**
     * Feature installation, works like a factory
     * @param application The current application [A]
     * @param configure A configuration, that can be anything
     */
    public fun install(application: A, configure: C.() -> Unit): F
}

/**
 * Installs a feature into a [TriumphApplication]
 * @param A The application to use, be a plugin or not
 * @param C The configuration to be used by the installer
 * @param F The feature that'll be created
 */
public fun <A : TriumphApplication, C : Any, F : Any> A.install(
    feature: ApplicationFeature<A, C, F>,
    configure: C.() -> Unit = {}
): F {
    if (attributes.getOrNull(feature.key) != null) {
        throw DuplicateFeatureException(
            "Conflicting feature is already installed with the same key as `${feature.key.name}`"
        )
    }

    val installed = feature.install(this, configure)
    attributes.put(feature.key, installed)
    return installed
}

/**
 * Gets feature instance for this application, or fails with [MissingFeatureException] if the feature is not installed
 * @throws MissingFeatureException
 * @param feature application feature to lookup
 * @return an instance of feature
 */
public fun <A : TriumphApplication, C : Any, F : Any> A.feature(feature: ApplicationFeature<A, C, F>): F {
    return attributes.getOrNull(feature.key) ?: throw MissingFeatureException(feature.key)
}

/**
 * Returns feature instance for this application, or null if feature is not installed
 */
public fun <M : TriumphApplication, C : Any, F : Any> M.featureOrNull(feature: ApplicationFeature<M, C, F>): F? {
    return attributes.getOrNull(feature.key)
}