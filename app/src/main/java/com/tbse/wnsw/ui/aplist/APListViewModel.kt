package com.tbse.wnsw.ui.aplist

import android.app.Application
import android.net.wifi.WifiManager
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.tbse.wnsw.TAG
import com.tbse.wnsw.models.AccessPointUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

sealed interface APListUiState {

    object NoAPs : APListUiState

    data class HasListOfAPs(
        val aps: List<AccessPointUI>,
    ) : APListUiState

}

private data class APListViewModelState(
    val isLoading: Boolean,
    val hasScanResult: Boolean,
    val aps: List<AccessPointUI>,
) {
    fun toUiState(): APListUiState =
        if (hasScanResult) {
            aps.log()
            APListUiState.HasListOfAPs(
                aps = aps
            )
        } else {
            APListUiState.NoAPs
        }
}

@HiltViewModel
class APListViewModel @Inject constructor(
    application: Application,
    wifiManager: WifiManager,
//    wifiScanRepository: WifiScanRepository
) : AndroidViewModel(application) {

    init {
        wifiManager.startScan()
    }

    private val viewModelState = MutableStateFlow(
        APListViewModelState(
            isLoading = true,
            hasScanResult = false,
            aps = listOf()
        )
    )

    // UI state exposed to the UI
    val uiState = viewModelState
        .map { it.toUiState() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

//    val activeNetworkInfoLiveData: LiveData<NetworkInfo?> =
//        ReceiverLiveData(getApplication(),
//            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)) { context: Context, intent: Intent? ->
//            (context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
//        }
}

private fun List<AccessPointUI>.log() {
    Log.d(TAG, "Access Points:")
    this.forEachIndexed { index, ap ->
        Log.d(TAG, " - $index. ${ap.SSID} [${ap.BSSID}]")
        Log.d(TAG, " - - capabilities: ${ap.capabilities}")
        Log.d(TAG, " - - channel: ${ap.channel}")
    }
    Log.d(TAG, "---------------")
}