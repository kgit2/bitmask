val kotlinVersion: String by ext
val kspVersion: String by ext
val koinVersion: String by ext
val koinKspVersion: String by ext

plugins {
    kotlin("multiplatform")
    id("com.google.devtools.ksp")
}

kotlin {
    jvm()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":bitmask-library"))
                implementation("io.insert-koin:koin-core:$koinVersion")
                implementation("io.insert-koin:koin-annotations:$koinKspVersion")
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation("com.google.devtools.ksp:symbol-processing-api:$kspVersion")
            }
            kotlin.srcDirs(
                "src/main/kotlin",
                "build/generated/ksp/jvm/jvmMain"
            )
            resources.srcDir("src/main/resources")
        }
    }
}

dependencies {
    add("kspJvm", "io.insert-koin:koin-ksp-compiler:$koinKspVersion")
}
