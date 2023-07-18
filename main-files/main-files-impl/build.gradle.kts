plugins {
    id("feature-convention")
}

android {
    namespace = "com.samara.main_files_impl"
}

dependencies {

    implementation(project(Deps.Project.core))
    implementation(project(Deps.Project.mainFilesApi))

    implementation(libs.coreKtx)
    implementation(libs.kotlinCoroutines)

    implementation(libs.dagger)
    kapt(libs.daggerCompiler)

    implementation(libs.bundles.android)

    testImplementation(libs.testJunit)
    androidTestImplementation(libs.testJunitExt)
    androidTestImplementation(libs.espresso)
}