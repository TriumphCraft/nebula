import dev.triumphteam.root.baseLibs
import dev.triumphteam.root.includeProject
import dev.triumphteam.root.localLibs
import dev.triumphteam.root.releasesRepo

dependencyResolutionManagement {
    includeBuild("build-logic")
    repositories {
        releasesRepo()
        gradlePluginPortal()
    }

    versionCatalogs {
        baseLibs("0.0.3-kt-2.0.10")
    }
}

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://repo.triumphteam.dev/releases")
    }
}

rootProject.name = "nebula"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

plugins {
    id("dev.triumphteam.root.settings") version "0.0.9"
}

listOf("application", "core", "paper", "fabric", "utilities").forEach(::includeProject)
// listOf("scheduler", "commands").forEach(::includeModule)
