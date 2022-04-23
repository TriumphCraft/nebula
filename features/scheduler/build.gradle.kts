plugins {
    id("core.base-conventions")
    id("core.library-conventions")
}

dependencies {
    api(project(":nebula"))
    api(libs.coroutines)
}