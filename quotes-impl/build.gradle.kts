plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.podonin.quotes_impl"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
        freeCompilerArgs += listOf("-Xcontext-receivers")
    }
}

dependencies {

    implementation(project(":quotes-api"))
    implementation(project(":main-api"))
    implementation(project(":common-ui"))
    implementation(project(":common-io"))
    implementation(project(":common-utils"))

    val composeBom = platform(libs.jetpack.compose)
    implementation(composeBom)
    implementation(libs.jetpack.compose.foundation)
    implementation(libs.jetpack.compose.runtime)
    implementation(libs.jetpack.compose.ui)
    implementation(libs.jetpack.compose.constraint)
    implementation(libs.jetpack.compose.material3)
    implementation(libs.hilt.compose.navigation)

    implementation(libs.ktor.cio)
    implementation(libs.ktor.content)
    implementation(libs.ktor.core)

    implementation(libs.coil.compose)
    implementation(libs.coil.network)

    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    implementation(libs.kotlinx.serialization)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
}