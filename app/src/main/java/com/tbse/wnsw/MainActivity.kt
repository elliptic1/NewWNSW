package com.tbse.wnsw

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiManager
import android.net.wifi.WifiManager.SCAN_RESULTS_AVAILABLE_ACTION
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.tbse.wnsw.ui.aplist.AccessPointList
import com.tbse.wnsw.ui.aplist.preview.AccessPointPreviewProviderMany
import com.tbse.wnsw.ui.theme.NewWNSWTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var receiver: ScanResultsBroadcastReceiver
    private lateinit var intentFilter: IntentFilter

    override fun onResume() {
        super.onResume()
        Log.d("wnsw", "onResume")

//        <uses-permission android:name=/>
//        <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
//        <uses-permission android:name="android.permission.CHANGE_WIFI_STATE"/>

        checkSelfPermission("android.permission.ACCESS_FINE_LOCATION")

        receiver = ScanResultsBroadcastReceiver()
        intentFilter = IntentFilter(SCAN_RESULTS_AVAILABLE_ACTION)
        registerReceiver(receiver, intentFilter, null, null)

        init()

    }

    private fun init() {
        setContent {
            NewWNSWTheme {
                Surface(color = MaterialTheme.colors.background) {
                    AccessPointList(
                        itemViewStates =
                        AccessPointPreviewProviderMany().values.toList()
                    )
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(receiver)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val wifi = application.getSystemService(WIFI_SERVICE) as WifiManager
        init(wifi.scanResults)
    }

    companion object {
        fun getWifiScanIntent(context: Context): Intent {
            val i = Intent(context, MainActivity::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            i.putExtra("wifi_scan", "1")
            return i
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NewWNSWTheme {
        AccessPointList(
            itemViewStates =
            AccessPointPreviewProviderMany().values.toList()
        )
    }
}