import dev.triumphteam.root.root

plugins {
    `kotlin-dsl`
    id("dev.triumphteam.root.logic") version "0.0.1"
}

dependencies {
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))

    implementation(baseLibs.bundles.kotlin.all)
    implementation("gradle.plugin.com.hierynomus.gradle.plugins:license-gradle-plugin:0.16.1")

    root("0.0.1")
}
