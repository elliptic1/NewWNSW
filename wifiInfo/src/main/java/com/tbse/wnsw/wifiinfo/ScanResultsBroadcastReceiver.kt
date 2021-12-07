package com.tbse.wnsw.wifiinfo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import java.util.*
import kotlin.Comparator

/**
 * Created by toddsmith on 5/19/21.
 * Copyright TBSE 2022
 */
class ScanResultsBroadcastReceiver : BroadcastReceiver() {
    private val TAG = "ScanResultsBroadcastReceiver"

    override fun onReceive(context: Context, intent: Intent) {
        StringBuilder().apply {
            append("Action: ${intent.action}\n")
            append("URI: ${intent.toUri(Intent.URI_INTENT_SCHEME)}\n")
            toString().also { log ->
                Log.d(TAG, log)
                Toast.makeText(context, log, Toast.LENGTH_LONG).show()
            }
//            context.startActivity(MainActivity.getWifiScanIntent(context))
        }
    }
}