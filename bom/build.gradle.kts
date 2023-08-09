@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidLibrary)
}

dependencies {
    constraints {
        api(project(":common"))
        api(project(":code-tools"))
        api(project(":compose-common"))
        api(project(":file-management"))
        api(project(":git"))
        api(project(":network"))
        api(project(":resources"))
    }
}