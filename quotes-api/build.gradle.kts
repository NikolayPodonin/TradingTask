plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
}
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}

dependencies {

    implementation(project(":main-api"))
    val composeBom = platform(libs.jetpack.compose)
    implementation(composeBom)
    implementation(libs.jetpack.compose.foundation)
    implementation(libs.jetpack.compose.runtime)

    implementation(libs.kotlinx.serialization)
}
