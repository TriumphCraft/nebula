plugins {
    id("core.base-conventions")
    id("core.library-conventions")
}

repositories {
    maven("https://m2.dv8tion.net/releases")
}

dependencies {
    api(project(":nebula"))
    api(libs.jda)
}