dependencyResolutionManagement {
    includeBuild("build-logic")
    repositories.gradlePluginPortal()
}

rootProject.name = "nebula"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

listOf("core", "bukkit", "jda").forEach(::includeProject)
listOf("scheduler", "commands", "config").forEach(::includeModule)
// listOf("commands").forEach { includePlatformModule(it, "bukkit") }
// listOf("listeners").forEach { includePlatformModule(it, "jda") }

include("test-module")

fun includeProject(name: String) {
    include(name) {
        this.name = "${rootProject.name}-$name"
    }
}

fun includeModule(name: String) {
    include(name) {
        this.name = "${rootProject.name}-module-$name"
        this.projectDir = file("modules/$name")
    }
}

fun includePlatformModule(name: String, platform: String) {
    include(name) {
        this.name = "${rootProject.name}-module-$platform-$name"
        this.projectDir = file("modules/$platform/$name")
    }
}

fun include(name: String, block: ProjectDescriptor.() -> Unit) {
    include(name)
    project(":$name").apply(block)
}
