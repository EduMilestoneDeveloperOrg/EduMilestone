// ✅ Apply necessary plugins for an Android Library module
plugins {
    alias(libs.plugins.android.library) // Android Library plugin
    alias(libs.plugins.kotlin.android)  // Kotlin Android plugin
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.edumilestone.modules01" // ✅ Correct package name
    compileSdk = 35 // ✅ Target latest stable SDK

    defaultConfig {
        minSdk = 24 // ✅ Minimum supported Android version

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro") // ✅ ProGuard rules for consumer modules
    }

    buildTypes {
        release {
            isMinifyEnabled = false // ✅ Disable ProGuard minification for now
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
        jvmTarget = "11" // ✅ Use Java 11 for better performance
    }

    // ✅ Enable Jetpack Compose
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1" // ✅ Specify Compose compiler version
    }
    buildFeatures {
        compose = true // ✅ Enable Jetpack Compose UI framework
    }
}

dependencies {
    // ✅ Core AndroidX Dependencies
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.multidex)

    // ✅ Jetpack Compose Dependencies (Using BOM)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)

    // ✅ Correct Material3 for Jetpack Compose
    implementation(libs.androidx.material3)

    // ✅ Jetpack Compose Navigation
    implementation(libs.androidx.navigation.compose)

    // ✅ Tooling for Debugging (Only in Debug Builds)
    debugImplementation(libs.androidx.ui.tooling)

    // ✅ Link Central Core to Module 01
    implementation(project(":centralcore"))

    // ✅ Testing Dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // ✅ Material Icons Extended for Jetpack Compose using version catalog
    implementation(libs.compose.material.icons.extended)
}