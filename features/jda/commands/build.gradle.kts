plugins {
    id("core.base-conventions")
}

dependencies {
    api(project(":triumph-core-jda"))
    api(libs.cmd.slash)
    api(libs.cmd.prefixed)
}