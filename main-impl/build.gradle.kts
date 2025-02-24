plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.podonin.main_impl"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    }
}

dependencies {

    implementation(project(":common-ui"))
    implementation(project(":common-io"))
    implementation(project(":common-utils"))
    implementation(project(":main-api"))
    implementation(project(":quotes-api"))

    val composeBom = platform(libs.jetpack.compose)
    implementation(composeBom)
    implementation(libs.jetpack.compose.foundation)
    implementation(libs.jetpack.compose.navigation)
    implementation(libs.hilt.compose.navigation)

    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    implementation(libs.kotlinx.serialization)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
}