plugins {
    id("nebula.base-conventions")
    id("nebula.library-conventions")
}

dependencies {
    api(project(":nebula"))
    api(libs.config)
}
