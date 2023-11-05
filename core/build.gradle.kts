plugins {
    id("nebula.base")
    id("nebula.library")
}

dependencies {
    api(libs.slf4j)
    implementation("io.insert-koin:koin-core:3.5.0")
}
