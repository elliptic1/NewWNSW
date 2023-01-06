/**
 * Created by toddsmith on 2/5/22.
 */
object Compose {
    const val composeVersion = "1.3.2"
    private const val composeCompilerVersion = "1.3.0"
    private const val composeMaterialVersion = "1.3.1"
    private const val composeUiVersion = "1.3.2"
    private const val composeRuntimeVersion = "1.3.2"
    const val material = "androidx.compose.material:material:$composeMaterialVersion"
    const val ui = "androidx.compose.ui:ui:$composeUiVersion"
    const val uiToolingPreview = "androidx.compose.ui:ui-tooling-preview:$composeUiVersion"
    const val uiTooling = "androidx.compose.ui:ui-tooling:$composeUiVersion"
    const val runtime = "androidx.compose.runtime:runtime:$composeRuntimeVersion"
    const val compiler = "androidx.compose.compiler:compiler:$composeCompilerVersion"

    private const val navigationVersion = "2.5.3"
    const val navigation = "androidx.navigation:navigation-compose:$navigationVersion"

    private const val hiltNavigationComposeVersion = "1.0.0"
    const val hiltNavigationCompose = "androidx.hilt:hilt-navigation-compose:$hiltNavigationComposeVersion"

    private const val activityComposeVersion = "1.6.1"
    const val activityCompose = "androidx.activity:activity-compose:$activityComposeVersion"

    private const val lifecycleVersion = "2.5.1"
    const val viewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion"
}