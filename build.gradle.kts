import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val javaVersion = "1.8"

plugins {
    kotlin("jvm") version "1.5.10"
    id("com.github.hierynomus.license") version "0.16.1"
    `maven-publish`
}

group = "me.mattstudios"
version = "2.0.0-SNAPSHOT"

allprojects {
    repositories {
        mavenCentral()
        mavenLocal()
        maven("https://repo.mattstudios.me/artifactory/public/")
    }
}

subprojects {

    apply {
        plugin("org.jetbrains.kotlin.jvm")
        plugin("maven-publish")
        plugin("com.github.hierynomus.license")
    }

    dependencies {
        // Remove this one later
        api("me.mattstudios.utils:matt-framework:1.4.6")
        api("me.mattstudios:triumph-config:1.0.5-SNAPSHOT")

        compileOnly(kotlin("stdlib"))

        // Testing
        testImplementation(kotlin("test-junit5"))
        testImplementation("org.assertj:assertj-core:3.11.1")
        testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
        testImplementation("org.junit.jupiter:junit-jupiter-engine:5.6.0")

    }

    val kotlinComponent: SoftwareComponent = components["kotlin"]

    license {
        header = rootProject.file("LICENSE")
        encoding = "UTF-8"
        mapping("kotlin", "JAVADOC_STYLE")
        include("**/*.kt")
    }

    tasks {

        test {
            useJUnitPlatform()
        }

        kotlin {
            explicitApi()
        }

        withType<KotlinCompile> {
            kotlinOptions {
                jvmTarget = javaVersion
                javaParameters = true
            }
        }

        val sourcesJar by creating(Jar::class) {
            archiveClassifier.set("sources")
            from(sourceSets.main.get().allSource)
        }

        val javadocJar by creating(Jar::class) {
            dependsOn.add(javadoc)
            archiveClassifier.set("javadoc")
            from(javadoc)
        }

        publishing {
            publications {
                create<MavenPublication>("maven") {
                    from(kotlinComponent)

                    artifact(sourcesJar)
                    artifact(javadocJar)

                    versionMapping {
                        usage("java-api") {
                            fromResolutionOf("runtimeClasspath")
                        }
                        usage("java-runtime") {
                            fromResolutionResult()
                        }
                    }
                    pom {
                        name.set("TriumphCore")
                        description.set("Adds utils and required classes to plugins")
                        url.set("https://github.com/TriumphTeam/core")

                        licenses {
                            license {
                                name.set("MIT License")
                                url.set("http://www.opensource.org/licenses/mit-license.php")
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
                            connection.set("scm:git:git://github.com/TriumphTeam/core.git")
                            developerConnection.set("scm:git:ssh://github.com:TriumphTeam/core.git")
                            url.set("http://github.com/TriumphTeam/core")
                        }
                    }
                }
            }

            repositories {
                maven {
                    credentials {
                        username = System.getenv("REPO_USER")
                        password = System.getenv("REPO_PASS")
                    }

                    url = uri("https://repo.mattstudios.me/artifactory/public")
                }
            }

        }

    }
}