package com.tbse.wnsw.ui.aplist

import android.app.Application
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.net.wifi.WifiManager
import android.net.wifi.WifiNetworkSpecifier
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
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

    private val connectivityManager: ConnectivityManager =
        application.getSystemService(ConnectivityManager::class.java)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            @Suppress("DEPRECATION")
            wifiManager.startScan()
        }
        observeAccessPoints()
        autoConnectToStrongestFavorite()
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

    fun connectToNetwork(ap: AccessPointUI) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d(TAG, "Attempting to connect to ${ap.SSID} [${ap.BSSID}]")
            requestNetworkConnection(ap.SSID, ap.BSSID)
        }
    }

    private fun autoConnectToStrongestFavorite() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val favorites = apRepository.getFavoritesByStrength().first()
                if (favorites.isNotEmpty()) {
                    val strongest = favorites.first()
                    Log.d(TAG, "Auto-connecting to strongest favorite: ${strongest.SSID}")
                    requestNetworkConnection(strongest.SSID, strongest.BSSID)
                }
            } catch (e: Exception) {
                Log.w(TAG, "Failed to auto-connect to favorite", e)
            }
        }
    }

    private fun requestNetworkConnection(ssid: String, bssid: String) {
        if (ssid.isBlank()) {
            Log.w(TAG, "Cannot connect to network with empty SSID (hidden network): $bssid")
            return
        }
        try {
            val specifier = WifiNetworkSpecifier.Builder()
                .setSsid(ssid)
                .setBssid(android.net.MacAddress.fromString(bssid))
                .build()

            val request = NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .removeCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .setNetworkSpecifier(specifier)
                .build()

            val callback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    Log.d(TAG, "Connected to $ssid")
                    connectivityManager.bindProcessToNetwork(network)
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    Log.w(TAG, "Network $ssid unavailable")
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    Log.d(TAG, "Lost connection to $ssid")
                    connectivityManager.bindProcessToNetwork(null)
                }
            }

            connectivityManager.requestNetwork(request, callback)
            Log.d(TAG, "Network request sent for $ssid")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to request network connection", e)
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