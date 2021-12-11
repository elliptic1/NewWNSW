dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "New WNSW"
include(":app")
include(":wifiInfoDatabase")
include(":wifiDatabase")
include(":wifiSupport")
include(":wifiDomain")
include(":wifiSystem")
