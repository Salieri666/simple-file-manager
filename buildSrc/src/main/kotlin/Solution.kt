import org.gradle.api.JavaVersion

object Solution {

    object Version {
        const val minSdk = 28
        const val targetSdk = 33
        const val compileSdk = targetSdk
        val java = JavaVersion.VERSION_17
        val jvmTarget = java.toString()
    }

    object App

    object Keys
}