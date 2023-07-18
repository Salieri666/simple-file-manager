plugins {
    id("feature-convention")
}

android {
    namespace = "com.samara.ui_kit"

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Deps.Versions.composeVersion
    }
}

dependencies {

    implementation(libs.coreKtx)
    implementation(libs.kotlinCoroutines)

    implementation(libs.bundles.android)

    implementation(platform(libs.composeBom))
    implementation(libs.bundles.compose)

    androidTestImplementation(platform(libs.composeBom))
    androidTestImplementation(libs.uiTestJunit)
    debugImplementation(libs.uiTooling)
    debugImplementation(libs.uiTestManifest)

    //dagger
    implementation(libs.dagger)
    kapt(libs.daggerCompiler)
}