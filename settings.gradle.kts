dependencyResolutionManagement {
    includeBuild("build-logic")
    repositories.gradlePluginPortal()
}

rootProject.name = "triumph-core"

include("core")
project(":core").name = rootProject.name

listOf("bukkit", "jda").forEach(::includeProject)
listOf("config", "locale", "scheduler", "commands").forEach(::includeFeature)
listOf("listeners").forEach { includePlatformFeature(it, "bukkit") }
listOf("listeners").forEach { includePlatformFeature(it, "jda") }

include("testing")

fun includeProject(name: String) {
    include(name) {
        this.name = "${rootProject.name}-$name"
    }
}

fun includeFeature(name: String) {
    include(name) {
        this.name = "${rootProject.name}-feature-$name"
        this.projectDir = file("features/$name")
    }
}

fun includePlatformFeature(name: String, platform: String) {
    include(name) {
        this.name = "${rootProject.name}-feature-$platform-$name"
        this.projectDir = file("features/$platform/$name")
    }
}

fun include(name: String, block: ProjectDescriptor.() -> Unit) {
    include(name)
    project(":$name").apply(block)
}

enableFeaturePreview("VERSION_CATALOGS")