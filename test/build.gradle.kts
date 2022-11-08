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

    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")
    val nativeTarget = when {
        hostOs == "Mac OS X" -> macosArm64("native")
        hostOs == "Linux" -> linuxX64("native")
        isMingwX64 -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":bitmask-library"))
            }
            kotlin.srcDirs("build/generated/ksp/metadata/commonMain")
        }
        val jvmMain by getting {
            kotlin.srcDirs("build/generated/ksp/jvm/jvmMain/kotlin")
        }
        val nativeMain by getting {
            kotlin.srcDirs("build/generated/ksp/native/nativeMain/kotlin")
        }
    }
}

dependencies {
    add("kspCommonMainMetadata", project(":bitmask-processor"))
    add("kspJvm", project(":bitmask-processor"))
    add("kspNative", project(":bitmask-processor"))
    // ksp(project(":bitmask-processor"))
}
