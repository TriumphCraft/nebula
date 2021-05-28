rootProject.name = "triumph-core"

include("core")
project(":core").name = rootProject.name

listOf("bukkit", "jda").forEach(::includeProject)
listOf("config", "locale").forEach(::includeFeature)

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

fun include(name: String, block: ProjectDescriptor.() -> Unit) {
    include(name)
    project(":$name").apply(block)
}