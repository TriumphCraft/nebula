plugins {
    id("nebula.base-conventions")
    id("nebula.bukkit-conventions")
    id("nebula.library-conventions")
}

dependencies {
    api(projects.nebulaCore)
    api(libs.cmd.core)
}
