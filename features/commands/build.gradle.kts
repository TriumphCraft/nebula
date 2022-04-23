plugins {
    id("core.base-conventions")
    id("core.bukkit-conventions")
    id("core.library-conventions")
}

dependencies {
    api(project(":nebula"))
    api(libs.cmd.core)
}