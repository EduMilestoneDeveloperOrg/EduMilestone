// ✅ Apply necessary plugins for an Android Library module
plugins {
    alias(libs.plugins.android.library) // ✅ Plugin for Android libraries
    alias(libs.plugins.kotlin.android) // ✅ Plugin for Kotlin support
}

android {
    namespace = "com.edumilestone.centralcore" // ✅ Unique package namespace for Central Core module
    compileSdk = 35 // ✅ Target latest stable SDK

    defaultConfig {
        minSdk = 24 // ✅ Minimum supported Android version
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro") // ✅ ProGuard rules for consumer modules
    }

    buildTypes {
        release {
            isMinifyEnabled = true // ✅ Enable ProGuard & R8 shrinking for smaller APKs
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17 // ✅ Use Java 17 for better performance
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17" // ✅ Kotlin JVM target set to 17
    }

    buildFeatures {
        viewBinding = true // ✅ Enable ViewBinding (if using XML-based UI components)
    }
}

dependencies {
    // ✅ Core Android Libraries
    implementation(libs.androidx.core.ktx) // Android KTX extensions
    implementation(libs.androidx.appcompat) // Support for older Android versions

    implementation(libs.material) // ✅ Fix: Use correct Material Components dependency

    testImplementation(libs.junit) // ✅ Unit Testing
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
