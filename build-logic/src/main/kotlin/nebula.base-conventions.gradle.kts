import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `java-library`
    id("com.github.hierynomus.license")
    kotlin("jvm")
}

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://repo.triumphteam.dev/snapshots/")
}

dependencies {
    implementation(kotlin("stdlib"))
}

license  {
    header = rootProject.file("LICENSE")
    encoding = "UTF-8"
    mapping("kotlin", "JAVADOC_STYLE")
    mapping("java", "JAVADOC_STYLE")

    include("**/*.kt")
    include("**/*.java")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
    withSourcesJar()
    withJavadocJar()
}

tasks {
    kotlin {
        explicitApi()
    }

    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
            languageVersion = "1.6"
            javaParameters = true
            freeCompilerArgs = listOf("-Xcontext-receivers", "-Xskip-prerelease-check")
        }
    }
}