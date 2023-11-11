plugins {
    id("nebula.base")
    id("nebula.library")
}

dependencies {
    api(projects.nebulaCore)
    // TODO: IMPORT CORE INSTEAD OF JSON
    api(baseLibs.kotlin.serialization.json)
}
