/**
 * Created by toddsmith on 2/5/22.
 */
object AndroidX {
    // https://developer.android.com/jetpack/androidx/releases/core
    private const val coreKtxVersion = "1.12.0"
    const val coreKtx = "androidx.core:core-ktx:$coreKtxVersion"

    // https://developer.android.com/jetpack/androidx/releases/appcompat
    private const val appCompatVersion = "1.6.1"
    const val appCompat = "androidx.appcompat:appcompat:$appCompatVersion"

    private const val lifecycleVersion = "2.7.0"
    const val lifecycle = "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion"

    private const val navigationVersion = "2.7.7"
    const val navigation = "androidx.navigation:navigation-compose:$navigationVersion"
}