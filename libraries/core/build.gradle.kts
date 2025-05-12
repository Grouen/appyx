import org.jetbrains.compose.ComposeBuildConfig

plugins {
    id("com.android.library")
    kotlin("multiplatform")
    id("org.jetbrains.kotlin.plugin.compose") version "2.1.20"
    id("org.jetbrains.compose") version "1.8.0"
    id("kotlin-parcelize")
    id("maven-publish")
}

group = "com.bumble.appyx"
version = "1.0"

kotlin {
    jvmToolchain(17)
    androidTarget {
        publishLibraryVariants("release")
        compilerOptions.freeCompilerArgs.addAll(
            "-P",
            "plugin:org.jetbrains.kotlin.parcelize:additionalAnnotation=com.bumble.appyx.utils.Parcelize"
        )
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "appyxCoreKit"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.ui)
            implementation(compose.material)
            implementation("org.jetbrains.compose.ui:ui-backhandler:${ComposeBuildConfig.composeVersion}")
            implementation("org.jetbrains.androidx.lifecycle:lifecycle-runtime-compose:2.8.4")
        }
        androidMain.dependencies {
            implementation("androidx.appcompat:appcompat:1.7.0")
        }
    }
}

android {
    namespace = "com.bumble.appyx.core"
    compileSdk = 36

    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        compose = true
    }
}

publishing {
    repositories {
        maven {

        }
    }
}
