plugins {
    kotlin("jvm") version "1.7.20"
}

val kotlinVersion: String by project
val kspVersion: String by project

allprojects {
    group = "com.kgit2"
    version = "1.0.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }

    ext {
        set("kotlinVersion", kotlinVersion)
        set("kspVersion", kspVersion)
    }
}
