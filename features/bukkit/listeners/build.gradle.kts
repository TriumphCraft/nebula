plugins {
    id("core.base-conventions")
    id("core.bukkit-conventions")
    id("core.library-conventions")
}

dependencies {
    api(project(":triumph-core-bukkit"))
    compileOnly(libs.spigot)
}