plugins {
    kotlin("jvm")
    id("dev.triumphteam.root")
    id("com.diffplug.spotless")
    id("io.gitlab.arturbosch.detekt")
    id("com.github.hierynomus.license")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
}

license {
    header = rootProject.file("LICENSE")
    encoding = "UTF-8"
    mapping("kotlin", "JAVADOC_STYLE")
    mapping("java", "JAVADOC_STYLE")

    include("**/*.kt")
    include("**/*.java")
}

root {
    configureKotlin(8)
    configureSpotless()
}
