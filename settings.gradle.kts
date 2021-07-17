rootProject.name = "triumph-core"

include("core")
project(":core").name = rootProject.name

listOf("bukkit", "jda").forEach(::includeProject)
listOf("config", "locale", "commands").forEach(::includeFeature)
listOf("commands", "listeners").forEach { includePlatformFeature(it, "bukkit") }

include("testing")

fun includeProject(name: String) {
    include(name) {
        this.name = "${rootProject.name}-$name"
    }
}

fun includeFeature(name: String) {
    include(name) {
        this.name = "${rootProject.name}-feature-$name"
        this.projectDir = file("features/common/$name")
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