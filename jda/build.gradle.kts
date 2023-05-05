plugins {
    id("nebula.base-conventions")
    id("nebula.library-conventions")
}

repositories {
    maven("https://m2.dv8tion.net/releases")
}

dependencies {
    api(projects.nebulaCore)
    api(libs.jda)
}
