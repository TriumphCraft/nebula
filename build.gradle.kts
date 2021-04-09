val javaVersion = "1.8"

plugins {
    kotlin("jvm") version "1.4.32"
    id("com.github.johnrengelman.shadow") version "6.1.0"
    id("maven-publish")
}

group = "me.mattstudios"
version = "1.2.3"

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://repo.mattstudios.me/artifactory/public/")
}

dependencies {
    api("me.mattstudios.utils:matt-framework:1.4.6")
    api("me.mattstudios:triumph-config:1.0.5-SNAPSHOT")

    compileOnly("org.spigotmc:spigot-api:1.16.4-R0.1-SNAPSHOT")
    compileOnly("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // Not sure how to have these two as compileOnly and test at the same time
    // I'll figure it later kek
    testImplementation("org.spigotmc:spigot-api:1.16.4-R0.1-SNAPSHOT")

    // Testing
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.assertj:assertj-core:3.11.1")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.6.0")

}

val kotlinComponent: SoftwareComponent = components["kotlin"]

tasks {

    test {
        useJUnitPlatform()
    }

    withType<JavaCompile> {
        options.compilerArgs.add("-parameters")
        options.encoding = "UTF-8"
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }

    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
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