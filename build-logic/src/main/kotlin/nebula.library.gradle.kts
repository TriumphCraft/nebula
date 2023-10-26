import gradle.kotlin.dsl.accessors._c3b5a8584877c7599cd6fe5cb3cf4f8d.kotlinSourcesJar

plugins {
    `java-library`
    `maven-publish`
}

tasks {
    publishing {
        publications {
            create<MavenPublication>("maven") {
                from(components["java"])
                
                artifact(kotlinSourcesJar)

                pom {
                    name.set("nebula")
                    description.set("Core library and modules for Triumph plugins.")
                    url.set("https://github.com/TriumphTeam/nebula")

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
                        connection.set("scm:git:git://github.com/TriumphTeam/nebula.git")
                        developerConnection.set("scm:git:ssh://github.com:TriumphTeam/nebula.git")
                        url.set("http://github.com/TriumphTeam/nebula")
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
                    username = providers.gradleProperty("triumph.repo.user").orNull ?: System.getenv("GITHUB_ACTOR")
                    password = providers.gradleProperty("triumph.repo.token").orNull ?: System.getenv("GITHUB_TOKEN")
                }
            }
        }
    }
}
