package com.kgit2.bitmask

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.attributes.plugin.GradlePluginApiVersion
import org.gradle.api.logging.LoggingManager

class BitmaskPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        println("BitmaskPlugin applied to ${target.name}")
    }
}
