plugins {
    id("core.base-conventions")
}

repositories {
    maven("https://m2.dv8tion.net/releases")
}

dependencies {
    api(project(":triumph-core"))
    api(libs.jda)
}