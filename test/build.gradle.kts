plugins {
    kotlin("multiplatform")
    id("com.google.devtools.ksp")
    application
}

val kotlinVersion: String by ext
val kspVersion: String by ext

kotlin {
    jvm {
        application {
            mainClass.set("com.bppleman.MainKt")
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":library"))
            }
        }
        val jvmMain by getting
    }
}

dependencies {
    add("kspJvm", project(":processor"))
    // add("kspNative", project(":processor"))
}
