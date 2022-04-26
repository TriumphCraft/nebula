plugins {
    id("nebula.base-conventions")
    id("nebula.bukkit-conventions")
    id("nebula.library-conventions")
}

dependencies {
    api(project(":nebula"))
    compileOnly(libs.spigot)
}