import org.gradle.api.tasks.Delete
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.hilt.android) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.compose.plugin) apply false
}

tasks.register("clean", Delete::class) {
   delete(rootProject.layout.buildDirectory)
}
