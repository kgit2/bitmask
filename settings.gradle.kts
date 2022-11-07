pluginManagement {
    val kotlinVersion: String by settings
    val kspVersion: String by settings
    plugins {
        kotlin("multiplatform") version kotlinVersion apply false
        id("com.google.devtools.ksp") version kspVersion apply false
    }

    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "bitmask"

include(
    ":processor",
    ":library",
    ":plugin",
    ":test"
)
