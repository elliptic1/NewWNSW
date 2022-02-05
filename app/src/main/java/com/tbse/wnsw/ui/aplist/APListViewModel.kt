package com.tbse.wnsw.ui.aplist

import android.app.Application
import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.tbse.wifi.support.ReceiverLiveData
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