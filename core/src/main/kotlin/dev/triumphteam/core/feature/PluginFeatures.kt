package dev.triumphteam.core.feature

import dev.triumphteam.core.TriumphModule

public interface Feature<
        in P : TriumphModule,
        out C : Any,
        F : Any> {

    public val key: AttributeKey<F>

    public fun install(plugin: P, configure: C.() -> Unit): F

}

public fun <P : TriumphModule, C : Any, F : Any> P.install(feature: Feature<P, C, F>, configure: C.() -> Unit = {}) {

}