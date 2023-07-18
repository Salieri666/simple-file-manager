
buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.toolsBuildGradle)
        classpath(libs.kotlinGradlePlugin)
    }
}

allprojects {

    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

tasks.register("clear", Delete::class) {
    delete(rootProject.buildDir)
}
