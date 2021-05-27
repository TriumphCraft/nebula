rootProject.name = "triumph-core"

include("core")
findProject("core")?.name = "triumph-core"

listOf("bukkit").forEach {
    include(it)
    findProject(it)?.name = "triumph-core-$it"
}