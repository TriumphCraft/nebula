plugins {
    `java-library`
    `maven-publish`
}

tasks {
    publishing {
        publications {
            create<MavenPublication>("maven") {
                from(components["java"])

                versionMapping {
                    usage("java-api") {
                        fromResolutionOf("runtimeClasspath")
                    }
                    usage("java-runtime") {
                        fromResolutionResult()
                    }
                }
                pom {
                    name.set("triumph-core")
                    description.set("Core library and features for Triumph plugins.")
                    url.set("https://github.com/TriumphTeam/triumph-core")

                    licenses {
                        license {
                            name.set("MIT License")
                            url.set("https://www.opensource.org/licenses/mit-license.php")
                        }
                    }

                    developers {
                        developer {
                            id.set("matt")
                            name.set("Mateus Moreira")
                        }
                    }

                    // Change later
                    scm {
                        connection.set("scm:git:git://github.com/TriumphTeam/triumph-core.git")
                        developerConnection.set("scm:git:ssh://github.com:TriumphTeam/triumph-core.git")
                        url.set("http://github.com/TriumphTeam/triumph-core")
                    }
                }
            }
        }

        repositories {
            maven {
                url = if (version.toString().endsWith("-SNAPSHOT")) {
                    uri("https://repo.triumphteam.dev/snapshots/")
                } else {
                    uri("https://repo.triumphteam.dev/releases/")
                }

                credentials {
                    username = System.getenv("REPO_USER")
                    password = System.getenv("REPO_PASS")
                }
            }
        }
    }
}