// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.kspPlugin) version libs.versions.android.ksp apply false
    alias(libs.plugins.hiltPlugin) version libs.versions.hilt apply false
    // alias(libs.plugins.googleServices) version libs.versions.android.gsm apply false
    //alias(libs.plugins.crashlytics) version libs.versions.android.crashlytics apply false
    alias(libs.plugins.library) version libs.versions.library apply false
    kotlin("plugin.serialization") version "2.0.10" apply false
}