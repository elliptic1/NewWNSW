package com.tbse.wnsw.ui.aplist

import android.app.Application
import android.net.wifi.WifiManager
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.tbse.wifi.support.ModelMapper
import com.tbse.wnsw.TAG
import com.tbse.wnsw.domain.models.AccessPointDomain
import com.tbse.wnsw.domain.repositories.APRepository
import com.tbse.wnsw.models.AccessPointUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface APListUiState {

    object NoAPs : APListUiState

    data class HasListOfAPs(
        val aps: List<AccessPointUI>,
    ) : APListUiState

}

sealed interface APListEvent {
    data class OpenWifiSettings(val ssid: String) : APListEvent
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
    private val wifiManager: WifiManager,
    private val apRepository: APRepository,
    private val accessPointMapper: ModelMapper<AccessPointDomain, AccessPointUI>
) : AndroidViewModel(application) {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            @Suppress("DEPRECATION")
            wifiManager.startScan()
        }
        observeAccessPoints()
    }

    private val viewModelState = MutableStateFlow(
        APListViewModelState(
            isLoading = true,
            hasScanResult = false,
            aps = listOf()
        )
    )

    private val _events = MutableSharedFlow<APListEvent>()
    val events: SharedFlow<APListEvent> = _events

    // UI state exposed to the UI
    val uiState = viewModelState
        .map { it.toUiState() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    private fun observeAccessPoints() {
        viewModelScope.launch(Dispatchers.IO) {
            apRepository.getAllAps().collect { domainList ->
                val uiList = domainList.map { accessPointMapper(it) }
                viewModelState.value = APListViewModelState(
                    isLoading = false,
                    hasScanResult = uiList.isNotEmpty(),
                    aps = uiList
                )
            }
        }
    }

    fun updateFavorite(bssid: String, isFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            apRepository.updateFavorite(bssid, isFavorite)
            Log.d(TAG, "Updated favorite for $bssid to $isFavorite")
        }
    }

    fun onNetworkTapped(ap: AccessPointUI) {
        viewModelScope.launch {
            Log.d(TAG, "Network tapped: ${ap.SSID} [${ap.BSSID}] - opening WiFi settings")
            _events.emit(APListEvent.OpenWifiSettings(ap.SSID))
        }
    }
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