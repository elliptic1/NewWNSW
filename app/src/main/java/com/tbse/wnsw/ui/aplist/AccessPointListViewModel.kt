package com.tbse.wnsw.ui.aplist

import android.app.Application
import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.tbse.wifi.support.ReceiverLiveData
import com.tbse.wnsw.models.AccessPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

sealed interface AccessPointListUiState {

    object NoAPs : AccessPointListUiState

    data class HasListOfAPs(
        val aps: List<AccessPoint>,
    ) : AccessPointListUiState

}

private data class AccessPointListViewModelState(
    val isLoading: Boolean,
    val hasScanResult: Boolean,
    val aps: List<AccessPoint>,
) {
    fun toUiState(): AccessPointListUiState =
        if (!hasScanResult) {
            AccessPointListUiState.NoAPs
        } else {
            AccessPointListUiState.HasListOfAPs(
                aps = aps
            )
        }
}

class AccessPointListViewModel(
    application: Application,
//    wifiScanRepository: WifiScanRepository
) : AndroidViewModel(application) {

    private val viewModelState = MutableStateFlow(
        AccessPointListViewModelState(
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

    val activeNetworkInfoLiveData: LiveData<NetworkInfo?> =
        ReceiverLiveData(getApplication(),
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)) { context: Context, intent: Intent? ->
            (context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
        }
}