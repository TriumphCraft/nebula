plugins {
    id("nebula.base")
    id("nebula.library")
}

dependencies {
    api(projects.nebulaCore)
    api(libs.kotlin.serialization.core)
}
