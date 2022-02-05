package com.tbse.wnsw.system.extensions

/**
 * Created by toddsmith on 2/4/22.
 */

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.callbackFlow

@OptIn(ExperimentalCoroutinesApi::class)
fun Context.registerReceiverAsFlow(
    vararg intentFilterActions: String,
): Flow<Intent> = callbackFlow {
    val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            trySendBlocking(intent)
        }
    }
    val intentFilter = IntentFilter()
    intentFilterActions.forEach { intentFilter.addAction(it) }
    registerReceiver(receiver, intentFilter)
    awaitClose {
        unregisterReceiver(receiver)
    }
}.buffer(capacity = Channel.UNLIMITED)
