plugins {
    id("core.base-conventions")
    id("core.bukkit-conventions")
}

dependencies {
    api(project(":triumph-core-bukkit"))
    compileOnly(libs.spigot)
}