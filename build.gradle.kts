// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
//    val hiltVersion by extra("2.35")
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.1.0-alpha01")
//        https://maven.google.com/web/index.html?q=com.android.tools#com.android.tools.build:gradle:7.1.0-alpha01
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.10")
//        classpath("com.google.dagger:hilt-android-gradle-plugin:$hiltVersion")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}