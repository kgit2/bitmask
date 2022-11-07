plugins {
    kotlin("multiplatform")
}

val kotlinVersion: String by ext
val kspVersion: String by ext

kotlin {
    jvm("jvm") {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    js(IR) {
        nodejs {
            useCommonJs()
        }
        browser()
    }
    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")
    val nativeTarget = when {
        hostOs == "Mac OS X" -> macosArm64("native")
        hostOs == "Linux" -> linuxX64("native")
        isMingwX64 -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }


    ios("ios") {
        binaries {
            framework {
                baseName = "bitmask"
            }
        }
    }
    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        // val jvmMain by getting
        // val jvmTest by getting
        // val jsMain by getting
        // val jsTest by getting
        // val nativeMain by getting
        // val nativeTest by getting
        // val iosMain by getting
        // val iosTest by getting
    }
}
