// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    val hiltVersion by extra("2.51.1")
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        // https://maven.google.com/web/index.html?q=com.android.tools#com.android.tools.build:gradle
        classpath("com.android.tools.build:gradle:8.7.3")

        // https://mvnrepository.com/artifact/org.jetbrains.kotlin/kotlin-gradle-plugin
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:2.0.21")

        // https://mvnrepository.com/artifact/com.google.dagger/hilt-android-gradle-plugin
        classpath("com.google.dagger:hilt-android-gradle-plugin:$hiltVersion")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory)
}