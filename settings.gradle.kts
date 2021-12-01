rootProject.name = "triumph-core"

include("core")
project(":core").name = rootProject.name

listOf("bukkit", "jda").forEach(::includeProject)
listOf("config", "locale", "scheduler").forEach(::includeFeature)
//listOf("commands", "listeners").forEach { includePlatformFeature(it, "bukkit") }
listOf("commands", "listeners").forEach { includePlatformFeature(it, "jda") }

//include("testing")

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