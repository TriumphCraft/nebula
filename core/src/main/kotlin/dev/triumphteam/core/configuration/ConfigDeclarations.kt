package dev.triumphteam.core.configuration

import dev.triumphteam.core.TriumphApplication
import dev.triumphteam.core.exception.DuplicateFeatureException

/**
 * Installs a feature into a [TriumphApplication]
 * @param A The application to use, be a plugin or not
 * @param C The configuration to be used by the installer
 * @param F The feature that'll be created
 */
public fun <A : TriumphApplication, C : Any> A.installConfig(
    config: ConfigFeature<A, C>,
    configure: C.() -> Unit = {}
) {
    if (configs[config.key] != null) {
        // TODO change
        throw DuplicateFeatureException(
            "Conflicting feature is already installed with the same key as `${config.key.name}`"
        )
    }

    configs[config.key] = config.install(this, configure)
}

public fun <A : TriumphApplication, C : Any> A.config(config: ConfigFeature<A, C>): BaseConfig {
    return attributes.getOrNull(config.key) ?: throw DuplicateFeatureException("")
}