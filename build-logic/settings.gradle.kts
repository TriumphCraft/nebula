import dev.triumphteam.root.baseLibs
import dev.triumphteam.root.localLibs
import dev.triumphteam.root.releasesRepo

rootProject.name = "build-logic"

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        gradlePluginPortal()
        releasesRepo()
    }

    versionCatalogs {
        create("libs") {
            from(files(localLibs))
        }

        baseLibs("0.0.2-063a785")
    }
}

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://repo.triumphteam.dev/releases")
    }
}

plugins {
    id("dev.triumphteam.root.settings") version "0.0.2"
}
