/**
 * Created by toddsmith on 2/5/22.
 */
object Compose {
    // Compose compiler version must match Kotlin version: 1.9.22 -> 1.5.10
    const val composeVersion = "1.5.10"
    private const val composeBomVersion = "2024.02.00"
    private const val composeUiVersion = "1.6.1"
    private const val composeRuntimeVersion = "1.6.1"
    const val material = "androidx.compose.material:material:$composeUiVersion"
    const val ui = "androidx.compose.ui:ui:$composeUiVersion"
    const val uiToolingPreview = "androidx.compose.ui:ui-tooling-preview:$composeUiVersion"
    const val uiTooling = "androidx.compose.ui:ui-tooling:$composeUiVersion"
    const val runtime = "androidx.compose.runtime:runtime:$composeRuntimeVersion"
    const val compiler = "androidx.compose.compiler:compiler:$composeVersion"

    private const val navigationVersion = "2.7.7"
    const val navigation = "androidx.navigation:navigation-compose:$navigationVersion"

    private const val hiltNavigationComposeVersion = "1.1.0"
    const val hiltNavigationCompose = "androidx.hilt:hilt-navigation-compose:$hiltNavigationComposeVersion"

    private const val activityComposeVersion = "1.8.2"
    const val activityCompose = "androidx.activity:activity-compose:$activityComposeVersion"

    private const val lifecycleVersion = "2.7.0"
    const val viewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion"
}