dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("buildSrc/libs.toml"))
        }
    }
}
rootProject.name = "Simple File Manager"
include(
    ":app",
    ":core",
    ":ui-kit",
    ":main-files:main-files",
    ":main-files:main-files-api",
    ":main-files:main-files-provider",
    ":main-files:main-files-impl"
)
