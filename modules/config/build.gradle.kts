plugins {
    id("core.base-conventions")
    id("core.library-conventions")
}

dependencies {
    api(project(":triumph-core"))
    api(libs.config)
}