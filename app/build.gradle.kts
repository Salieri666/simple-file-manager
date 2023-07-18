plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
}

android {
    namespace = "com.samara.simplefilemanager"
    compileSdk = Solution.Version.compileSdk

    defaultConfig {
        applicationId = "com.samara.simplefilemanager"
        minSdk = Solution.Version.minSdk
        targetSdk = Solution.Version.targetSdk
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
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
        sourceCompatibility = Solution.Version.java
        targetCompatibility = Solution.Version.java
    }
    kotlinOptions {
        jvmTarget = Solution.Version.jvmTarget
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Deps.Versions.composeVersion
    }

    kapt {
        correctErrorTypes = true
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(project(Deps.Project.core))
    implementation(project(Deps.Project.uikit))
    implementation(project(Deps.Project.mainFiles))

    implementation(libs.coreKtx)
    implementation(libs.kotlinCoroutines)

    implementation(libs.bundles.lifecycle)

    implementation(libs.dagger)
    kapt(libs.daggerCompiler)

    implementation(platform(libs.composeBom))
    implementation(libs.bundles.compose)

    testImplementation(libs.testJunit)
    androidTestImplementation(libs.testJunitExt)
    androidTestImplementation(libs.espresso)
    androidTestImplementation(platform(libs.composeBom))
    androidTestImplementation(libs.uiTestJunit)
    debugImplementation(libs.uiTooling)
    debugImplementation(libs.uiTestManifest)

}