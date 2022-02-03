plugins {
    id("core.base-conventions")
    id("core.bukkit-conventions")
}

dependencies {
    api(project(":triumph-core-bukkit"))
    api(libs.cmd.bukkit)
    compileOnly(libs.spigot)
}