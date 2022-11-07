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
    ":bitmask-processor",
    ":bitmask-library",
    ":bitmask-plugin",
    ":bitmask-test"
)

project(":bitmask-processor").projectDir = File("processor")
project(":bitmask-library").projectDir = File("library")
project(":bitmask-plugin").projectDir = File("plugin")
project(":bitmask-test").projectDir = File("test")
