plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.podonin.common_io"
    compileSdk = 35

    buildFeatures.buildConfig = true

    defaultConfig {
        minSdk = 24

        buildConfigField("String", "WEB_SOCKET_URL", "\"wss://wss.tradernet.com/\"")
        buildConfigField("String", "HTTP_API_URL", "\"https://tradernet.com/api/\"")
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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    implementation(libs.ktor.android)
    implementation(libs.ktor.websocket)
    implementation(libs.ktor.cio)
    implementation(libs.ktor.okhttp)
    implementation(libs.ktor.content)
    implementation(libs.ktor.serialization)
    implementation(libs.ktor.core)

    implementation(libs.kotlinx.serialization)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
}