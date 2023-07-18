plugins {
    id("feature-convention")
}

android {
    namespace = "com.samara.main_files_provider"
}

dependencies {
    implementation(project(Deps.Project.mainFilesApi))
    implementation(project(Deps.Project.mainFilesImpl))
}