plugins {
    id("nebula.base")
    id("nebula.library")
}

dependencies {
    api(projects.nebulaCore)
    compileOnly(libs.fabric.loader)
}

root {
    configureKotlin(17)
}
