
plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

val composeVersion  = "1.1.0-rc03" // https://developer.android.com/jetpack/androidx/releases/compose#versions

android {
    compileSdk = 31

    defaultConfig {
        applicationId = "com.tbse.wnsw"
        minSdk = 30
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        // https://developer.android.com/jetpack/androidx/releases/compose-compiler
        kotlinCompilerExtensionVersion = composeVersion
    }
}


dependencies {

    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("com.google.android.material:material:1.5.0")
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling:$composeVersion") {
        because("@Preview annotation")
    }

    // https://mvnrepository.com/artifact/androidx.lifecycle/lifecycle-runtime-ktx
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.0")

    // https://androidx.tech/artifacts/activity/activity-compose/
    implementation("androidx.activity:activity-compose:1.4.0")
    implementation(project(mapOf("path" to ":wifiSupport")))
    implementation(project(mapOf("path" to ":wifiDomain")))
    implementation(project(mapOf("path" to ":wifiDatabase")))
    implementation(project(mapOf("path" to ":wifiSystem")))

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

//    https://mvnrepository.com/artifact/androidx.compose.ui/ui-test-junit4
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.1.0-beta04")

    // Dagger - Hilt
    implementation("com.google.dagger:hilt-android:2.40.5")
    kapt("com.google.dagger:hilt-android-compiler:2.40.5")

    // Compose Navigaton
    implementation("androidx.navigation:navigation-compose:2.4.0-beta02")
}
