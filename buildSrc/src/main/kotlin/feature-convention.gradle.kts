plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
}

android {
    compileSdk = Solution.Version.compileSdk

    defaultConfig {
        minSdk = Solution.Version.minSdk
        targetSdk = Solution.Version.targetSdk

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
        sourceCompatibility = Solution.Version.java
        targetCompatibility = Solution.Version.java
    }
    kotlinOptions {
        jvmTarget = Solution.Version.jvmTarget
    }

    kapt {
        correctErrorTypes = true
    }
}