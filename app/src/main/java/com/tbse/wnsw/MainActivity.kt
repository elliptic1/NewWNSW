package com.tbse.wnsw

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.ACCESS_WIFI_STATE
import android.Manifest.permission.CHANGE_WIFI_STATE
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.wifi.WifiManager
import android.net.wifi.WifiManager.SCAN_RESULTS_AVAILABLE_ACTION
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.tbse.wnsw.models.transformers.ScanResultToAccessPointTransformer
import com.tbse.wnsw.ui.aplist.AccessPointList
import com.tbse.wnsw.ui.aplist.preview.AccessPointPreviewProviderMany
import com.tbse.wnsw.ui.need_permissions.NeedPermissionsPage
import com.tbse.wnsw.ui.theme.NewWNSWTheme
import com.tbse.wnsw.wifiinfo.broadcast_receiver.ScanResultsBroadcastReceiver
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalTime

const val PERMISSION_REQUEST_LOCATION = 0

val requiredPermissions = setOf(
    ACCESS_FINE_LOCATION,
    ACCESS_WIFI_STATE,
    CHANGE_WIFI_STATE
)

@AndroidEntryPoint
class MainActivity : AppCompatActivity(),
    ActivityCompat.OnRequestPermissionsResultCallback {

    //    @Inject
    lateinit var apTransformer: ScanResultToAccessPointTransformer

    private var receiver: ScanResultsBroadcastReceiver? = null
    private lateinit var intentFilter: IntentFilter


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
            receiver = ScanResultsBroadcastReceiver()
            intentFilter = IntentFilter(SCAN_RESULTS_AVAILABLE_ACTION)
            registerReceiver(receiver, intentFilter, null, null)

            init()

        } else {
            requestPermissions()
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
        val wifiManager = getWifiManager()
        apTransformer = ScanResultToAccessPointTransformer()

        setContent {
            val lastLoad = remember {
                mutableStateOf(LocalTime.now())
            }
            val setLastLoad: () -> Unit = {
                lastLoad.value = LocalTime.now()
            }
            NewWNSWTheme {
                Surface(color = MaterialTheme.colors.background) {
                    if (wifiManager != null) {
                        AccessPointList(
                            itemViewStates =
                            wifiManager
                                .scanResults
                                .map { apTransformer(it, wifiManager) },
                            setLastLoad = setLastLoad
                        )
                    } else {
                        Text("Need permissions")
                    }
                }
            }
        }
    }


    override fun onPause() {
        super.onPause()
        if (receiver != null) {
            unregisterReceiver(receiver)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        init()
    }

    private fun getWifiManager(): WifiManager? {
        return if (hasPermissions()) {
            application.getSystemService(WIFI_SERVICE) as WifiManager
        } else null
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

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NewWNSWTheme {
        AccessPointList(
            itemViewStates =
            AccessPointPreviewProviderMany().values.toList()
        ) {}
    }
}