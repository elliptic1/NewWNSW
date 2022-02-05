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
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.tbse.tbse.wifi.database.APDao
import com.tbse.tbse.wifi.database.AccessPoint
import com.tbse.wifi.support.ModelMapper
import com.tbse.wnsw.system.extensions.registerReceiverAsFlow
import com.tbse.wnsw.ui.aplist.APListViewModel
import com.tbse.wnsw.ui.aplist.APProMainScreen
import com.tbse.wnsw.ui.need_permissions.NeedPermissionsPage
import com.tbse.wnsw.ui.theme.NewWNSWTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach
import java.time.LocalTime
import javax.inject.Inject

const val PERMISSION_REQUEST_LOCATION = 0

val requiredPermissions = setOf(
    ACCESS_FINE_LOCATION,
    ACCESS_WIFI_STATE,
    CHANGE_WIFI_STATE
)

@AndroidEntryPoint
class MainActivity : AppCompatActivity(),
    ActivityCompat.OnRequestPermissionsResultCallback {

    @Inject
    lateinit var wifiManager: WifiManager

    @Inject
    lateinit var mapScanResultToAccessPoint: ModelMapper<ScanResult, AccessPoint>

    @Inject
    lateinit var apDao: APDao

    private val apViewModel by viewModels<APListViewModel>()

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_LOCATION) {
            // Request for camera permission.
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission has been granted. Start Activity.
                init()
            } else {
                // Permission request was denied.
                showNeedsPermissionsPage()
            }
        }
    }

    override fun onResume() {
        super.onResume()

        if (hasPermissions()) {
            registerScanResultsReceiver()
            init()
        } else {
            requestPermissions()
        }

    }

    private fun registerScanResultsReceiver() {
        applicationContext
            .registerReceiverAsFlow(SCAN_RESULTS_AVAILABLE_ACTION)
            .onEach { intent ->
                wifiManager.scanResults
                    .map {
                        val ap = mapScanResultToAccessPoint(it)
                        apDao.insertAP(ap)
                    }
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

    private fun showNeedsPermissionsPage() {
        setContent {
            NewWNSWTheme {
                NeedPermissionsPage {
                    requestPermissions()
                }
            }
        }
    }

    private fun init() {
        setContent {
            val lastLoad = remember {
                mutableStateOf(LocalTime.now())
            }
            val setLastLoad: () -> Unit = {
                lastLoad.value = LocalTime.now()
            }

            NewWNSWTheme {
                Surface(color = MaterialTheme.colors.background) {
                    APProMainScreen(
                        apViewModel = apViewModel,
                        setLastLoad = setLastLoad,
                        addNetworkSuggestions = ::addNetworkSuggestions,
                        removeNetworkSuggestions = ::removeNetworkSuggestions
                    )
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        init()
    }

    companion object {
        fun getWifiScanIntent(context: Context): Intent {
            val i = Intent(context, MainActivity::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            i.putExtra("wifi_scan", "1")
            return i
        }
    }

    private fun hasPermissions(): Boolean {
        return if (
            requiredPermissions.all {
                ContextCompat.checkSelfPermission(
                    applicationContext,
                    it
                ) == PackageManager.PERMISSION_GRANTED
            }
        ) {
            true
        } else {
            requestPermissions()
            false
        }
    }

    /**
     * Requests the [android.Manifest.permission.ACCESS_FINE_LOCATION] permission.
     * If an additional rationale should be displayed, the user has to launch the request from
     * a SnackBar that includes additional information.
     */
    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            requiredPermissions.toTypedArray(),
            PERMISSION_REQUEST_LOCATION
        )
    }

    private fun addNetworkSuggestions(list: List<WifiNetworkSuggestion>) {
        wifiManager.addNetworkSuggestions(list)
    }

    private fun removeNetworkSuggestions(list: List<WifiNetworkSuggestion>) {
        wifiManager.removeNetworkSuggestions(list)
    }

}
