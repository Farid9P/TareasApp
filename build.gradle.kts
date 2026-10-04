// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt) apply false
}

// Keep generated files outside OneDrive to prevent Windows/OneDrive file locks.
val externalBuildRoot = gradle.gradleUserHomeDir
    .resolve("project-builds")
    .resolve(rootProject.name)

layout.buildDirectory.set(externalBuildRoot.resolve("root"))

subprojects {
    layout.buildDirectory.set(externalBuildRoot.resolve(name))
}
