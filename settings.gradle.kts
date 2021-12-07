dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "New WNSW"
include(":app")
include(":wifiInfo")
include(":wifiInfoDatabase")
include(":wifiDatabase")
include(":wifiSupport")
