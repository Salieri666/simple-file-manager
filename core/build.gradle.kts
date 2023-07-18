plugins {
    id("feature-convention")
}

android {
    namespace = "com.samara.core"

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

    implementation(libs.bundles.lifecycle)

    implementation(libs.securityCrypto)

    implementation(libs.dagger)
    kapt(libs.daggerCompiler)

    implementation(platform(libs.composeBom))
    implementation(libs.bundles.compose)

    implementation(libs.bundles.android)

}