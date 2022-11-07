plugins {
    kotlin("jvm")
    `java-gradle-plugin`
}

val kotlinVersion: String by ext
val kspVersion: String by ext

gradlePlugin {
    plugins {
        create(project.rootProject.name) {
            id = "${project.group}.${project.rootProject.name}"
            version = "${project.version}"
            implementationClass = "${project.group}.${project.rootProject.name}.BitmaskPlugin"
        }
    }
}
