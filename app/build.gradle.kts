
plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
//    id("dagger.hilt.android.plugin")
}

android {
    compileSdkVersion("android-S")

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

val composeVersion  = "1.0.4" // https://developer.android.com/jetpack/androidx/releases/compose#versions

dependencies {

    implementation("androidx.core:core-ktx:1.5.0")
    implementation("androidx.appcompat:appcompat:1.3.0")
    implementation("com.google.android.material:material:1.3.0")
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling:$composeVersion")

    // https://mvnrepository.com/artifact/androidx.lifecycle/lifecycle-runtime-ktx
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.0")

    // https://androidx.tech/artifacts/activity/activity-compose/
    implementation("androidx.activity:activity-compose:$composeVersion")

//    implementation("com.google.dagger:hilt-android:${rootProject.extra["hiltVersion"]}")
//    kapt("com.google.dagger:hilt-android-compiler:${rootProject.extra["hiltVersion"]}")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeVersion")
}