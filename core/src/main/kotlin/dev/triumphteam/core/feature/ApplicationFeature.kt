package dev.triumphteam.core.feature

import dev.triumphteam.core.TriumphApplication
import dev.triumphteam.core.feature.attribute.AttributeKey

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