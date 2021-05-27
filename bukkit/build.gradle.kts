repositories {
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
}

dependencies {
    api(project(":core"))

    compileOnly("org.spigotmc:spigot-api:1.16.4-R0.1-SNAPSHOT")

    // Not sure how to have these two as compileOnly and test at the same time
    // I'll figure it later kek
    testImplementation("org.spigotmc:spigot-api:1.16.4-R0.1-SNAPSHOT")

}