plugins {
    kotlin("jvm") version "1.7.20"
}

val kotlinVersion: String by project
val kspVersion: String by project
val koinVersion: String by project
val koinKspVersion: String by project

allprojects {
    group = "com.kgit2"
    version = "1.0.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }

    ext {
        set("kotlinVersion", kotlinVersion)
        set("kspVersion", kspVersion)
        set("koinVersion", koinVersion)
        set("koinKspVersion", koinKspVersion)
    }

    tasks.whenTaskAdded {
        if (name.startsWith("ksp")) {
            logging.captureStandardError(LogLevel.ERROR)
            logging.captureStandardOutput(LogLevel.DEBUG)
            group = "ksp"
        }
    }
}
