plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.21"
}

android {
    namespace = "com.tbse.wnsw"
    compileSdk = ProjectConfig.compileSdk

    defaultConfig {
        applicationId = ProjectConfig.appId
        minSdk = ProjectConfig.minSdk
        targetSdk = ProjectConfig.targetSdk
        versionCode = ProjectConfig.versionCode
        versionName = ProjectConfig.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
}


dependencies {

    implementation(AndroidX.coreKtx)
    implementation(AndroidX.appCompat)
    implementation(Google.material)
    implementation(Compose.ui)
    implementation(Compose.material)
    implementation(Compose.uiTooling) {
        because("@Preview annotation")
    }

    // https://mvnrepository.com/artifact/androidx.lifecycle/lifecycle-runtime-ktx
    implementation(AndroidX.lifecycle)

    // https://androidx.tech/artifacts/activity/activity-compose/
    implementation(Compose.activityCompose)
    implementation(project(mapOf("path" to ":wifiSupport")))
    implementation(project(mapOf("path" to ":wifiDomain")))
    implementation(project(mapOf("path" to ":wifiDatabase")))
    implementation(project(mapOf("path" to ":wifiSystem")))

    testImplementation(Testing.junit4)
    androidTestImplementation(Testing.junitAndroidExt)
    androidTestImplementation(Testing.espresso)
    androidTestImplementation(Testing.composeUiTest)

    // Dagger - Hilt
    implementation(DaggerHilt.hiltAndroid)
    kapt(DaggerHilt.hiltCompiler)

    // Compose Navigation
    implementation(AndroidX.navigation)
}
