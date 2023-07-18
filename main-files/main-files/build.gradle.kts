plugins {
    id("feature-convention")
}

android {
    namespace = "com.samara.main_files"

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Deps.Versions.composeVersion
    }
}

dependencies {
    implementation(project(Deps.Project.uikit))
    implementation(project(Deps.Project.core))
    implementation(project(Deps.Project.mainFilesApi))
    implementation(project(Deps.Project.mainFilesProvider))

    implementation(libs.coreKtx)
    implementation(libs.kotlinCoroutines)

    implementation(libs.dagger)
    kapt(libs.daggerCompiler)

    implementation(platform(libs.composeBom))
    implementation(libs.bundles.compose)

    implementation(libs.bundles.android)

    testImplementation(libs.testJunit)
    androidTestImplementation(libs.testJunitExt)
    androidTestImplementation(libs.espresso)
}