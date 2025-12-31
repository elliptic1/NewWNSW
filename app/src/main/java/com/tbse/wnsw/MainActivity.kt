package com.tbse.wnsw

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.ACCESS_WIFI_STATE
import android.Manifest.permission.CHANGE_WIFI_STATE
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.net.wifi.WifiManager.SCAN_RESULTS_AVAILABLE_ACTION
import android.net.wifi.WifiNetworkSuggestion
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.tbse.tbse.wifi.database.APDao
import com.tbse.tbse.wifi.database.AccessPoint
import com.tbse.wifi.support.ModelMapper
import com.tbse.wnsw.system.extensions.registerReceiverAsFlow
import com.tbse.wnsw.ui.aplist.APListViewModel
import com.tbse.wnsw.ui.aplist.APProMainScreen
import com.tbse.wnsw.ui.need_permissions.NeedPermissionsPage
import com.tbse.wnsw.ui.theme.NewWNSWTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.Job
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.flow.catch
import java.time.LocalTime
import javax.inject.Inject

const val PERMISSION_REQUEST_LOCATION = 0

val requiredPermissions = setOf(
    ACCESS_FINE_LOCATION,
    ACCESS_WIFI_STATE,
    CHANGE_WIFI_STATE
)

@AndroidEntryPoint
class MainActivity : ComponentActivity(),
    ActivityCompat.OnRequestPermissionsResultCallback {

    @Inject
    lateinit var wifiManager: WifiManager

    @Inject
    lateinit var mapScanResultToAccessPoint: ModelMapper<ScanResult, AccessPoint>

    @Inject
    lateinit var apDao: APDao

    private val apViewModel by viewModels<APListViewModel>()

    private val permissionState = MutableStateFlow(false)

    private var scanResultsJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val permissionsGranted = hasPermissions()
        updatePermissionState(permissionsGranted)
        if (!permissionsGranted) {
            requestRequiredPermissions()
        }

        setContent {
            val hasPermissions by permissionState.collectAsState()

            val lastLoad = remember {
                mutableStateOf(LocalTime.now())
            }
            val setLastLoad: () -> Unit = {
                lastLoad.value = LocalTime.now()
                triggerWifiScan()
            }

            NewWNSWTheme {
                Surface(color = MaterialTheme.colors.background) {
                    if (hasPermissions) {
                        APProMainScreen(
                            apViewModel = apViewModel,
                            setLastLoad = setLastLoad,
                            addNetworkSuggestions = ::addNetworkSuggestions,
                            removeNetworkSuggestions = ::removeNetworkSuggestions
                        )
                    } else {
                        NeedPermissionsPage {
                            requestRequiredPermissions()
                        }
                    }
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_LOCATION) {
            val granted = grantResults.isNotEmpty() &&
                grantResults.all { it == PackageManager.PERMISSION_GRANTED }
            updatePermissionState(granted)
        }
    }

    override fun onResume() {
        super.onResume()
        updatePermissionState(hasPermissions())
    }

    override fun onDestroy() {
        scanResultsJob?.cancel()
        super.onDestroy()
    }

    private fun updatePermissionState(granted: Boolean) {
        permissionState.value = granted
        if (granted) {
            startWifiScanCollection()
            triggerWifiScan()
        } else {
            stopWifiScanCollection()
        }
    }

    private fun startWifiScanCollection() {
        if (scanResultsJob?.isActive == true) {
            return
        }
        scanResultsJob = lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(androidx.lifecycle.Lifecycle.State.STARTED) {
                persistLatestScanResults()
                applicationContext
                    .registerReceiverAsFlow(SCAN_RESULTS_AVAILABLE_ACTION)
                    .catch { throwable ->
                        Log.e(TAG, "Failed to receive Wi-Fi scan results", throwable)
                    }
                    .collect { intent ->
                        persistLatestScanResults()
                        logScanIntent(intent)
                    }
            }
        }
    }

    private fun stopWifiScanCollection() {
        scanResultsJob?.cancel()
        scanResultsJob = null
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        updatePermissionState(hasPermissions())
    }

    companion object {
        fun getWifiScanIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                putExtra("wifi_scan", "1")
            }
        }
    }

    private fun hasPermissions(): Boolean {
        return requiredPermissions.all {
            ContextCompat.checkSelfPermission(
                applicationContext,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    /**
     * Requests the [android.Manifest.permission.ACCESS_FINE_LOCATION] permission.
     * If an additional rationale should be displayed, the user has to launch the request from
     * a SnackBar that includes additional information.
     */
    private fun requestRequiredPermissions() {
        ActivityCompat.requestPermissions(
            this,
            requiredPermissions.toTypedArray(),
            PERMISSION_REQUEST_LOCATION
        )
    }

    private fun addNetworkSuggestions(list: List<WifiNetworkSuggestion>) {
        lifecycleScope.launch(Dispatchers.IO) {
            val status = wifiManager.addNetworkSuggestions(list)
            handleSuggestionStatus(status, true)
        }
    }

    private fun removeNetworkSuggestions(list: List<WifiNetworkSuggestion>) {
        lifecycleScope.launch(Dispatchers.IO) {
            val status = wifiManager.removeNetworkSuggestions(list)
            handleSuggestionStatus(status, false)
        }
    }

    @Suppress("DEPRECATION")
    private fun triggerWifiScan() {
        lifecycleScope.launch {
            try {
                val started = wifiManager.startScan()
                Log.d(TAG, "WiFi scan started: $started")
                // Also persist any existing results immediately
                persistLatestScanResults()
            } catch (e: SecurityException) {
                Log.w(TAG, "Missing permission to start WiFi scan", e)
            }
        }
    }

    private suspend fun persistLatestScanResults() {
        try {
            withContext(Dispatchers.IO) {
                wifiManager.scanResults.forEach { scanResult: ScanResult ->
                    val ap = mapScanResultToAccessPoint(scanResult)
                    apDao.insertAP(ap)
                }
            }
        } catch (securityException: SecurityException) {
            Log.w(TAG, "Missing permission for Wi-Fi scan results", securityException)
        }
    }

    private suspend fun logScanIntent(intent: Intent) {
        withContext(Dispatchers.Main) {
            StringBuilder().apply {
                append("Action: ${intent.action}\n")
                append("URI: ${intent.toUri(Intent.URI_INTENT_SCHEME)}\n")
                toString().also { log ->
                    Log.d(TAG, log)
                    Toast.makeText(applicationContext, log, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private suspend fun handleSuggestionStatus(status: Int, isAddOperation: Boolean) {
        withContext(Dispatchers.Main) {
            if (status != WifiManager.STATUS_NETWORK_SUGGESTIONS_SUCCESS) {
                val message = when (status) {
                    WifiManager.STATUS_NETWORK_SUGGESTIONS_ERROR_ADD_EXCEEDS_MAX_PER_APP ->
                        "Too many network suggestions requested"
                    WifiManager.STATUS_NETWORK_SUGGESTIONS_ERROR_ADD_DUPLICATE ->
                        "Duplicate network suggestion provided"
                    WifiManager.STATUS_NETWORK_SUGGESTIONS_ERROR_ADD_INVALID ->
                        "Invalid network suggestion"
                    WifiManager.STATUS_NETWORK_SUGGESTIONS_ERROR_ADD_NOT_ALLOWED ->
                        "Adding network suggestions is not allowed"
                    WifiManager.STATUS_NETWORK_SUGGESTIONS_ERROR_REMOVE_INVALID ->
                        "Unable to remove network suggestion"
                    else -> "Suggestion operation failed with status $status"
                }
                Log.w(TAG, message)
                Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
            } else {
                val operation = if (isAddOperation) "added" else "removed"
                Log.d(TAG, "Network suggestions successfully $operation")
            }
        }
    }
}
