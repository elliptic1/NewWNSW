package com.tbse.wnsw.ui.aplist.preview

import java.util.*
import kotlin.random.Random.Default.nextInt

/**
 * Created by toddsmith on 5/16/21.
 * Copyright TBSE 2017
 */
internal object RandomMac {
    val randomMacAddress: String
        get() {
            return (0..5).joinToString(":") {
                String.format("%02x", nextInt(255))
            }.toUpperCase(Locale.ROOT)
        }
}